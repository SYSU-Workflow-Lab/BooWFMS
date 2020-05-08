# Todo List

## To Be finished By Skye
- DAOException、ServiceFailureException使用推广
- AuthDomainHelper用法纠正

## Future Work
- 原始项目core包存在方法名和属性名开头大写的情况，需要纠正
- 原始项目内多次使用set集合类存储数据，尽量改为list
- 注释统一英文或中文或中英文均有，自行决定
- DAO层实现目前是使用JDBC，没有用JPA是因为hibernate缓存有时候出现不一致问题，如果有好的想法可以进行更换
- 数据库查询分页查询优化
- 资源服务可以被设计成无状态服务集群，可以以此为研究生毕业设计（？）
- 原始项目资源服务中ContextLockManager是否可以有替换方案
- 登录后生成Token替换现有的accountId
- Redis分布式数据缓存
- ElasticSearch日志管理
- Jenkins使用CI/CD