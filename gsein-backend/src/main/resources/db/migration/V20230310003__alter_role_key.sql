alter table system_role
    change `key` role_key varchar(50) null comment '代表权限的字符串';
