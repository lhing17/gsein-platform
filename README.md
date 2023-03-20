# gsein-platform
从零开始搭建开发平台

所有搭建过程在B站通过视频方式记录

B站视频地址：https://www.bilibili.com/video/BV1ee4y1c7ub/?vd_source=6e917eace06e9e149bf0c2eaf681f7af

## 规划特性
### 系统层面
1. 支持代码生成
2. 日志：系统日志、业务日志（登录、操作、异常）
3. 定时任务
4. 接口文档
5. 监控数据库连接池状态
6. 系统鉴权——对应用户、角色、权限管理

### 业务层面
1. 用户管理
2. 角色管理/权限管理
3. 菜单管理
4. 部门管理
5. 数据字典
6. 系统通知
7. 文件服务
8. 其他常用功能：系统参数配置、在线状态监控

## 技术选型
- Spring boot 基础框架
- Spring security 安全框架，用于身份认证和鉴权
- Spring data jpa 持久层框架，底层为hibernate实现
- JWT 生成token
- Flyway 数据库迁移(migration)
- ...

## 问题解决记录(trouble shooting)
1. 实体中的字段为null时，会导致sql语句中的字段值为null，而不是数据库中的默认值。
    - 可以使用@DynamicInsert@DynamicUpdate注解，但是这样会导致没有办法将字段值更新为null。

2. BaseEntity中的字段没有和数据库中的字段形成映射。
    - 在BaseEntity中添加@MappedSuperclass注解，表示该类是一个基类。

3. 关联关系的实体在序列化时会出现死循环。
    - 在实体类的字段上添加@JsonIgnore注解，表示该字段不参与序列化。
    - 在实体类中添加@JsonBackReference注解，表示该字段不参与序列化。该注解与@JsonManagedReference注解配合使用。



## QQ交流群
[![加入QQ群](https://img.shields.io/badge/763122883-blue.svg)](https://jq.qq.com/?_wv=1027&k=GfZCRmow)
