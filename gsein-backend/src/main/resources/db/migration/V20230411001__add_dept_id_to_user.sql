-- system_user表中增加dept_id字段，用于存储用户所属部门的id
alter table system_user
    add dept_id bigint comment '用户所属部门id';
