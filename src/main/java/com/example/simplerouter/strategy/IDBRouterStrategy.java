package com.example.simplerouter.strategy;

/**
 * @author: Richerlv
 * @date: 2023/7/21 0:02
 * @description:
 */
public interface IDBRouterStrategy {

    void doRouter(String dbKeyAttr);

    void setDBKey(int dbIdx);

    void setTBKey(int tbIdx);

    int dbCount();

    int tbCount();

    void clear();
}
