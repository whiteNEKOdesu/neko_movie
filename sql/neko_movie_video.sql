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

 Date: 01/08/2023 08:39:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category_info
-- ----------------------------
DROP TABLE IF EXISTS `category_info`;
CREATE TABLE `category_info`  (
                                  `category_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '分类id',
                                  `parent_id` int(255) NULL DEFAULT NULL COMMENT '父分类id',
                                  `level` int(5) NOT NULL DEFAULT 0 COMMENT '分类层级，0，1,最大为1',
                                  `category_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名',
                                  `create_time` datetime NOT NULL COMMENT '创建时间',
                                  `update_time` datetime NOT NULL COMMENT '更新时间',
                                  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '影视分类表' ROW_FORMAT = Dynamic;

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
-- Table structure for discount_info
-- ----------------------------
DROP TABLE IF EXISTS `discount_info`;
CREATE TABLE `discount_info`  (
                                  `discount_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '秒杀折扣活动id',
                                  `discount_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '秒杀折扣名',
                                  `discount_rate` int(255) NOT NULL COMMENT '折扣百分比',
                                  `operate_admin_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '添加活动管理员id',
                                  `number` int(255) NOT NULL COMMENT '限制数量',
                                  `lock_number` int(255) NOT NULL DEFAULT 0 COMMENT '锁定数量',
                                  `is_end` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否结束',
                                  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                  `start_time` datetime NOT NULL COMMENT '开始时间',
                                  `end_time` datetime NOT NULL COMMENT '结束时间',
                                  `create_time` datetime NOT NULL COMMENT '创建时间',
                                  `update_time` datetime NOT NULL COMMENT '更新时间',
                                  PRIMARY KEY (`discount_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '秒杀折扣信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of discount_info
-- ----------------------------
INSERT INTO `discount_info` VALUES ('1684813417543634945', '会员折扣活动', 90, '1642398369596944385', 5, 0, 0, 0, '2023-07-29 11:35:41', '2023-08-03 12:35:41', '2023-07-28 14:29:56', '2023-07-31 11:17:07');

-- ----------------------------
-- Table structure for discount_lock_log
-- ----------------------------
DROP TABLE IF EXISTS `discount_lock_log`;
CREATE TABLE `discount_lock_log`  (
                                      `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
                                      `discount_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '秒杀折扣活动id，对应discount_info表discount_id',
                                      `lock_number` int(255) NOT NULL COMMENT '锁定数量',
                                      `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '-1->已取消锁定，0->锁定中，1->用户已支付',
                                      `create_time` datetime NOT NULL COMMENT '创建时间',
                                      `update_time` datetime NOT NULL COMMENT '更新时间',
                                      PRIMARY KEY (`order_id`) USING BTREE,
                                      INDEX `idx_discount_id`(`discount_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员限时折扣限制数量锁定日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of discount_lock_log
-- ----------------------------
INSERT INTO `discount_lock_log` VALUES ('202307291700043291685213590245691394', '1684813417543634945', 1, -1, '2023-07-29 17:00:42', '2023-07-29 17:05:43');
INSERT INTO `discount_lock_log` VALUES ('202307291701479731685214024968523777', '1684813417543634945', 1, -1, '2023-07-29 17:01:51', '2023-07-29 17:06:51');
INSERT INTO `discount_lock_log` VALUES ('202307291705081141685214864391360513', '1684813417543634945', 1, -1, '2023-07-29 17:05:10', '2023-07-29 17:10:10');
INSERT INTO `discount_lock_log` VALUES ('202307291707369371685215488612847618', '1684813417543634945', 1, -1, '2023-07-29 17:07:39', '2023-07-29 17:12:39');
INSERT INTO `discount_lock_log` VALUES ('202307291708450361685215774244950017', '1684813417543634945', 1, -1, '2023-07-29 17:08:47', '2023-07-29 17:13:47');
INSERT INTO `discount_lock_log` VALUES ('202307291709282871685215955640209410', '1684813417543634945', 1, -1, '2023-07-29 17:09:30', '2023-07-29 17:14:30');
INSERT INTO `discount_lock_log` VALUES ('202307291710249621685216193373360130', '1684813417543634945', 1, 1, '2023-07-29 17:10:27', '2023-07-29 17:12:01');
INSERT INTO `discount_lock_log` VALUES ('202307291720192111685218685792374786', '1684813417543634945', 1, -1, '2023-07-29 17:20:41', '2023-07-29 17:25:41');
INSERT INTO `discount_lock_log` VALUES ('202307291721279331685218974075277314', '1684813417543634945', 1, 1, '2023-07-29 17:21:34', '2023-07-29 17:22:05');
INSERT INTO `discount_lock_log` VALUES ('202307291723377111685219518374301698', '1684813417543634945', 1, -1, '2023-07-29 17:23:40', '2023-07-29 17:28:40');
INSERT INTO `discount_lock_log` VALUES ('202307291725398091685220030473654274', '1684813417543634945', 1, -1, '2023-07-29 17:25:43', '2023-07-29 17:30:43');
INSERT INTO `discount_lock_log` VALUES ('202307291739355501685223535842250753', '1684813417543634945', 1, 1, '2023-07-29 17:39:55', '2023-07-29 17:40:33');
INSERT INTO `discount_lock_log` VALUES ('202307311003263031685833516668997634', '1684813417543634945', 1, -1, '2023-07-31 10:04:22', '2023-07-31 10:09:22');
INSERT INTO `discount_lock_log` VALUES ('202307311005427071685834088755286017', '1684813417543634945', 1, 1, '2023-07-31 10:05:48', '2023-07-31 10:06:30');
INSERT INTO `discount_lock_log` VALUES ('202307311112049751685850791635664897', '1684813417543634945', 1, -1, '2023-07-31 11:12:07', '2023-07-31 11:17:07');

-- ----------------------------
-- Table structure for mq_return_message
-- ----------------------------
DROP TABLE IF EXISTS `mq_return_message`;
CREATE TABLE `mq_return_message`  (
                                      `mq_return_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'rabbitmq回退消息id',
                                      `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息',
                                      `type` tinyint(4) NOT NULL COMMENT '消息类型，0->订单处理延迟队列消息，1->视频删除延迟队列消息，2->修改会员等级消息，3->会员等级过期插件延迟队列消息',
                                      `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                      `create_time` datetime NOT NULL COMMENT '创建时间',
                                      `update_time` datetime NOT NULL COMMENT '修改时间',
                                      PRIMARY KEY (`mq_return_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'rabbitmq消息发送失败记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mq_return_message
-- ----------------------------

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
                               `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
                               `alipay_trade_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付宝流水id',
                               `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
                               `discount_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '秒杀折扣id，对应discount_info表discount_id',
                               `cost` decimal(20, 2) NOT NULL COMMENT '订单总价',
                               `actual_cost` decimal(20, 2) NOT NULL COMMENT '实际支付价格',
                               `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '-1->取消，0->未支付，1->已支付',
                               `member_level_id` int(255) NOT NULL COMMENT '订单购买等级id',
                               `role_type` varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单购买会员等级类型角色名，冗余字段',
                               `pay_level_months` int(255) NOT NULL COMMENT '开通月数',
                               `create_time` datetime NOT NULL COMMENT '创建时间',
                               `update_time` datetime NOT NULL COMMENT '更新时间',
                               PRIMARY KEY (`order_id`) USING BTREE,
                               INDEX `idx_uid`(`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('202307291700043291685213590245691394', NULL, '1684853753762611201', '1684813417543634945', 27.00, 27.00, -1, 5, '2级会员', 2, '2023-07-29 17:00:42', '2023-07-29 17:05:42');
INSERT INTO `order_info` VALUES ('202307291701479731685214024968523777', NULL, '1684853753762611201', '1684813417543634945', 13.50, 13.50, -1, 5, '2级会员', 1, '2023-07-29 17:01:51', '2023-07-29 17:06:51');
INSERT INTO `order_info` VALUES ('202307291705081141685214864391360513', NULL, '1684853753762611201', '1684813417543634945', 13.50, 13.50, -1, 5, '2级会员', 1, '2023-07-29 17:05:10', '2023-07-29 17:10:10');
INSERT INTO `order_info` VALUES ('202307291707369371685215488612847618', NULL, '1684853753762611201', '1684813417543634945', 13.50, 13.50, -1, 5, '2级会员', 1, '2023-07-29 17:07:39', '2023-07-29 17:12:39');
INSERT INTO `order_info` VALUES ('202307291708450361685215774244950017', NULL, '1684853753762611201', '1684813417543634945', 13.50, 13.50, -1, 5, '2级会员', 1, '2023-07-29 17:08:47', '2023-07-29 17:13:47');
INSERT INTO `order_info` VALUES ('202307291709282871685215955640209410', NULL, '1684853753762611201', '1684813417543634945', 13.50, 13.50, -1, 5, '2级会员', 1, '2023-07-29 17:09:30', '2023-07-29 17:14:30');
INSERT INTO `order_info` VALUES ('202307291710249621685216193373360130', '2023072922001436790500470601', '1684853753762611201', '1684813417543634945', 18.00, 18.00, 1, 4, '1级会员', 2, '2023-07-29 17:10:27', '2023-07-29 17:12:01');
INSERT INTO `order_info` VALUES ('202307291720192111685218685792374786', NULL, '1642067605873348610', '1684813417543634945', 9.00, 9.00, -1, 4, '1级会员', 1, '2023-07-29 17:20:41', '2023-07-29 17:25:41');
INSERT INTO `order_info` VALUES ('202307291721279331685218974075277314', '2023072922001436790500470602', '1642067605873348610', '1684813417543634945', 9.00, 9.00, 1, 4, '1级会员', 1, '2023-07-29 17:21:34', '2023-07-29 17:22:05');
INSERT INTO `order_info` VALUES ('202307291723377111685219518374301698', NULL, '1684853753762611201', '1684813417543634945', 9.00, 9.00, -1, 4, '1级会员', 1, '2023-07-29 17:23:40', '2023-07-29 17:28:40');
INSERT INTO `order_info` VALUES ('202307291725398091685220030473654274', NULL, '1684853753762611201', '1684813417543634945', 40.50, 40.50, -1, 5, '2级会员', 3, '2023-07-29 17:25:43', '2023-07-29 17:30:43');
INSERT INTO `order_info` VALUES ('202307291739355501685223535842250753', '2023072922001436790500479397', '1642067605873348610', '1684813417543634945', 9.00, 9.00, 1, 4, '1级会员', 1, '2023-07-29 17:39:55', '2023-07-29 17:40:33');
INSERT INTO `order_info` VALUES ('202307291742212631685224230884560898', NULL, '1684853753762611201', NULL, 30.00, 30.00, -1, 4, '1级会员', 3, '2023-07-29 17:42:23', '2023-07-29 17:47:23');
INSERT INTO `order_info` VALUES ('202307291743381681685224553489453058', NULL, '1684853753762611201', NULL, 30.00, 30.00, -1, 4, '1级会员', 3, '2023-07-29 17:43:40', '2023-07-29 17:48:40');
INSERT INTO `order_info` VALUES ('202307310848355921685814681266085890', NULL, '1684853753762611201', NULL, 45.00, 45.00, -1, 5, '2级会员', 3, '2023-07-31 08:48:38', '2023-07-31 08:53:38');
INSERT INTO `order_info` VALUES ('202307310900433441685817733658238978', NULL, '1684853753762611201', '1684813417543634945', 40.00, 40.00, -1, 4, '1级会员', 4, '2023-07-31 09:00:46', '2023-07-31 09:05:46');
INSERT INTO `order_info` VALUES ('202307311003263031685833516668997634', NULL, '1642067605873348610', '1684813417543634945', 9.00, 9.00, -1, 4, '1级会员', 1, '2023-07-31 10:04:22', '2023-07-31 10:09:22');
INSERT INTO `order_info` VALUES ('202307311005427071685834088755286017', '2023073122001436790500485807', '1642067605873348610', '1684813417543634945', 9.00, 9.00, 1, 4, '1级会员', 1, '2023-07-31 10:05:48', '2023-07-31 10:06:30');
INSERT INTO `order_info` VALUES ('202307311008537471685834890018344961', NULL, '1642067605873348610', '1684813417543634945', 10.00, 10.00, -1, 4, '1级会员', 1, '2023-07-31 10:09:00', '2023-07-31 10:14:00');
INSERT INTO `order_info` VALUES ('202307311012098481685835712584228865', NULL, '1642067605873348610', '1684813417543634945', 10.00, 10.00, -1, 4, '1级会员', 1, '2023-07-31 10:12:16', '2023-07-31 10:17:16');
INSERT INTO `order_info` VALUES ('202307311013377541685836081292922882', NULL, '1642067605873348610', NULL, 10.00, 10.00, -1, 4, '1级会员', 1, '2023-07-31 10:13:47', '2023-07-31 10:18:47');
INSERT INTO `order_info` VALUES ('202307311112049751685850791635664897', NULL, '1685850632348516354', '1684813417543634945', 13.50, 13.50, -1, 5, '2级会员', 1, '2023-07-31 11:12:07', '2023-07-31 11:17:07');

-- ----------------------------
-- Table structure for video_collection
-- ----------------------------
DROP TABLE IF EXISTS `video_collection`;
CREATE TABLE `video_collection`  (
                                     `collection_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收藏id',
                                     `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
                                     `video_info_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收藏影视信息id，对应video_info表video_info_id',
                                     `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                     `create_time` datetime NOT NULL COMMENT '创建时间',
                                     `update_time` datetime NOT NULL COMMENT '更新时间',
                                     PRIMARY KEY (`collection_id`) USING BTREE,
                                     INDEX `idx_uid`(`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_collection
-- ----------------------------

-- ----------------------------
-- Table structure for video_info
-- ----------------------------
DROP TABLE IF EXISTS `video_info`;
CREATE TABLE `video_info`  (
                               `video_info_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '影视信息id',
                               `video_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '剧名',
                               `video_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '介绍信息',
                               `video_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '封面',
                               `video_producer` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导演',
                               `video_actors` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '演员信息',
                               `category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类id，对应category_info表category_id',
                               `up_time` datetime NOT NULL COMMENT '上映时间',
                               `status` tinyint(4) NOT NULL DEFAULT -1 COMMENT '-1->下架，0->上架，1->回收站，2->删除',
                               `create_time` datetime NOT NULL COMMENT '创建时间',
                               `update_time` datetime NOT NULL COMMENT '更新时间',
                               PRIMARY KEY (`video_info_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '影视信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_info
-- ----------------------------
INSERT INTO `video_info` VALUES ('1680781825485639682', 'NEKO', 'NEKO', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/dbe85cae-7196-48b4-86e6-34cf54c0717c_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 'NEKO', 'NEKO', '34', '2023-03-29 15:46:35', -1, '2023-07-17 11:29:49', '2023-07-17 11:29:49');
INSERT INTO `video_info` VALUES ('1680783130899845122', 'NEKO', 'NEKO', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/0b88989f-13ac-4b4c-883e-c80f22510f1e_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 'NEKO', 'NEKO', '34', '2023-03-29 15:46:35', -1, '2023-07-17 11:35:01', '2023-07-17 11:35:01');
INSERT INTO `video_info` VALUES ('1680783148910186498', 'NEKO', 'NEKO', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/73580e48-e4a8-4c98-bef5-1582d63aa0cb_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 'NEKO', 'NEKO', '34', '2023-03-29 15:46:35', -1, '2023-07-17 11:35:05', '2023-07-17 11:35:05');
INSERT INTO `video_info` VALUES ('1680783166308159489', 'NEKO', 'NEKO', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/f75d6c13-e485-420b-8437-920261b9618f_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 'NEKO', 'NEKO', '34', '2023-03-29 15:46:35', -1, '2023-07-17 11:35:09', '2023-07-17 11:35:09');
INSERT INTO `video_info` VALUES ('1680783183198621698', 'NEKO', 'NEKO', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/f092c4d9-e242-4cc4-b51e-f112851bb4bb_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 'NEKO', 'NEKO', '34', '2023-03-29 15:46:35', -1, '2023-07-17 11:35:13', '2023-07-17 11:35:13');
INSERT INTO `video_info` VALUES ('1680783199812259841', 'NEKO', 'NEKO', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/68085315-f614-42de-ba03-329b09a95762_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 'NEKO', 'NEKO', '34', '2023-03-29 15:46:35', -1, '2023-07-17 11:35:17', '2023-07-17 11:35:17');
INSERT INTO `video_info` VALUES ('1680783216799191041', 'NEKO', 'NEKO', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/4614a8fc-6307-48c0-b250-5726358041a5_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 'NEKO', 'NEKO', '34', '2023-03-29 15:46:35', -1, '2023-07-17 11:35:21', '2023-07-17 11:35:21');
INSERT INTO `video_info` VALUES ('1680783233358303233', 'NEKO', 'NEKO', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/40f59830-6945-4c48-8cec-5bc5433364d5_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 'NEKO', 'NEKO', '34', '2023-03-29 15:46:35', -1, '2023-07-17 11:35:25', '2023-07-17 11:35:25');
INSERT INTO `video_info` VALUES ('1680783251037294593', 'NEKO', 'NEKO', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/463e007e-74a6-43c2-ab7a-37345a2bfe8d_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 'NEKO', 'NEKO', '34', '2023-03-29 15:46:35', -1, '2023-07-17 11:35:29', '2023-07-17 11:35:29');
INSERT INTO `video_info` VALUES ('1680783299959656449', 'NEKO', 'NEKO', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/260b5481-b78e-4ea9-92ec-5bb88d0d3842_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 'NEKO', 'NEKO', '34', '2023-03-29 15:46:35', -1, '2023-07-17 11:35:41', '2023-07-17 11:35:41');
INSERT INTO `video_info` VALUES ('1680783320826318849', 'NEKO', 'NEKO', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/259da5b9-f07d-4d0e-ad94-f0cae0a64762_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 'NEKO', 'NEKO', '34', '2023-03-29 15:46:35', -1, '2023-07-17 11:35:46', '2023-07-17 11:35:46');
INSERT INTO `video_info` VALUES ('1682551371754180610', '花神之舞', '妮露 花神之舞', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/62286bc7-6c92-4dc1-80e4-71c2fefca495_捕获.PNG', 'mihoyo', '妮露', '24', '2023-07-22 08:40:46', 0, '2023-07-22 08:41:22', '2023-07-22 08:41:22');
INSERT INTO `video_info` VALUES ('1683373796746121218', '鬼灭之刃 锻刀村篇', '卖炭维生的善良少年炭治郎，某天家人惨遭鬼杀害。而唯一幸存的妹妹祢豆子，却变化成鬼。遭到绝望现实打击的炭治郎，为了让妹妹恢复回人类，为了被杀害的家人向鬼报仇，决定踏上”猎鬼”之路。', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-24/99460edc-3409-4973-97a8-5302e45b2cad_捕获.PNG', '外崎春雄', '花江夏树 下野纮 松冈祯丞 鬼头明里 宫野真守 花泽香菜 河西健吾', '24', '2023-07-24 15:09:00', 0, '2023-07-24 15:09:23', '2023-07-24 15:09:23');
INSERT INTO `video_info` VALUES ('1683374868122357762', '鬼灭之刃 游郭篇', '鬼灭之刃游郭篇故事剧情将从《鬼灭之刃无限列车篇》失去炎柱后做为开端，以日本花街为舞台，而最让人期待的音柱、堕姬即将在鬼灭第二季登场！', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-24/032df54a-fb38-40da-a64d-893223e20e4d_20230411164723_513.jpg', '外崎春雄', '花江夏树 鬼头明里 下野纮 松冈祯丞 小西克幸 森川智之 泽城美雪 逢坂良太 石上静香 东山奈央 种崎敦美 关俊彦 浪川大辅 上田丽奈 江原裕理 石田彰', '24', '2023-07-24 15:12:27', 0, '2023-07-24 15:13:39', '2023-07-24 15:13:39');

-- ----------------------------
-- Table structure for video_series_info
-- ----------------------------
DROP TABLE IF EXISTS `video_series_info`;
CREATE TABLE `video_series_info`  (
                                      `video_series_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '集数id',
                                      `video_info_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '影视信息id，对应video_info表video_info_id',
                                      `series_number` int(255) NOT NULL COMMENT '视频集数',
                                      `video_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频地址',
                                      `weight_id` int(255) NOT NULL DEFAULT 1 COMMENT '观看所需会员等级类型权限id，对应neko_movie_member数据库user_weight表weight_id',
                                      `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                      `create_time` datetime NOT NULL COMMENT '创建时间',
                                      `update_time` datetime NOT NULL COMMENT '更新时间',
                                      PRIMARY KEY (`video_series_id`) USING BTREE,
                                      INDEX `idx_video_info_id`(`video_info_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '影视集数信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_series_info
-- ----------------------------
INSERT INTO `video_series_info` VALUES ('1650051503139880961', '1680783320826318849', 2, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/dbe85cae-7196-48b4-86e6-34cf54c0717c_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 16, 0, '2023-07-20 09:58:56', '2023-07-20 09:58:58');
INSERT INTO `video_series_info` VALUES ('1680781825485639682', '1680783320826318849', 1, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-17/dbe85cae-7196-48b4-86e6-34cf54c0717c_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', 16, 0, '2023-07-20 09:58:25', '2023-07-20 09:58:29');
INSERT INTO `video_series_info` VALUES ('1682564048132014082', '1682551371754180610', 1, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/89a91135-f3f3-4147-9ef6-2240702d1acb_nilou.mp4', 16, 0, '2023-07-22 09:31:44', '2023-07-22 09:31:44');
INSERT INTO `video_series_info` VALUES ('1683373881982767105', '1683373796746121218', 5, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/%5BUHA-WINGS%5D%20%5BKimetsu%20no%20Yaiba%20-%20Katanakaji%20no%20Sato-hen%5D%5B05%5D%5Bx264%201080p%5D%5BCHS%5D.mp4', 16, 0, '2023-07-24 15:09:44', '2023-07-24 15:09:44');
INSERT INTO `video_series_info` VALUES ('1683374990189187074', '1683373796746121218', 6, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/%5BUHA-WINGS%5D%20%5BKimetsu%20no%20Yaiba%20-%20Katanakaji%20no%20Sato-hen%5D%5B06%5D%5Bx264%201080p%5D%5BCHS%5D.mp4', 16, 0, '2023-07-24 15:14:08', '2023-07-24 15:14:08');

SET FOREIGN_KEY_CHECKS = 1;
