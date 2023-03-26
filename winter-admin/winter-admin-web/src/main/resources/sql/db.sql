create database if not exists winter_admin;
use winter_admin;

create table admin_user (
                           id bigint(20) not null primary key auto_increment,
                           email varchar(64) not null comment '邮箱',
                           password varchar(64) not null comment '密码',
                           create_time timestamp(3) not null default current_timestamp(3) comment '创建时间',
                           update_time timestamp(3) not null default current_timestamp(3) on update current_timestamp(3) comment '更新时间',
                           unique key `uniq_email_idx`(`email`)
) comment '用户信息表';

create table role_info (
                           id int(11) not null primary key auto_increment,
                           role_name varchar(20) not null comment '角色名称',
                           is_super_admin tinyint(1) default 0 not null comment '是否是超管 1 是 0 否',
                           create_time timestamp(3) not null default current_timestamp(3) comment '创建时间',
                           update_time timestamp(3) not null default current_timestamp(3) on update current_timestamp(3) comment '更新时间'
) comment '角色信息表';

create table permission_info (
                                 id int(11) not null primary key auto_increment,
                                 permission_name varchar(20) not null comment '角色名称',
                                 uri varchar(255) not null comment 'url地址',
                                 create_time timestamp(3) not null default current_timestamp(3) comment '创建时间',
                                 update_time timestamp(3) not null default current_timestamp(3) on update current_timestamp(3) comment '更新时间'
) comment '权限信息表';

create table user_role (
                           id bigint(20) not null primary key auto_increment,
                           admin_user_id bigint(20) not null comment '用户id',
                           role_id int(11) not null comment '角色id',
                           create_time timestamp(3) not null default current_timestamp(3) comment '创建时间',
                           update_time timestamp(3) not null default current_timestamp(3) on update current_timestamp(3) comment '更新时间',
                           unique key `user_role_id`(`admin_user_id`, `role_id`)
) comment '用户角色表';

create table role_permission (
                                 id bigint(20) not null primary key auto_increment,
                                 role_id int(11) not null comment '角色id',
                                 permission_id int(11) not null comment '权限id',
                                 create_time timestamp(3) not null default current_timestamp(3) comment '创建时间',
                                 update_time timestamp(3) not null default current_timestamp(3) on update current_timestamp(3) comment '更新时间',
                                 unique key `role_permission_id`(`role_id`, `permission_id`)
) comment '角色权限表';

insert into admin_user (`id`, `email`, password) values (1, 'tony@sina.co', '123456');
insert into admin_user (`id`, `email`, password) values (2, 'laoshi@sina.co', '123456');
insert into role_info (`id`, `role_name`, `is_super_admin`) values (1, '超级管理员', 1);
insert into role_info (`id`, `role_name`, `is_super_admin`) values (2, '运营人员', 0);
insert into permission_info (`id`, `permission_name`, `uri`) values (1, '查询用户详情', '/user/detail');
insert into role_permission (`role_id`, `permission_id`) values (2, 1);
insert into user_role (`admin_user_id`, `role_id`) values (1, 1);
insert into user_role (`admin_user_id`, `role_id`) values (2, 2);