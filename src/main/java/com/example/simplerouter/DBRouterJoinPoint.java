package com.example.simplerouter;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.example.simplerouter.annotation.DBRouter;
import com.example.simplerouter.strategy.IDBRouterStrategy;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Richerlv
 * @date: 2023/7/22 10:56
 * @description:
 */

@Aspect
public class DBRouterJoinPoint {

    private Logger logger = LoggerFactory.getLogger(DBRouterJoinPoint.class);

    private DBRouterConfig dbRouterConfig;

    private IDBRouterStrategy idbRouterStrategy;

    public DBRouterJoinPoint(DBRouterConfig dbRouterConfig, IDBRouterStrategy idbRouterStrategy) {
        this.dbRouterConfig = dbRouterConfig;
        this.idbRouterStrategy = idbRouterStrategy;
    }

    @Pointcut("@annotation(com.example.simplerouter.annotation.DBRouter)")
    public void aopPoint() {
    }

    @Around("aopPoint() && @annotation(dbRouter)")
    public Object doRouter(ProceedingJoinPoint jp, DBRouter dbRouter) throws Throwable {
        String dbKey = dbRouter.key( );
        if(StringUtils.isBlank(dbKey) && StringUtils.isBlank(dbRouterConfig.getRouterKey())) {
            throw new RuntimeException("annotation DBRouter key is null");
        }
        dbKey = StringUtils.isNotBlank(dbKey) ? dbKey : dbRouterConfig.getRouterKey();
        //获取路由属性值
        String dbKeyAttr = getAttrValue(dbKey, jp.getArgs());
        //执行路由策略
        idbRouterStrategy.doRouter(dbKeyAttr);
        //放行
        try {
            return jp.proceed();
        } finally {
            idbRouterStrategy.clear();
        }
    }

    public String getAttrValue(String attr, Object[] args) {
        String fieldValue = null;
        for(Object arg : args) {
            try {
                if(StringUtils.isNotBlank(fieldValue)) {
                    break;
                }
                fieldValue = BeanUtils.getProperty(arg, attr);
            } catch (Exception e) {
                logger.error("获取属性值失败 attr:{}", attr);
            }
        }
        return fieldValue;
    }
}
