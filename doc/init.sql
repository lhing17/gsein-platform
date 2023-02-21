# 建表语句
create table platform.system_user
(
    id           bigint auto_increment comment '主键'
        primary key,
    username     varchar(30)                        null comment '用户名',
    password     varchar(100)                       null comment '密码',
    gender       tinyint                            null comment '性别 0-女 1-男',
    id_card      varchar(30)                        null comment '身份证号',
    email        varchar(50)                        null comment '邮箱',
    phone        varchar(30)                        null comment '电话',
    birthday     datetime                           null comment '生日',
    status       tinyint  default 1                 null comment '用户状态——1-正常 2-禁用',
    last_login   datetime                           null comment '上次登录时间',
    created_by   bigint                             null comment '创建人的ID',
    created_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updated_by   bigint                             null comment '修改人ID',
    updated_time datetime default CURRENT_TIMESTAMP null comment '修改时间',
    is_deleted   tinyint  default 0                 null comment '逻辑删除的状态 0-未删除 1-已删除',
    remark       varchar(200)                       null comment '备注'
)
    comment '用户表';



