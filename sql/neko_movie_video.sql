/*
 Navicat Premium Data Transfer

 Source Server         : vm
 Source Server Type    : MySQL
 Source Server Version : 50738 (5.7.38)
 Source Host           : 192.168.30.131:3306
 Source Schema         : neko_movie_video

 Target Server Type    : MySQL
 Target Server Version : 50738 (5.7.38)
 File Encoding         : 65001

 Date: 16/07/2023 21:46:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category_info
-- ----------------------------
DROP TABLE IF EXISTS `category_info`;
CREATE TABLE `category_info`  (
  `category_id` int(255) NOT NULL AUTO_INCREMENT,
  `parent_id` int(255) NULL DEFAULT NULL,
  `level` int(5) NOT NULL DEFAULT 0 COMMENT '分类层级，0，1，2,最大为2',
  `category_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category_info
-- ----------------------------
INSERT INTO `category_info` VALUES (1, NULL, 0, '电影', '2023-03-29 15:46:35', '2023-03-29 15:46:37');
INSERT INTO `category_info` VALUES (2, NULL, 0, '电视剧', '2023-03-29 15:52:30', '2023-03-29 15:52:32');
INSERT INTO `category_info` VALUES (3, NULL, 0, '纪录片', '2023-03-29 15:54:05', '2023-03-29 15:54:07');
INSERT INTO `category_info` VALUES (23, NULL, 0, 'animate', '2023-04-25 14:12:55', '2023-04-25 14:12:55');
INSERT INTO `category_info` VALUES (24, 23, 1, '战斗', '2023-04-25 14:13:22', '2023-04-25 14:13:22');
INSERT INTO `category_info` VALUES (25, 23, 1, '恋爱', '2023-04-25 14:13:30', '2023-04-25 14:13:30');
INSERT INTO `category_info` VALUES (26, 1, 1, '科幻', '2023-05-01 14:37:42', '2023-05-01 14:37:42');
INSERT INTO `category_info` VALUES (27, 1, 1, '喜剧', '2023-07-16 10:24:43', '2023-07-16 10:24:45');
INSERT INTO `category_info` VALUES (28, 1, 1, '推理', '2023-07-16 10:25:16', '2023-07-16 10:25:18');
INSERT INTO `category_info` VALUES (29, 2, 1, '科幻', '2023-07-16 10:25:44', '2023-07-16 10:25:46');
INSERT INTO `category_info` VALUES (30, 2, 1, '喜剧', '2023-07-16 10:26:01', '2023-07-16 10:26:03');
INSERT INTO `category_info` VALUES (31, 2, 1, '推理', '2023-07-16 10:26:13', '2023-07-16 10:26:15');
INSERT INTO `category_info` VALUES (32, 3, 1, '地理', '2023-07-16 10:26:34', '2023-07-16 10:26:36');
INSERT INTO `category_info` VALUES (33, 3, 1, '动物', '2023-07-16 10:27:10', '2023-07-16 10:27:13');
INSERT INTO `category_info` VALUES (34, 23, 1, '校园', '2023-07-16 10:27:53', '2023-07-16 10:28:04');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `alipay_trade_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '支付宝流水id',
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `cost` decimal(20, 2) NOT NULL COMMENT '订单总价',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '-1->取消，0->未支付，1->已支付',
  `level` int(255) NOT NULL COMMENT '订单购买等级',
  `pay_level_days` int(255) NOT NULL COMMENT '开通天数',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for video_info
-- ----------------------------
DROP TABLE IF EXISTS `video_info`;
CREATE TABLE `video_info`  (
  `video_info_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `video_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '剧名',
  `video_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '介绍信息',
  `video_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `video_producer` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导演',
  `video_actors` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '演员信息',
  `up_time` datetime NOT NULL COMMENT '上映时间',
  `status` tinyint(4) NOT NULL DEFAULT -1 COMMENT '-1->下架，0->上架，1->回收站，2->删除',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`video_info_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '影视信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_info
-- ----------------------------

-- ----------------------------
-- Table structure for video_series_info
-- ----------------------------
DROP TABLE IF EXISTS `video_series_info`;
CREATE TABLE `video_series_info`  (
  `video_series_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `video_info_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '影视信息id，对应video_info表video_info_id',
  `series_number` int(255) NOT NULL COMMENT '视频集数',
  `video_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频地址',
  `require_level` int(255) NOT NULL DEFAULT 0 COMMENT '观看所需等级',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`video_series_id`) USING BTREE,
  INDEX `idx_video_info_id`(`video_info_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '影视集数信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_series_info
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
