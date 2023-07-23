package com.example.simplerouter.strategy.impl;

import com.example.simplerouter.DBContextHolder;
import com.example.simplerouter.DBRouterConfig;
import com.example.simplerouter.annotation.DBRouter;
import com.example.simplerouter.annotation.DBRouterStrategy;
import com.example.simplerouter.strategy.IDBRouterStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @author: Richerlv
 * @date: 2023/7/21 0:19
 * @description:
 */

public class DBRouterStrategyHashCode implements IDBRouterStrategy {

    private Logger logger = LoggerFactory.getLogger(DBRouterStrategyHashCode.class);

    private DBRouterConfig dbRouterConfig;

    public DBRouterStrategyHashCode(DBRouterConfig dbRouterConfig) {this.dbRouterConfig = dbRouterConfig;}

    @Override
    public void doRouter(String dbKeyAttr) {
        //一共有多少表,最好是2的幂次,散列更均匀
        int size = dbRouterConfig.getDbCount() *  dbRouterConfig.getTbCount();

        //扰动函数
        int idx = (size - 1) & (dbKeyAttr.hashCode() ^ (dbKeyAttr.hashCode() >>> 16));

        //路由到库
        int dbIdx = idx / dbRouterConfig.getDbCount() + 1;

        //路由到表
        int tbIdx = idx - dbRouterConfig.getTbCount() * (dbIdx - 1);

        //set
        setDBKey(dbIdx);
        setTBKey(tbIdx);
        logger.debug("数据库路由 dbIdx:{} tbIdx:{}", dbIdx, tbIdx);
    }

    @Override
    public void setDBKey(int dbIdx) {
        DBContextHolder.setDBKey(String.format("%02d", dbIdx));
    }

    @Override
    public void setTBKey(int tbIdx) {
        DBContextHolder.setTBKey(String.format("%03d", tbIdx));
    }

    @Override
    public int dbCount() {
        return dbRouterConfig.getDbCount();
    }

    @Override
    public int tbCount() {
        return dbRouterConfig.getTbCount();
    }

    @Override
    public void clear() {
        DBContextHolder.clearDBkey();
        DBContextHolder.clearTBKey();
    }
}
