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
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wsm-upms` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `wsm-upms`;

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编码',
  `region_id` bigint(20) DEFAULT NULL COMMENT '域id',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_enabled` tinyint(4) DEFAULT '1' COMMENT '是否有效(0无效，1有效)',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `region_id` (`region_id`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='项目（如公司里某个项目）';

/*Data for the table `project` */

/*Table structure for table `region` */

DROP TABLE IF EXISTS `region`;

CREATE TABLE `region` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编码',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级id',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_enabled` tinyint(4) DEFAULT '1' COMMENT '是否有效(0无效，1有效)',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='域（如一个公司）';

/*Data for the table `region` */

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编码',
  `button` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '按钮（页面要权限控制的按钮,也是后台接口方法名,多个用逗号分隔）',
  `url_pattern` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '权限API路径匹配',
  `project_id` bigint(20) DEFAULT NULL COMMENT '项目id',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_enabled` tinyint(4) DEFAULT '1' COMMENT '是否有效(0无效，1有效)',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `resource_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='资源（如前台某个菜单）';

/*Data for the table `resource` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '昵称',
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '666' COMMENT '密码',
  `photo` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '头像',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别(0女,1男)',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `phone` char(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号',
  `mail` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `address` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '地址',
  `explain` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '个人说明',
  `user_source` tinyint(4) DEFAULT NULL COMMENT '用户来源',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户状态（1：正常，2：异常，3：禁用）',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`nickname`,`password`,`photo`,`sex`,`birthday`,`phone`,`mail`,`address`,`explain`,`user_source`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values (2507172906111144231,'string','string','string','string',0,'2019-06-11','string','string','string','string',NULL,0,'string','2019-06-11 06:42:31','string','2019-06-11 06:42:31','string');

/*Table structure for table `user_group` */

DROP TABLE IF EXISTS `user_group`;

CREATE TABLE `user_group` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '编码',
  `project_id` bigint(20) DEFAULT NULL COMMENT '项目id',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_enabled` tinyint(4) DEFAULT '1' COMMENT '是否有效(0无效，1有效)',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `user_group_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户组';

/*Data for the table `user_group` */

/*Table structure for table `user_group_resource_relation` */

DROP TABLE IF EXISTS `user_group_resource_relation`;

CREATE TABLE `user_group_resource_relation` (
  `user_group_id` bigint(20) NOT NULL COMMENT '用户组id',
  `resource_id` bigint(20) NOT NULL COMMENT '资源id',
  `resource_button` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '资源按钮（页面要权限控制的按钮,也是后台接口方法名,多个用逗号分隔）',
  KEY `user_group_id` (`user_group_id`),
  KEY `resource_id` (`resource_id`),
  CONSTRAINT `user_group_resource_relation_ibfk_1` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`id`),
  CONSTRAINT `user_group_resource_relation_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户组-资源-关系表';

/*Data for the table `user_group_resource_relation` */

/*Table structure for table `user_group_user_relation` */

DROP TABLE IF EXISTS `user_group_user_relation`;

CREATE TABLE `user_group_user_relation` (
  `user_group_id` bigint(20) NOT NULL COMMENT '用户组id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  KEY `user_group_id` (`user_group_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_group_user_relation_ibfk_1` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`id`),
  CONSTRAINT `user_group_user_relation_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户组-用户-关系表';

/*Data for the table `user_group_user_relation` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
