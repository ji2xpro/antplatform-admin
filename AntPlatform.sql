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

# DROP DATABASE IF EXISTS AntPlatform;
# CREATE DATABASE IF NOT EXISTS AntPlatform DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
CREATE DATABASE IF NOT EXISTS AntPlatform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE AntPlatform;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(225) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户密码',
  `nickname` varchar(255) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT 'http://news.mydrivers.com/Img/20110518/04481549.png' COMMENT '用户头像',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户备注',
  `status` int(11) unsigned NOT NULL DEFAULT 0 COMMENT '状态,0-启用,1-禁用',
  `is_delete` tinyint(4) unsigned NOT NULL DEFAULT 0 COMMENT '是否删除,0-未删除,1-删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;

INSERT INTO `sys_user` VALUES (1, 'admin', '43286a86708820e38c333cdd4c496355', '超级管理员', NULL, NULL, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_user` VALUES (2, 'editor', '123456', '普通用户', NULL, NULL, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', NULL, 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键 ',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `keypoint` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色标识',
  `type` int(11) unsigned NOT NULL DEFAULT 2 COMMENT '类型,0-超级管理员,1-管理员,2-普通用户',
  `introduction` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '介绍',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `status` int(11) unsigned NOT NULL DEFAULT 0 COMMENT '状态,0-启用,-1禁用',
  `is_delete` tinyint(4) unsigned NOT NULL DEFAULT 0 COMMENT '是否删除,0-未删除,1-删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, 'Super Admin', 'super admin', 0, 'I am a super administrator', '超级管理员', 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role` VALUES (2, 'Admin', 'admin', 1, 'I am an administrator', '系统管理员', 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role` VALUES (3, 'Normal Editor', 'editor', 2, 'I am an editor', '普通用户', 0, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) unsigned DEFAULT NULL COMMENT '用户Id',
  `role_id` int(11) unsigned DEFAULT NULL COMMENT '角色Id',
  `is_delete` tinyint(4) unsigned NOT NULL DEFAULT 0 COMMENT '是否删除,0-未删除,1-删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1, 2, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_user_role` VALUES (2, 2, 3, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '权限名称',
  `alias` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '别名',
  `parent_id` int(11) unsigned DEFAULT NULL COMMENT '权限父类ID',
  `keypoint` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '权限点',
  `type` int(11) unsigned NOT NULL DEFAULT 0 COMMENT '类型,0-模块,1-菜单,2-按钮,3-链接',
  `icon` varchar(255) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '图标',
  `path` varchar(255) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '路径',
  `url` varchar(255) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '资源路径',
  `page_name` varchar(255) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '网页名称',
  `level` int(11) unsigned DEFAULT NULL COMMENT '优先级',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '介绍',
  `status` int(11) unsigned NOT NULL DEFAULT 0 COMMENT '状态,0-启用,-1禁用',
  `is_cache` tinyint(1) unsigned DEFAULT 0 COMMENT '是否缓存,0-缓存,1-不缓存',
  `is_delete` tinyint(4) unsigned NOT NULL DEFAULT 0 COMMENT '是否删除,0-未删除,1-删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `INDEX_PERM_NAME` (`name`) USING BTREE,
  KEY `INDEX_PERM_PID` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (1, '基础系统管理', 'system',  0, 'system:mgr', 0, 'system', 'system', 'system', NULL, 1000,'基础系统管理', 0, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_permission` VALUES (2, '系统管理', 'system', 0, 'system:cfg', 0, 'system', 'system', 'Layout', NULL, 1000, '系统管理',  0, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_permission` VALUES (3, '权限管理','permission', 2, 'system:permission', 0, 'permission', 'permission', 'nested', NULL, 1, '权限管理', 0, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_permission` VALUES (4, '用户管理', 'userTable', 3, 'userTable', 1, 'userTable', 'userTable', 'system/permission/userTable', 'userTable', 1, '用户管理菜单', 0, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_permission` VALUES (5, '角色管理', 'roleTable', 3, 'roleTable', 1, 'roleTable', 'roleTable', 'system/permission/roleTable', 'roleTable', 2, '角色管理', 0, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_permission` VALUES (6, '资源管理', 'resourceTable', 3, 'resourceTable', 1, 'resourceTable', 'resourceTable', 'system/permission/resourceTable', 'resourceTable', 3, '资源管理', 0, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_permission` VALUES (7, '组织管理', 'organizationTable', 3, 'organizationTable', 1, 'organizationTable', 'organizationTable', 'system/permission/organizationTable', 'organizationTable', 4, '组织管理',0, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_permission` VALUES (8, '基础配置', 'config', 2, 'system:base:cfg', 0, 'config', 'config', 'nested','config', 2, '系统配置',0, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_permission` VALUES (9, '数据字典', 'dictTable', 8, 'system:config:dict', 1, 'dictTable', 'dictTable', 'system/config/dictTable','dictTable', 1, '数据字典',0, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_permission` VALUES (10, '定时任务', 'timingtask', 8, 'system:task', 1, 'timingtask', 'https://github.com/xuxueli/xxl-job', 'system/task', NULL, 2, '定时任务',0, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_permission` VALUES (11, '日志管理', 'log', 2, 'system:log:mgr', 0, 'log', 'log', 'nested', NULL, 3, '日志管理', 0, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_permission` VALUES (12, '操作日志', 'operationTable', 11, 'system:log:operation', 1, 'operationTable', 'operationTable', 'system/log/operationTable', 'operationTable', 1, '管理员操作日志，只记录重要关键日志，请勿频繁记录，系统运行日志记录到log文件。',0, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_permission` VALUES (13, '系统日志', 'systemTable',11, 'system:log:run', 1, 'systemTable', 'https://my.oschina.net/feinik/blog/1580625', 'systemTable', NULL, 2, '运行日志使用ELK',0, 1, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) unsigned DEFAULT NULL COMMENT '角色Id',
  `permission_id` int(11) unsigned DEFAULT NULL COMMENT '权限Id',
  `is_delete` tinyint(4) unsigned NOT NULL DEFAULT 0 COMMENT '是否删除,0-未删除,1-删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='角色权限关联表';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES (1, 2, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (2, 2, 2, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (3, 2, 3, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (4, 2, 4, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (5, 2, 5, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (6, 2, 6, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (7, 2, 7, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (8, 2, 8, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (9, 2, 9, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (10, 2, 10, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (11, 2, 11, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (12, 2, 12, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (13, 2, 13, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (14, 3, 11, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (15, 3, 12, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO `sys_role_permission` VALUES (16, 3, 13, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
