/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.22 : Database - wsm-work
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wsm-work` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `wsm-work`;

/*Table structure for table `job` */

DROP TABLE IF EXISTS `job`;

CREATE TABLE `job` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_enabled` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否有效(0无效，1有效)',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `job_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='职位表';

/*Data for the table `job` */

insert  into `job`(`id`,`name`,`create_by`,`create_time`,`update_by`,`update_time`,`is_enabled`,`remark`) values (1,'1','1','2019-02-23 16:21:48',NULL,'2019-02-23 16:21:51',1,NULL),(2620619105830180738,'（seata）job测试分布式事务482',NULL,'2019-08-30 10:07:39',NULL,'2019-08-30 10:07:39',1,NULL);

/*Table structure for table `recruit_platform` */

DROP TABLE IF EXISTS `recruit_platform`;

CREATE TABLE `recruit_platform` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_enabled` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否有效(0无效，1有效)',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='招聘平台表';

/*Data for the table `recruit_platform` */

insert  into `recruit_platform`(`id`,`name`,`create_by`,`create_time`,`update_by`,`update_time`,`is_enabled`,`remark`) values (1,'1',NULL,NULL,NULL,NULL,1,NULL);

/*Table structure for table `work` */

DROP TABLE IF EXISTS `work`;

CREATE TABLE `work` (
  `id` bigint(20) NOT NULL,
  `recruit_platform_id` bigint(20) NOT NULL COMMENT '招聘平台id',
  `job_id` bigint(20) NOT NULL COMMENT '职位id',
  `site` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '地点',
  `date` date DEFAULT NULL COMMENT '日期',
  `condition` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '条件',
  `job_number` int(11) DEFAULT NULL COMMENT '职位条数',
  `create_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_enabled` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否有效(0无效，1有效)',
  `remark` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `recruit_platform_id` (`recruit_platform_id`),
  KEY `job_id` (`job_id`),
  CONSTRAINT `work_ibfk_1` FOREIGN KEY (`recruit_platform_id`) REFERENCES `recruit_platform` (`id`),
  CONSTRAINT `work_ibfk_2` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='职业信息表';

/*Data for the table `work` */

insert  into `work`(`id`,`recruit_platform_id`,`job_id`,`site`,`date`,`condition`,`job_number`,`create_by`,`create_time`,`update_by`,`update_time`,`is_enabled`,`remark`) values (1,1,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
