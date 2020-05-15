/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.22 : Database - wsm-upms
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `flyway_schema_history` */

DROP TABLE IF EXISTS `flyway_schema_history`;

CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `type` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `script` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `flyway_schema_history` */

/*Table structure for table `gateway_api_define` */

DROP TABLE IF EXISTS `gateway_api_define`;

CREATE TABLE `gateway_api_define` (
  `id` varchar(50) NOT NULL,
  `path` varchar(255) NOT NULL,
  `service_id` varchar(50) DEFAULT '',
  `url` varchar(255) DEFAULT '',
  `retryable` tinyint(1) DEFAULT '1',
  `enabled` tinyint(1) DEFAULT '1' COMMENT '是否有效',
  `strip_prefix` int(11) DEFAULT '1',
  `api_name` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `gateway_api_define` */

insert  into `gateway_api_define`(`id`,`path`,`service_id`,`url`,`retryable`,`enabled`,`strip_prefix`,`api_name`) values ('1','/wsm-oauth/**','wsm-oauth','',1,1,1,''),('2','/wsm-upms/**','wsm-upms','',1,1,1,''),('3','/wsm-work/**','wsm-work','',1,1,1,''),('4','/wsm-public/**','wsm-public','',1,1,1,''),('5','/wsm-demo/**','wsm-demo','',1,1,1,'');

/*Table structure for table `oauth_access_token` */

DROP TABLE IF EXISTS `oauth_access_token`;

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `client_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  KEY `token_id_index` (`token_id`),
  KEY `authentication_id_index` (`authentication_id`),
  KEY `user_name_index` (`user_name`),
  KEY `client_id_index` (`client_id`),
  KEY `refresh_token_index` (`refresh_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `oauth_access_token` */

/*Table structure for table `oauth_client_details` */

DROP TABLE IF EXISTS `oauth_client_details`;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) CHARACTER SET utf8 NOT NULL,
  `resource_ids` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `client_secret` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `scope` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `authorities` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` text CHARACTER SET utf8,
  `autoapprove` varchar(255) CHARACTER SET utf8 DEFAULT 'false',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `oauth_client_details` */

insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`autoapprove`) values ('client_oauth','resource_oauth','secret_wsm','scope_wsm','client_credentials,authorization_code,password,refresh_token',NULL,NULL,0,0,NULL,'false'),('client_upms','resource_upms,resource_oauth,resource_work','secret_wsm','scope_wsm','client_credentials,authorization_code,password,refresh_token',NULL,NULL,0,0,NULL,'false'),('client_work','resource_work,resource_oauth','secret_wsm','scope_wsm','client_credentials,authorization_code,password,refresh_token',NULL,NULL,0,0,NULL,'false');

/*Table structure for table `oauth_code` */

DROP TABLE IF EXISTS `oauth_code`;

CREATE TABLE `oauth_code` (
  `code` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `authentication` blob,
  KEY `code_index` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `oauth_code` */

/*Table structure for table `oauth_refresh_token` */

DROP TABLE IF EXISTS `oauth_refresh_token`;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `token` blob,
  `authentication` blob,
  KEY `token_id_index` (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `oauth_refresh_token` */

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `code` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '编码（网关前缀）',
  `region_id` bigint(20) DEFAULT NULL COMMENT '域id',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_enabled` tinyint(4) DEFAULT '1' COMMENT '是否有效(0无效，1有效)',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `region_id` (`region_id`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='项目（如公司里某个项目）';

/*Data for the table `project` */

insert  into `project`(`id`,`name`,`code`,`region_id`,`create_by`,`create_time`,`update_by`,`update_time`,`is_enabled`,`remark`) values (1,'upms服务','wsm-upms',1,'wsm','2019-07-18 11:36:48',NULL,NULL,1,NULL),(2,'work服务','wsm-work',1,'wsm','2019-08-01 17:52:12',NULL,NULL,1,NULL);

/*Table structure for table `region` */

DROP TABLE IF EXISTS `region`;

CREATE TABLE `region` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `code` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '编码',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父级id',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_enabled` tinyint(4) DEFAULT '1' COMMENT '是否有效(0无效，1有效)',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `region_name` (`name`),
  UNIQUE KEY `region_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='域（如一个公司）';

/*Data for the table `region` */

insert  into `region`(`id`,`name`,`code`,`parent_id`,`create_by`,`create_time`,`update_by`,`update_time`,`is_enabled`,`remark`) values (1,'wsm域','wsm-region',0,'wsm','2019-07-18 11:35:49',NULL,NULL,1,NULL);

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `code` varchar(40) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '编码',
  `button` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '按钮（页面要权限控制的按钮,也是后台接口方法名,多个用逗号分隔）',
  `url_pattern` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '权限API路径匹配(注：即后台控制器名，后面带authority,或者authority_button(对应button字段)代表受权限控制)',
  `project_id` bigint(20) DEFAULT NULL COMMENT '项目id',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_enabled` tinyint(4) DEFAULT '1' COMMENT '是否有效(0无效，1有效)',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `resource_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='资源（如前台某个菜单）';

/*Data for the table `resource` */

insert  into `resource`(`id`,`name`,`code`,`button`,`url_pattern`,`project_id`,`create_by`,`create_time`,`update_by`,`update_time`,`is_enabled`,`remark`) values (1,'用户管理','upms_user','add,update,deleteById,deleteByIds','/user',1,'wsm','2019-07-18 13:43:07',NULL,NULL,1,NULL),(2,'角色管理','upms_role','add,update,deleteById,deleteByIds','/role',1,'wsm','2019-07-18 17:18:58',NULL,NULL,1,NULL),(3,'域管理','upms_region','add,update,deleteById,deleteByIds','/region',1,'wsm','2019-08-01 17:56:11',NULL,NULL,1,NULL),(4,'项目管理','upms_project','add,update,deleteById,deleteByIds','/project',1,'wsm','2019-08-01 17:55:29',NULL,NULL,1,NULL),(5,'资源管理','upms_resource','add,update,deleteById,deleteByIds','/resource',1,'wsm','2019-08-01 17:57:07',NULL,NULL,1,NULL),(11,'招聘平台管理','work_recruitPlatform','add,update,deleteById,deleteByIds','/recruitPlatform',2,'wsm','2019-08-02 09:20:51',NULL,NULL,1,NULL),(12,'职位管理','work_job','add,update,deleteById,deleteByIds','/job',2,'wsm','2019-08-02 09:22:40',NULL,NULL,1,NULL);

/*Table structure for table `resource_role` */

DROP TABLE IF EXISTS `resource_role`;

CREATE TABLE `resource_role` (
  `resource_id` bigint(20) NOT NULL COMMENT '资源id',
  `resource_button` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '资源按钮（页面要权限控制的按钮,也是后台接口方法名,多个用逗号分隔）',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  KEY `user_group_id` (`role_id`),
  KEY `resource_id` (`resource_id`),
  CONSTRAINT `resource_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `resource_role_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='资源-角色-关系表';

/*Data for the table `resource_role` */

insert  into `resource_role`(`resource_id`,`resource_button`,`role_id`) values (1,'add,update,deleteById,deleteByIds',1),(1,'',2),(2,'add,update,deleteById,deleteByIds',1),(3,'add,update,deleteById,deleteByIds',1),(4,'add,update,deleteById,deleteByIds',1),(5,'add,update,deleteById,deleteByIds',1),(11,'add,update,deleteById,deleteByIds',1),(12,'add,update,deleteById,deleteByIds',1);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `code` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '编码',
  `project_id` bigint(20) DEFAULT NULL COMMENT '项目id',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_enabled` tinyint(4) DEFAULT '1' COMMENT '是否有效(0无效，1有效)',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `role_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='角色';

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`code`,`project_id`,`create_by`,`create_time`,`update_by`,`update_time`,`is_enabled`,`remark`) values (1,'wsm域_超级管理员','wsm_region_manage',1,'wsm','2019-07-18 11:42:24',NULL,NULL,1,NULL),(2,'upms_管理员','upms_manage',1,'wsm','2019-07-18 11:42:26',NULL,NULL,1,NULL),(3,'work_管理员','work_manage',2,'wsm','2019-08-02 09:25:28',NULL,NULL,1,NULL);

/*Table structure for table `undo_log` */

DROP TABLE IF EXISTS `undo_log`;

CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `undo_log` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `nickname` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '昵称',
  `password` varchar(200) COLLATE utf8_unicode_ci NOT NULL DEFAULT '123456' COMMENT '密码',
  `photo` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '头像',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别(0女,1男)',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `phone` char(11) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '手机号',
  `mail` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '邮箱',
  `address` varchar(100) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '地址',
  `explain` varchar(500) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '个人说明',
  `user_source` tinyint(4) DEFAULT NULL COMMENT '用户来源',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户状态（1：正常，2：异常，3：禁用）',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`nickname`,`password`,`photo`,`sex`,`birthday`,`phone`,`mail`,`address`,`explain`,`user_source`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values (1,'wsm1','王帅逼1','123456',NULL,NULL,'2019-10-23',NULL,NULL,NULL,'1231',NULL,1,NULL,'2019-10-25 17:45:44',NULL,'2019-10-25 08:37:50',NULL),(2,'wsm2','王帅逼2','123456',NULL,NULL,'2019-10-23',NULL,NULL,NULL,NULL,NULL,1,NULL,'2019-10-23 17:45:45',NULL,'2019-10-22 08:37:50',NULL),(3,'wsm3',NULL,'123456',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  KEY `user_group_id` (`role_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户-角色-关系表';

/*Data for the table `user_role` */

insert  into `user_role`(`user_id`,`role_id`) values (1,1),(2,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
