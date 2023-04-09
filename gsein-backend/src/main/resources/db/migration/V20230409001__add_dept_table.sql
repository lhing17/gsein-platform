create table system_dept
(
    id           bigint auto_increment comment '主键',
    name         varchar(50)                        null comment '部门名称',
    parent_id    bigint                             null comment '父级部门ID',
    ancestors    varchar(200)                       null comment '部门层级路径（逗号分隔，不包含本级）',
    order_num    int                                null comment '排序',
    created_by   bigint                             null comment '创建者',
    created_time datetime default current_timestamp null comment '创建时间',
    updated_by   bigint                             null comment '修改者',
    updated_time datetime default current_timestamp null comment '修改时间',
    is_deleted   tinyint                            null comment '是否删除 0-未删除 1-已删除',
    remark       varchar(500)                       null comment '备注',
    constraint system_dept_pk
        primary key (id)
)
    comment '部门表';

