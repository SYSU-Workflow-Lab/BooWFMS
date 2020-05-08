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

 Date: 08/05/2020 14:49:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for boo_account
-- ----------------------------
DROP TABLE IF EXISTS `boo_account`;
CREATE TABLE `boo_account`  (
  `account_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户名',
  `password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户密码',
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '盐值',
  `organization_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属组织名称',
  `status` int(11) NOT NULL COMMENT '账户状态（0-停用，1-正常）',
  `level` int(11) NOT NULL COMMENT '账户级别',
  `last_login_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`account_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `name_org`(`username`, `organization_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of boo_account
-- ----------------------------
INSERT INTO `boo_account` VALUES ('account-1', 'admin@admin', '921de9d99bcd43f7648ab376f9e4d88acfbc7ffa8d7ab270e6480c0592d89a84', 'bf6b8251', 'admin', 1, 0, NULL, '2020-05-08 14:48:09', '2020-05-08 14:48:09');

-- ----------------------------
-- Table structure for boo_agent
-- ----------------------------
DROP TABLE IF EXISTS `boo_agent`;
CREATE TABLE `boo_agent`  (
  `agent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'agent ID',
  `display_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '显示名称',
  `location` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '账户密码',
  `reentrant_type` int(11) NULL DEFAULT NULL COMMENT '是否可重入（0-否，1-是）',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`agent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `main_business_object_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主业务对象名称',
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
-- Table structure for boo_business_role_map
-- ----------------------------
DROP TABLE IF EXISTS `boo_business_role_map`;
CREATE TABLE `boo_business_role_map`  (
  `business_role_map_id` varchar(255) CHARACTER SET sjis COLLATE sjis_japanese_ci NOT NULL COMMENT '数据库主键',
  `process_instance_id` varchar(255) CHARACTER SET sjis COLLATE sjis_japanese_ci NOT NULL COMMENT '流程实例ID',
  `business_role_name` text CHARACTER SET sjis COLLATE sjis_japanese_ci NOT NULL COMMENT '业务角色名称',
  `organization_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属组织ID',
  `mapped_account_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '映射用户账户ID',
  `data_version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据版本',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`business_role_map_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boo_exit_item
-- ----------------------------
DROP TABLE IF EXISTS `boo_exit_item`;
CREATE TABLE `boo_exit_item`  (
  `exit_item_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `work_item_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '对应工作项ID',
  `process_instance_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程实例ID',
  `status` int(11) NOT NULL COMMENT '状态',
  `visibility` int(11) NOT NULL COMMENT '可见性，0-domain,1-WFMSadmin',
  `reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '原因',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`exit_item_id`) USING BTREE,
  INDEX `work_item_id`(`work_item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boo_organization
-- ----------------------------
DROP TABLE IF EXISTS `boo_organization`;
CREATE TABLE `boo_organization`  (
  `organization_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `status` int(11) NOT NULL COMMENT '组织状态（0-停用，1-正常）',
  `parent_organization_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父组织ID',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`organization_id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boo_process_instance
-- ----------------------------
DROP TABLE IF EXISTS `boo_process_instance`;
CREATE TABLE `boo_process_instance`  (
  `process_instance_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程实例ID',
  `process_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属流程ID',
  `launch_account_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提交运行账户ID',
  `launch_platform` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提交运行平台',
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
-- Table structure for boo_process_participant
-- ----------------------------
DROP TABLE IF EXISTS `boo_process_participant`;
CREATE TABLE `boo_process_participant`  (
  `process_participant_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程参与者ID',
  `account_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户ID',
  `display_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '显示名称',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型',
  `agent_location` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'URL位置，当类型为Agent时用',
  `reentrant_type` int(11) NULL DEFAULT NULL COMMENT '重入类型，当类型为Agent时用',
  `note` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注信息',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`process_participant_id`) USING BTREE,
  INDEX `account_id`(`account_id`) USING BTREE
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

-- ----------------------------
-- Table structure for boo_work_item
-- ----------------------------
DROP TABLE IF EXISTS `boo_work_item`;
CREATE TABLE `boo_work_item`  (
  `work_item_id` varchar(255) CHARACTER SET sjis COLLATE sjis_japanese_ci NOT NULL COMMENT '工作项ID',
  `process_instance_id` varchar(255) CHARACTER SET sjis COLLATE sjis_japanese_ci NOT NULL COMMENT '流程实例ID',
  `resource_service_id` varchar(255) CHARACTER SET sjis COLLATE sjis_japanese_ci NOT NULL COMMENT '资源服务ID',
  `process_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务流程ID',
  `business_object_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务对象ID',
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '来源任务ID',
  `task_polymorphism_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '来源任务的模型ID',
  `arguments` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '相关参数',
  `allocate_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '分配时间戳',
  `launch_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '开始时间戳',
  `finish_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '完成时间戳',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作项状态',
  `resourcing_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作项资源调度生命周期状态',
  `launch_account_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作项启动者ID',
  `finish_account_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作项完成者ID',
  `timer_trigger_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '启动时间触发器标识符',
  `timer_expiry_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中止时间触发器标识符',
  `last_launch_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后一次启动时间戳',
  `execute_time` bigint(20) NULL DEFAULT NULL COMMENT '执行时间间隔（ms）',
  `callback_node_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回调事件发送目的地业务对象的标识符',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`work_item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boo_work_item_list
-- ----------------------------
DROP TABLE IF EXISTS `boo_work_item_list`;
CREATE TABLE `boo_work_item_list`  (
  `work_item_list_id` varchar(255) CHARACTER SET sjis COLLATE sjis_japanese_ci NOT NULL COMMENT '工作项列表ID',
  `owner_account_id` varchar(255) CHARACTER SET sjis COLLATE sjis_japanese_ci NULL DEFAULT NULL COMMENT '拥有者ID',
  `type` int(11) NOT NULL COMMENT '类型',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`work_item_list_id`) USING BTREE,
  INDEX `list_item`(`owner_account_id`, `type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boo_work_item_list_item
-- ----------------------------
DROP TABLE IF EXISTS `boo_work_item_list_item`;
CREATE TABLE `boo_work_item_list_item`  (
  `work_item_list_item_id` varchar(255) CHARACTER SET sjis COLLATE sjis_japanese_ci NOT NULL COMMENT '工作项列表项ID',
  `work_item_list_id` varchar(255) CHARACTER SET sjis COLLATE sjis_japanese_ci NOT NULL COMMENT '所属工作项列表ID',
  `work_item_id` varchar(255) CHARACTER SET sjis COLLATE sjis_japanese_ci NOT NULL COMMENT '对应工作项ID',
  `create_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '创建时间戳',
  `last_update_timestamp` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间戳',
  PRIMARY KEY (`work_item_list_item_id`) USING BTREE,
  INDEX `list_item`(`work_item_list_id`, `work_item_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
