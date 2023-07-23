package com.example.simplerouter.dynamic;

import com.example.simplerouter.DBContextHolder;
import com.example.simplerouter.annotation.DBRouter;
import com.example.simplerouter.annotation.DBRouterStrategy;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Richerlv
 * @date: 2023/7/22 10:54
 * @description:
 */

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DynamicMybatisPlugin implements Interceptor {

    //将正则表达式编译为Pattern对象，用于匹配sql语句中的表名
    private Pattern pattern = Pattern.compile("(from|into|update)[\\s]{1,}(\\w{1,})", Pattern.CASE_INSENSITIVE);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //获取StatementHandler、元信息
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

        //根据自定义注解判断是否分表
        //获取当前sql语句的唯一标识符（由命名空间和sql语句的id组成）
        String id = mappedStatement.getId();
        String className = id.substring(0, id.lastIndexOf('.'));
        Class<?> clazz = Class.forName(className);
        DBRouterStrategy dbRouterStrategy = clazz.getAnnotation(DBRouterStrategy.class);
        if(dbRouterStrategy == null || !dbRouterStrategy.splitTable()) {
            return invocation.proceed();
        }

        //获取sql
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();

        //替换表名
        Matcher matcher = pattern.matcher(sql);
        String tableName = null;
        if(matcher.find()) {
            //获取表名，除去两端空格
            tableName = matcher.group().trim();
        }
        assert tableName != null;
        String replaceSql = matcher.replaceAll(tableName + "_" + DBContextHolder.getTBKey());

        //通过反射修改sql语句
        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql, replaceSql);
        field.setAccessible(false);

        return invocation.proceed();
    }
}
