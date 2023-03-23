create table system_operation_log
(
    id             bigint auto_increment              not null comment '主键',
    description    varchar(50)                        null comment '操作内容描述',
    username       varchar(30)                        null comment '操作用户名',
    ip             varchar(50)                        null comment 'IP地址',
    method         varchar(30)                        null comment '方法名',
    params         varchar(2000)                      null comment '参数',
    result         varchar(2000)                      null comment '返回结果',
    time           datetime default current_timestamp null comment '操作时间',
    duration       bigint                             null comment '耗时',
    operation_type int                                null comment '操作类型',
    is_deleted     tinyint                            null comment '是否删除 0-未删除 1-已删除',
    created_by     bigint                             null comment '创建人',
    created_time   datetime default current_timestamp null comment '创建时间',
    updated_by     bigint                             null comment '修改人',
    updated_time   datetime default current_timestamp null comment '修改时间',
    remark         varchar(200)                       null comment '备注',
    constraint system_operation_log_pk
        primary key (id)
)
    comment '系统操作日志';

