# Todo List

## 历史遗留问题

-[ ] boo-engine模块和boo-resource模块的core包存在方法名和属性名开头大写的情况，需要纠正
-[ ] 项目内多次使用set集合类存储数据，改为list
-[ ] 注释统一语言，目前英文或中文或中英文均有，自行决定
-[ ] 数据库查询分页查询实现，已在```BooPagedQuery```类留下分页数据接收属性
-[ ] 资源服务可以被设计成无状态服务集群（？）
-[ ] boo-resource模块中使用ContextLockManager加锁，探索是否可以有替换方案
-[ ] 众包样例项目，可以实现在example下
-[ ] 项目内众多TODO，慢慢发掘吧少年们！（笑


## 未来计划

-[ ] 登录后生成Token替换现有的accountId
-[ ] 微服务架构其他组件补充，配置中心、分布式链路追踪等
-[ ] 和微服务小组合作，部署到华为云k8s
-[ ] Redis分布式数据缓存
-[ ] ElasticSearch日志管理
-[ ] Jenkins使用CI/CD
-[ ] DAO层实现目前是使用JDBC，没有用JPA是因为hibernate缓存有时候出现不一致问题，如果有好的想法可以进行更换
