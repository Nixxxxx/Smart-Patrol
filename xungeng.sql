/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50508
Source Host           : localhost:3306
Source Database       : xungeng

Target Server Type    : MYSQL
Target Server Version : 50508
File Encoding         : 65001

Date: 2017-05-19 00:03:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(256) NOT NULL COMMENT '密码(存储有salt)',
  `mobile` char(11) NOT NULL COMMENT '手机',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱(可为空)',
  `extra` varchar(256) NOT NULL DEFAULT '' COMMENT '备注(可为空)',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `last_login_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后登录时间',
  `last_login_ip` char(12) NOT NULL DEFAULT '' COMMENT '最后登录IP',
  `type` tinyint(4) NOT NULL DEFAULT '2' COMMENT '用户类型 1超级管理员 2管理  3 查看',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户状态 1正常 2已删除',
  PRIMARY KEY (`id`),
  KEY `index_status` (`status`),
  KEY `index_name` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('100', 'admin', '21232f297a57a5a743894a0e4a801fc3', '15387574162', 'yuan@hicoder.cn', '超级管理员', '2017-03-19 22:05:43', '2017-03-20 03:08:48', '', '1', '1');
INSERT INTO `admin` VALUES ('102', 'admmm', '81dc9bdb52d04dc20036dbd8313ed055', '21', '11', '1211', '2017-05-08 23:30:34', '2017-05-08 22:27:12', '1.1.1', '2', '1');

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `number` varchar(64) NOT NULL COMMENT '设备号',
  `name` varchar(64) NOT NULL COMMENT '设备名',
  `extra` varchar(256) NOT NULL DEFAULT '' COMMENT '备注(可为空)',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1正常 2已删除',
  PRIMARY KEY (`id`),
  KEY `index_status` (`status`),
  KEY `index_number` (`number`)
) ENGINE=MyISAM AUTO_INCREMENT=111 DEFAULT CHARSET=utf8 COMMENT='设备表';

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('110', '1996', '设备十三号', '我是设备十三号', '2017-05-07 15:14:50', '1');
INSERT INTO `device` VALUES ('108', '131', '设备一号', '我是设备一号', '2017-05-07 15:13:29', '1');
INSERT INTO `device` VALUES ('109', '186', '设备七号', '我是设备七号', '2017-05-07 15:13:53', '1');

-- ----------------------------
-- Table structure for location
-- ----------------------------
DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `number` varchar(64) NOT NULL COMMENT '地点编号',
  `name` varchar(64) NOT NULL COMMENT '地点名',
  `extra` varchar(256) NOT NULL DEFAULT '' COMMENT '备注(可为空)',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1正常 2已删除',
  PRIMARY KEY (`id`),
  KEY `index_status` (`status`),
  KEY `index_number` (`number`)
) ENGINE=MyISAM AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 COMMENT='地点表';

-- ----------------------------
-- Records of location
-- ----------------------------
INSERT INTO `location` VALUES ('105', '2015004', '行健轩4', '行健轩4', '2017-05-07 15:15:28', '1');
INSERT INTO `location` VALUES ('106', '2015001', '行健轩1', '行健轩1', '2017-05-07 15:15:49', '1');
INSERT INTO `location` VALUES ('107', '2117003', '弘毅轩5', '弘毅轩5', '2017-05-07 15:16:13', '1');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '人员ID',
  `number` varchar(64) NOT NULL COMMENT '工号',
  `name` varchar(64) NOT NULL COMMENT '姓名',
  `mobile` char(11) NOT NULL COMMENT '手机',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱(可为空)',
  `extra` varchar(256) NOT NULL DEFAULT '' COMMENT '备注(可为空)',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1正常 2已删除',
  PRIMARY KEY (`id`),
  KEY `index_status` (`status`),
  KEY `index_number` (`number`)
) ENGINE=MyISAM AUTO_INCREMENT=115 DEFAULT CHARSET=utf8 COMMENT='人员表';

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('110', '001123132131231', '张三', '15616161616', '15616161616@qq.com', '我是张三', '2017-05-07 15:11:04', '1');
INSERT INTO `member` VALUES ('111', '002', '李四', '17271712132', 'hdjs@163.com', '我是李斯', '2017-05-07 15:12:47', '1');
INSERT INTO `member` VALUES ('114', '12', '12', '12', '', '', '2017-05-08 23:51:49', '1');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `device_id` int(10) unsigned NOT NULL COMMENT '设备ID',
  `location_id` int(10) unsigned NOT NULL COMMENT '地点ID',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `index_device_id` (`device_id`),
  KEY `index_location_id` (`location_id`)
) ENGINE=MyISAM AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='巡更记录表';

-- ----------------------------
-- Records of record
-- ----------------------------

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `member_id` int(10) unsigned NOT NULL COMMENT '人员ID',
  `device_id` int(10) unsigned NOT NULL COMMENT '设备ID',
  `location_id` int(10) unsigned NOT NULL COMMENT '地点ID',
  `start_datetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '开始时间',
  `end_datetime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '结束时间',
  `extra` varchar(256) NOT NULL DEFAULT '' COMMENT '备注(可为空)',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `org_stage` tinyint(4) NOT NULL DEFAULT '1' COMMENT '阶段 1待完成 2已完成 3未按时完成 4未完成',
  `now_stage` tinyint(4) NOT NULL DEFAULT '1' COMMENT '阶段 1待完成 2已完成 3未按时完成 4未完成',
  `record_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '记录ID',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1正常 2已删除',
  PRIMARY KEY (`id`),
  KEY `index_member_id` (`member_id`),
  KEY `index_device_id` (`device_id`),
  KEY `index_location_id` (`location_id`),
  KEY `index_now_stage` (`now_stage`),
  KEY `index_record_id` (`record_id`),
  KEY `index_status` (`status`)
) ENGINE=MyISAM AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COMMENT='巡更任务表';

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('100', '110', '108', '105', '2017-05-17 00:00:00', '2017-05-17 02:10:00', 'aa', '2017-05-07 20:14:06', '1', '1', '0', '1');
INSERT INTO `task` VALUES ('101', '110', '108', '105', '2017-05-17 12:00:00', '2017-05-17 14:00:00', 'aa', '2017-05-07 20:14:06', '1', '1', '0', '1');
INSERT INTO `task` VALUES ('102', '110', '108', '107', '2017-05-17 08:20:00', '2017-05-17 10:20:00', 'aa', '2017-05-07 20:14:06', '1', '1', '0', '1');

-- ----------------------------
-- Table structure for time
-- ----------------------------
DROP TABLE IF EXISTS `time`;
CREATE TABLE `time` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `start_time` time NOT NULL COMMENT '开始时间',
  `end_time` time NOT NULL COMMENT '结束时间',
  `extra` varchar(256) NOT NULL DEFAULT '' COMMENT '备注(可为空)',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1正常 2已删除',
  PRIMARY KEY (`id`),
  KEY `index_status` (`status`)
) ENGINE=MyISAM AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 COMMENT='时间范围表';

-- ----------------------------
-- Records of time
-- ----------------------------
INSERT INTO `time` VALUES ('111', '00:00:00', '00:00:00', '', '2017-05-07 20:22:35', '1');
INSERT INTO `time` VALUES ('107', '00:00:00', '02:10:00', '凌晨', '2017-05-07 15:17:20', '1');
INSERT INTO `time` VALUES ('108', '08:20:00', '10:20:00', '上午', '2017-05-07 15:17:37', '1');
INSERT INTO `time` VALUES ('109', '12:00:00', '14:00:00', '中午', '2017-05-07 15:17:52', '1');
INSERT INTO `time` VALUES ('110', '08:50:00', '11:00:00', '晚上', '2017-05-07 15:18:12', '1');
