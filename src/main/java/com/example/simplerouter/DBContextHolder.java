package com.example.simplerouter;

/**
 * @author: Richerlv
 * @date: 2023/7/21 0:09
 * @description:
 */

public class DBContextHolder {

    private static final ThreadLocal<String> dbKey = new ThreadLocal<>();

    private static final ThreadLocal<String> tbKey = new ThreadLocal<>();

    public static String setDBKey(String dbKeyIdx) {dbKey.set(dbKeyIdx);
        return dbKeyIdx;
    }

    public static String getDBkey() {return dbKey.get();}

    public static void setTBKey(String tbKeyIdx) {tbKey.set(tbKeyIdx);}

    public static String getTBKey() {return tbKey.get();}

    public static void clearDBkey() {dbKey.remove();}

    public static void clearTBKey() {tbKey.remove();}

}
