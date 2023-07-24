1.分库和分表的区别
2.为什么选择哈希散列路由，还知道什么其他的路由策略，大概想怎么实现
3.为什么路由字段要放在ThreadLocal里面
4.怎么想到在路由策略类里面放一个路由配置类
5.解释下hashMap的散列函数
6.为什么想到借鉴散列函数
7.为什么记录表的总数而不是每张表的数量 -不好维护
8.AbstractRoutingDataSource  什么时候生效
9.如果用id分，但是id是自增的怎么办
10.介绍一下正则表达式，介绍一下Pattern、Matcher
11.【算法】正则表达式
12.为什么想到区分springboot1.x和2.x
13.RelaxedPropertyResolver
14.事务管理
15.通过设置数据源和事务管理器，使得事务能够在多个数据源之间进行切换，保证事务的一致性 

应用
1.为什么要做这个、跟其他的有什么不同  --比较轻量
2.其他实现方案
3.怎么解决跨库表的join问题
https://developer.aliyun.com/article/248259
4.是否支持分布式部署的库表，在这一层上有容灾设计吗
https://www.infoq.cn/article/solution-of-distributed-system-transaction-consistency/
5.支不支持数据库连接池和ORM框架
6.如果查询时需要新的id是不是需要算回来？？
https://zhuanlan.zhihu.com/p/136963357
