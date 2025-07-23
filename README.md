# gsein-platform（吉森开发平台）
吉森开发平台是一个后台管理系统的脚手架项目，可以在本项目的基础上快速开始后端管理平台的开发。

## 本项目的特性
1. 从零开始搭建的开发平台，主要搭建过程都在B站通过视频方式进行了记录，B站视频地址：https://www.bilibili.com/video/BV1ee4y1c7ub/?vd_source=6e917eace06e9e149bf0c2eaf681f7af
2. 本平台致力于满足各种快速开发的场景，以简单和高效为主要出发点，不追求过于复杂的架构和技术。
3. 平台支持代码生成，常用的代码均可以通过代码生成器生成，大大减少了编写样本代码的时间。
4. 支持记录系统运行日志、业务操作日志（包括登录、操作、异常等），方便问题的调试。
5. 快速创建定时任务。
6. 在线展示接口文档，接口文档支持接口调试。
7. 支持对数据库连接的监控。
8. 支持基于角色和权限的细粒度的系统鉴权。

## 内置功能模块
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
- knife4j作为接口文档的门面
- alibaba druid作为数据库连接池
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

## 项目运行(开发环境)
### 后端项目
1. 创建数据库platform
2. 修改application.yml中的数据库连接信息
3. 使用IDEA打开项目，运行GseinPlatformApplication.java，或者使用maven命令：mvn spring-boot:run
4. 访问http://localhost:8888/doc.html，查看接口文档是否能够正常访问

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
5. 访问http://ip:8888/doc.html，查看接口文档是否能够正常访问
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



## QQ交流群
欢迎加入QQ群进行技术交流：[![加入QQ群](https://img.shields.io/badge/763122883-blue.svg)](https://jq.qq.com/?_wv=1027&k=GfZCRmow)
