create table system_role
(
    id           bigint auto_increment comment '主键',
    name         varchar(50)                        null comment '名称',
    `key`        varchar(50)                        null comment '代表权限的字符串',
    sort         int      default 0                 null comment '排序',
    is_deleted   tinyint  default 0                 null comment '是否删除 0-未删除 1-已删除',
    created_by   bigint                             null comment '创建人ID',
    created_time datetime default current_timestamp null comment '创建时间',
    updated_by   bigint                             null comment '修改人ID',
    updated_time datetime default current_timestamp null comment '修改时间',
    remark       varchar(200)                       null comment '备注',
    constraint system_role_pk
        primary key (id)
)
    comment '角色表';

