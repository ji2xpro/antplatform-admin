/*
 AntPlatform 自动化测试平台 SQL脚本初始化
 Version   1.0.0
 Author    JIMMY

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : 127.0.0.1:3306
 Source Schema         : AntPlatform

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date      12/01/2020 15:14:18

 ************  WARNING  ************   
 执行此脚本，会导致数据库所有数据初始化，初次执行，请慎重！！！！
*/

-- DROP DATABASE IF EXISTS AntPlatform;
CREATE DATABASE IF NOT EXISTS AntPlatform DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
-- CREATE DATABASE IF NOT EXISTS AntPlatform CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';

USE AntPlatform;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(225) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) DEFAULT 'http://news.mydrivers.com/Img/20110518/04481549.png' COMMENT '用户头像',
  `remark` varchar(255) DEFAULT NULL COMMENT '用户备注',
  `status` tinyint(2) unsigned NOT NULL DEFAULT 0 COMMENT '状态,0-启用,1-禁用',
  `deleted` tinyint(2) unsigned NOT NULL DEFAULT 0 COMMENT '状态,0-未删除,1-删除',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', '123456', '超级管理员', NULL, NULL, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_user` VALUES (2, 'editor', '123456', '普通用户', NULL, NULL, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '角色名称',
  `type` varchar(255) DEFAULT NULL COMMENT '角色类型',
  `introduction` varchar(255) DEFAULT NULL COMMENT '角色介绍',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '角色备注',
  `status` tinyint(2) unsigned NOT NULL DEFAULT 0 COMMENT '状态,0-启用,-1禁用',
  `deleted` tinyint(2) unsigned NOT NULL DEFAULT 0 COMMENT '状态,0-未删除,1-删除',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, 'Super Admin', 'admin', 'I am a super administrator', '超级管理员', 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role` VALUES (2, 'Admin', 'admin', 'I am an administrator', '管理员', 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role` VALUES (3, 'Normal Editor', 'editor', 'I am an editor', '普通用户', 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` int(11) unsigned DEFAULT NULL COMMENT '用户Id',
  `roleId` int(11) unsigned DEFAULT NULL COMMENT '角色Id',
  `deleted` tinyint(2) unsigned NOT NULL DEFAULT 0 COMMENT '状态,0-未删除,1-删除',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_user_role` VALUES (2, 2, 3, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
