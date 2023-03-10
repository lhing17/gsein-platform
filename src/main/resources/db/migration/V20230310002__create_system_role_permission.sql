create table system_role_permission
(
    id            bigint auto_increment comment '主键'  primary key,
    role_id       bigint null comment '角色ID',
    permission_id bigint null comment '权限ID'
)
    comment '角色权限中间表';
