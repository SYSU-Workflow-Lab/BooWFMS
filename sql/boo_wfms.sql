/*
 Navicat Premium Data Transfer

 Source Server         : MYSQL57
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : boo_wfms

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 02/01/2020 11:12:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for boo_archived_tree
-- ----------------------------
DROP TABLE IF EXISTS `boo_archived_tree`;
CREATE TABLE `boo_archived_tree`  (
  `process_instance_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属流程实例ID & 数据库主键',
  `tree` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '树数据',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`process_instance_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boo_bin_step
-- ----------------------------
DROP TABLE IF EXISTS `boo_bin_step`;
CREATE TABLE `boo_bin_step`  (
  `bin_step_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库主键',
  `process_instance_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程实例ID',
  `parent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父节点ID',
  `notification_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知ID',
  `binlog` longblob NOT NULL COMMENT '内容数据',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`bin_step_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boo_business_object
-- ----------------------------
DROP TABLE IF EXISTS `boo_business_object`;
CREATE TABLE `boo_business_object`  (
  `business_object_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务对象ID',
  `business_object_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务对象名称',
  `process_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属流程ID',
  `status` int(11) NOT NULL COMMENT '业务对象状态（0-停用，1-正常）',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '业务对象模型内容',
  `serialization` blob NULL COMMENT '序列化的业务对象',
  `business_roles` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '涉及的业务角色',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`business_object_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boo_business_process
-- ----------------------------
DROP TABLE IF EXISTS `boo_business_process`;
CREATE TABLE `boo_business_process`  (
  `business_process_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务流程ID',
  `business_process_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务流程名称',
  `main_business_object_Id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主业务对象ID',
  `creator_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者ID',
  `launch_count` int(11) NOT NULL COMMENT '启动次数',
  `success_count` int(11) NOT NULL COMMENT '成功完成次数',
  `average_cost` bigint(20) NOT NULL COMMENT '平均完成时间（ms）',
  `status` int(11) NULL DEFAULT NULL COMMENT '流程状态（0-停用，1-正常）',
  `last_launch_timestamp` datetime(6) NULL DEFAULT NULL COMMENT '最后一次启动时间戳',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`business_process_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boo_process_instance
-- ----------------------------
DROP TABLE IF EXISTS `boo_process_instance`;
CREATE TABLE `boo_process_instance`  (
  `process_instance_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程实例ID',
  `process_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属流程ID',
  `launch_account_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提交运行账户ID',
  `launch_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提交运行方式',
  `launch_type` int(11) NULL DEFAULT NULL COMMENT '提交运行类型',
  `engine_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '运行引擎ID',
  `resource_service_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源服务ID',
  `resource_binding` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '绑定的静态资源（保留）',
  `resource_binding_type` int(11) NULL DEFAULT NULL COMMENT '资源绑定类型（0-业务角色映射，1-静态绑定）',
  `failure_type` int(11) NULL DEFAULT NULL COMMENT '失败类型（0-快速失败，1-尝试继续执行）',
  `participant_cache` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '流程实例参与者缓存',
  `launch_timestamp` datetime(6) NULL DEFAULT NULL COMMENT '提交运行时间戳',
  `finish_timestamp` datetime(6) NULL DEFAULT NULL COMMENT '完成时间戳',
  `result_type` int(11) NULL DEFAULT NULL COMMENT '结果类型（-1-失败，0-未定，1-成功）',
  `tag` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '附加信息',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`process_instance_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boo_service_info
-- ----------------------------
DROP TABLE IF EXISTS `boo_service_info`;
CREATE TABLE `boo_service_info`  (
  `service_info_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务信息ID',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务URL',
  `is_active` bit(1) NOT NULL COMMENT '服务是否存活',
  `business` double NULL DEFAULT NULL COMMENT '繁忙程度性能指标',
  `cpu_occupancy_rate` double NULL DEFAULT NULL COMMENT 'CPU占用率',
  `memory_occupancy_rate` double NULL DEFAULT NULL COMMENT '内存占用率',
  `tomcat_concurrency` double NULL DEFAULT NULL COMMENT 'Tomcat连接并发数',
  `work_item_count` double NULL DEFAULT NULL COMMENT '工作项个数',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`service_info_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boo_task_item
-- ----------------------------
DROP TABLE IF EXISTS `boo_task_item`;
CREATE TABLE `boo_task_item`  (
  `task_item_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库主键',
  `business_object_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务对象ID',
  `task_polymorphism_id` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '来源任务的模型ID',
  `task_polymorphism_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '来源任务的模型名',
  `business_role` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '涉及业务角色',
  `principle` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `event_descriptor` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'event name like eventSuccess in descriptor json string',
  `hook_descriptor` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'post hooks like onOffer in descriptor json string',
  `documentation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parameters` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数(JSON)',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`task_item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
