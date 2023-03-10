create table platform.system_user_role
(
    id      bigint auto_increment comment '主键'
        primary key,
    user_id bigint null comment '用户ID',
    role_id bigint null comment '角色ID'
)
    comment '用户和角色的中间表';

