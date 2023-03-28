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
### 后端技术
从零开始搭建的maven工程
- Spring boot 基础框架
- Spring security 安全框架，用于身份认证和鉴权
- Spring data jpa 持久层框架，底层为hibernate实现
- JWT 生成token
- Flyway 数据库迁移(migration)
- 使用xxl-job作为定时任务调度器
- ...

### 前端技术
使用Vuestic Admin作为前端模板
- Vue.js 前端框架 Vue3.x版本，采用Composition API（组合式）的代码风格
- vite 前端构建工具
- Vuestic UI 前端UI框架
- pinia 状态管理
- axios 网络请求
- yarn/npm 包管理工具
- ...

## 项目结构
```
├─gsein-platform
│  ├─doc 项目文档
|  ├─gsein-backend 后端项目
|  ├─gsein-frontend 前端项目
|  ├─xxl-job-admin 定时任务调度器
```

## 项目运行
### 后端项目
1. 创建数据库platform
2. 修改application.yml中的数据库连接信息
3. 使用IDEA打开项目，运行GseinPlatformApplication.java，或者使用maven命令：mvn spring-boot:run
4. 访问http://localhost:8080/doc.html，查看接口文档是否能够正常访问

### 前端项目
1. 安装node.js
2. 安装yarn
3. 进入gsein-frontend目录，执行yarn install
4. 执行yarn dev
5. 访问http://localhost:3000，查看前端页面是否能够正常访问

### 定时任务调度器
1. 执行doc/db/tables_xxl_job.sql，创建数据库和表
2. 修改application.properties中的数据库连接信息
3. 使用IDEA打开项目，运行XxlJobAdminApplication.java，或者使用maven命令：mvn spring-boot:run
4. 访问http://localhost:8081/xxl-job-admin，查看定时任务调度器页面是否能够正常访问，默认账号密码为admin/123456
5. 查看控制台日志，查看后端项目是否能够正常调用定时任务调度器
6. 查看数据库表xxl_job_log，查看定时任务调度器是否能够正常记录日志

## 项目部署
### 后端项目
1. 修改application.yml中的数据库连接信息
2. 使用maven命令：mvn clean package -Dmaven.test.skip=true
3. 将target目录下的gsein-platform-0.0.1-SNAPSHOT.jar改名后拷贝到服务器上
4. 使用nohup命令启动jar包：nohup java -jar XXX.jar &
5. 访问http://ip:8080/doc.html，查看接口文档是否能够正常访问
6. 查看控制台日志，查看后端项目是否能够正常启动

### 前端项目
1. 安装node.js
2. 安装yarn
3. 进入gsein-frontend目录，执行yarn install
4. 执行yarn build
5. 将dist目录下的文件拷贝到nginx服务器上，配置nginx的反向代理
6. 访问nginx地址，查看前端页面是否能够正常访问

### 定时任务调度器
1. 在服务器的数据库中执行doc/db/tables_xxl_job.sql，创建数据库和表
2. 修改application.properties中的数据库连接信息
3. 使用maven命令：mvn clean package -Dmaven.test.skip=true
4. 将target目录下的xxl-job-admin-2.3.0.jar改名后拷贝到服务器上
5. 使用nohup命令启动jar包：nohup java -jar XXX.jar &
6. 访问http://ip:8081/xxl-job-admin，查看定时任务调度器页面是否能够正常访问
7. 查看控制台日志，查看后端项目是否能够正常调用定时任务调度器
8. 查看数据库表xxl_job_log，查看定时任务调度器是否能够正常记录日志

## 项目演示地址
待补充

## 问题解决记录(trouble shooting)
1. 实体中的字段为null时，会导致sql语句中的字段值为null，而不是数据库中的默认值。
    - 可以使用@DynamicInsert@DynamicUpdate注解，但是这样会导致没有办法将字段值更新为null。

2. BaseEntity中的字段没有和数据库中的字段形成映射。
    - 在BaseEntity中添加@MappedSuperclass注解，表示该类是一个基类。

3. 关联关系的实体在序列化时会出现死循环。
    - 在实体类的字段上添加@JsonIgnore注解，表示该字段不参与序列化。
    - 在实体类中添加@JsonBackReference注解，表示该字段不参与序列化。该注解与@JsonManagedReference注解配合使用。



## QQ交流群
欢迎加入QQ群进行技术交流：[![加入QQ群](https://img.shields.io/badge/763122883-blue.svg)](https://jq.qq.com/?_wv=1027&k=GfZCRmow)
