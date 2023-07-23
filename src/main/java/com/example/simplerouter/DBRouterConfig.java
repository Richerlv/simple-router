package com.example.simplerouter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Richerlv
 * @date: 2023/7/20 23:59
 * @description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBRouterConfig {

    /**
     * 分库数量
     */
    private int dbCount;

    /**
     * 分表数量
     */
    private int tbCount;

    /**
     * 路由字段
     */
    private String routerKey;

}
