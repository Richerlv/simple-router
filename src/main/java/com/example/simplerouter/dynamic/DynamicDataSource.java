package com.example.simplerouter.dynamic;

import com.example.simplerouter.DBContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author: Richerlv
 * @date: 2023/7/22 10:51
 * @description:
 */

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return "db" + DBContextHolder.getDBkey();
    }
}
