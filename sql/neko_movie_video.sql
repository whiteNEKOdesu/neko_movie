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

 Date: 10/08/2023 16:35:42
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
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '影视分类表' ROW_FORMAT = Dynamic;

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
INSERT INTO `category_info` VALUES (27, 1, 1, '恋爱', '2023-07-16 10:24:43', '2023-07-16 10:24:45');
INSERT INTO `category_info` VALUES (28, 1, 1, '战斗', '2023-07-16 10:25:16', '2023-07-16 10:25:18');
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
INSERT INTO `order_info` VALUES ('202308070933088011688362608505196546', NULL, '1642067605873348610', NULL, 10.00, 10.00, -1, 4, '1级会员', 1, '2023-08-07 09:33:15', '2023-08-07 09:38:15');
INSERT INTO `order_info` VALUES ('202308070934164821688362892409245697', '2023080722001436790500550627', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-07 09:34:22', '2023-08-07 09:34:55');
INSERT INTO `order_info` VALUES ('202308070937303451688363705529602049', '2023080722001436790500549566', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-07 09:37:38', '2023-08-07 09:38:07');
INSERT INTO `order_info` VALUES ('202308070943567881688365326426771457', '2023080722001436790500544380', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-07 09:44:03', '2023-08-07 09:44:29');
INSERT INTO `order_info` VALUES ('202308070959142421688369174507753474', '2023080722001436790500551882', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-07 09:59:21', '2023-08-07 09:59:49');
INSERT INTO `order_info` VALUES ('202308071001440891688369802994847745', '2023080722001436790500555247', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-07 10:01:50', '2023-08-07 10:02:22');
INSERT INTO `order_info` VALUES ('202308071004395901688370539074228225', '2023080722001436790500553269', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-07 10:04:46', '2023-08-07 10:05:13');
INSERT INTO `order_info` VALUES ('202308071109238181688386830686294018', '2023080722001436790500551883', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-07 11:09:29', '2023-08-07 11:09:57');
INSERT INTO `order_info` VALUES ('202308071111547641688387463799705602', '2023080722001436790500551884', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-07 11:12:01', '2023-08-07 11:12:26');
INSERT INTO `order_info` VALUES ('202308071446461151688441534124429314', NULL, '1642067605873348610', NULL, 10.00, 10.00, -1, 4, '1级会员', 1, '2023-08-07 14:47:40', '2023-08-07 14:52:40');
INSERT INTO `order_info` VALUES ('202308071448581461688442087885803522', NULL, '1642067605873348610', NULL, 10.00, 10.00, -1, 4, '1级会员', 1, '2023-08-07 14:49:05', '2023-08-07 14:54:05');
INSERT INTO `order_info` VALUES ('202308071453149351688443164957904898', '2023080722001436790500555248', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-07 14:53:22', '2023-08-07 14:53:50');
INSERT INTO `order_info` VALUES ('202308071457144501688444169552433154', '2023080722001436790500553273', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-07 14:57:22', '2023-08-07 14:57:50');
INSERT INTO `order_info` VALUES ('202308071459241331688444713444610049', '2023080722001436790500553274', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-07 14:59:37', '2023-08-07 15:00:39');
INSERT INTO `order_info` VALUES ('202308071504149141688445933039161345', '2023080722001436790500553275', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-07 15:04:23', '2023-08-07 15:04:52');
INSERT INTO `order_info` VALUES ('202308080853068581688714921967218689', '2023080822001436790500557677', '1684853753762611201', NULL, 20.00, 20.00, 1, 4, '1级会员', 2, '2023-08-08 08:53:09', '2023-08-08 08:54:00');
INSERT INTO `order_info` VALUES ('202308080916485541688720884983930882', '2023080822001436790500556304', '1684853753762611201', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 09:16:50', '2023-08-08 09:17:23');
INSERT INTO `order_info` VALUES ('202308080923447301688722630540009474', NULL, '1642067605873348610', NULL, 10.00, 10.00, -1, 4, '1级会员', 1, '2023-08-08 09:24:38', '2023-08-08 09:29:38');
INSERT INTO `order_info` VALUES ('202308080925295041688723069985628162', '2023080822001436790500557678', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 09:25:35', '2023-08-08 09:26:44');
INSERT INTO `order_info` VALUES ('202308080927240581688723550422179842', '2023080822001436790500556305', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 09:27:31', '2023-08-08 09:27:59');
INSERT INTO `order_info` VALUES ('202308080928246731688723804672499714', '2023080822001436790500551886', '1684853753762611201', NULL, 15.00, 15.00, 1, 5, '2级会员', 1, '2023-08-08 09:28:26', '2023-08-08 09:28:52');
INSERT INTO `order_info` VALUES ('202308080930051391688724226103582721', '2023080822001436790500559492', '1642067605873348610', NULL, 15.00, 15.00, 1, 5, '2级会员', 1, '2023-08-08 09:30:11', '2023-08-08 09:30:44');
INSERT INTO `order_info` VALUES ('202308080956352531688730895512510465', '2023080822001436790500559493', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 09:56:42', '2023-08-08 09:57:22');
INSERT INTO `order_info` VALUES ('202308080959532171688731725804347393', '2023080822001436790500553276', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 10:00:00', '2023-08-08 10:00:35');
INSERT INTO `order_info` VALUES ('202308081001248001688732109935484930', '2023080822001436790500560684', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 10:01:31', '2023-08-08 10:02:13');
INSERT INTO `order_info` VALUES ('202308081003571491688732748971253762', '2023080822001436790500562115', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 10:04:05', '2023-08-08 10:04:36');
INSERT INTO `order_info` VALUES ('202308081009369621688734174246084610', '2023080822001436790500556306', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 10:09:43', '2023-08-08 10:10:16');
INSERT INTO `order_info` VALUES ('202308081017412261688736205341990913', '2023080822001436790500562116', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 10:17:47', '2023-08-08 10:18:16');
INSERT INTO `order_info` VALUES ('202308081023175011688737615815122945', '2023080822001436790500557679', '1642067605873348610', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 10:23:31', '2023-08-08 10:24:14');
INSERT INTO `order_info` VALUES ('202308081529325531688814686398595074', '2023080822001436790500557680', '1684853753762611201', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 15:29:34', '2023-08-08 15:30:37');
INSERT INTO `order_info` VALUES ('202308081531491961688815259466350593', '2023080822001436790500564754', '1684853753762611201', NULL, 15.00, 15.00, 1, 5, '2级会员', 1, '2023-08-08 15:31:51', '2023-08-08 15:32:16');
INSERT INTO `order_info` VALUES ('202308081553016941688820596760150017', '2023080822001436790500566240', '1685850632348516354', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 15:53:04', '2023-08-08 15:53:31');
INSERT INTO `order_info` VALUES ('202308081638193521688831995393667074', '2023080822001436790500563415', '1684853753762611201', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 16:38:21', '2023-08-08 16:39:10');
INSERT INTO `order_info` VALUES ('202308081723494711688843446401376258', '2023080822001436790500562118', '1685850632348516354', NULL, 10.00, 10.00, 1, 4, '1级会员', 1, '2023-08-08 17:23:51', '2023-08-08 17:24:15');

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
INSERT INTO `video_collection` VALUES ('1688476904144814081', '1642067605873348610', '1683373796746121218', 0, '2023-08-07 17:07:19', '2023-08-07 17:07:19');
INSERT INTO `video_collection` VALUES ('1688476949535571969', '1642067605873348610', '1683374868122357762', 0, '2023-08-07 17:07:30', '2023-08-07 17:07:30');
INSERT INTO `video_collection` VALUES ('1688751314386919426', '1684853753762611201', '1683373796746121218', 1, '2023-08-08 11:17:43', '2023-08-08 11:17:43');
INSERT INTO `video_collection` VALUES ('1688831136375042050', '1684853753762611201', '1683373796746121218', 1, '2023-08-08 16:34:55', '2023-08-08 16:34:55');
INSERT INTO `video_collection` VALUES ('1688832417168683010', '1684853753762611201', '1683373796746121218', 1, '2023-08-08 16:40:00', '2023-08-08 16:40:00');
INSERT INTO `video_collection` VALUES ('1688843972039942145', '1685850632348516354', '1683373796746121218', 1, '2023-08-08 17:25:55', '2023-08-08 17:25:55');
INSERT INTO `video_collection` VALUES ('1689102136677314562', '1684853753762611201', '1683373796746121218', 0, '2023-08-09 10:31:46', '2023-08-09 10:31:46');
INSERT INTO `video_collection` VALUES ('1689102235037937665', '1684853753762611201', '', 0, '2023-08-09 10:32:10', '2023-08-09 10:32:10');
INSERT INTO `video_collection` VALUES ('1689466200775520258', '1684853753762611201', '1682551371754180610', 0, '2023-08-10 10:38:26', '2023-08-10 10:38:26');
INSERT INTO `video_collection` VALUES ('1689466260389163009', '1684853753762611201', '1689450889653301249', 0, '2023-08-10 10:38:40', '2023-08-10 10:38:40');
INSERT INTO `video_collection` VALUES ('1689466417461653506', '1684853753762611201', '1689457959261745154', 0, '2023-08-10 10:39:17', '2023-08-10 10:39:17');

-- ----------------------------
-- Table structure for video_info
-- ----------------------------
DROP TABLE IF EXISTS `video_info`;
CREATE TABLE `video_info`  (
                               `video_info_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '影视信息id',
                               `video_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '剧名',
                               `video_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '介绍信息',
                               `video_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '封面',
                               `video_producer` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '导演',
                               `video_actors` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '演员信息',
                               `category_id` int(255) NOT NULL COMMENT '分类id，对应category_info表category_id',
                               `up_time` datetime NOT NULL COMMENT '上映时间',
                               `delete_expire_time` datetime NULL DEFAULT NULL COMMENT '回收站到期时间',
                               `status` tinyint(4) NOT NULL DEFAULT -1 COMMENT '-1->下架，0->上架，1->回收站，2->删除',
                               `create_time` datetime NOT NULL COMMENT '创建时间',
                               `update_time` datetime NOT NULL COMMENT '更新时间',
                               PRIMARY KEY (`video_info_id`) USING BTREE,
                               INDEX `idx_for_video_inf_category_id`(`category_id`) USING BTREE,
                               CONSTRAINT `idx_for_video_inf_category_id` FOREIGN KEY (`category_id`) REFERENCES `category_info` (`category_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '影视信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_info
-- ----------------------------
INSERT INTO `video_info` VALUES ('1682551371754180610', '花神之舞', '妮露 花神之舞', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/62286bc7-6c92-4dc1-80e4-71c2fefca495_捕获.PNG', 'mihoyo', '妮露', 24, '2023-07-22 08:40:46', NULL, 0, '2023-07-22 08:41:22', '2023-07-22 08:41:22');
INSERT INTO `video_info` VALUES ('1683373796746121218', '鬼灭之刃 锻刀村篇', '卖炭维生的善良少年炭治郎，某天家人惨遭鬼杀害。而唯一幸存的妹妹祢豆子，却变化成鬼。遭到绝望现实打击的炭治郎，为了让妹妹恢复回人类，为了被杀害的家人向鬼报仇，决定踏上”猎鬼”之路。', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-24/99460edc-3409-4973-97a8-5302e45b2cad_捕获.PNG', '外崎春雄', '花江夏树 下野纮 松冈祯丞 鬼头明里 宫野真守 花泽香菜 河西健吾', 24, '2023-07-24 15:09:00', NULL, 0, '2023-07-24 15:09:23', '2023-08-09 16:03:27');
INSERT INTO `video_info` VALUES ('1683374868122357762', '鬼灭之刃 游郭篇', '鬼灭之刃游郭篇故事剧情将从《鬼灭之刃无限列车篇》失去炎柱后做为开端，以日本花街为舞台，而最让人期待的音柱、堕姬即将在鬼灭第二季登场！', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-24/032df54a-fb38-40da-a64d-893223e20e4d_20230411164723_513.jpg', '外崎春雄', '花江夏树 鬼头明里 下野纮 松冈祯丞 小西克幸 森川智之 泽城美雪 逢坂良太 石上静香 东山奈央 种崎敦美 关俊彦 浪川大辅 上田丽奈 江原裕理 石田彰', 24, '2023-07-24 15:12:27', NULL, 0, '2023-07-24 15:13:39', '2023-07-24 15:13:39');
INSERT INTO `video_info` VALUES ('1689450889653301249', '誓约', '《誓约》是2012年的一部美国浪漫电影。导演是迈克尔·苏克西，由瑞秋·麦克亚当斯、查尼·泰坦、山姆·尼尔等主演。影片改编自真实故事。于2011年8月至11月拍摄。2012年2月首映。', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/2cc8206e-f195-4d7c-9d37-eaebb7cea5a6_the-vow-2.jpg', '迈克尔·苏克西', '瑞秋·麦克亚当斯、查尼·泰坦、山姆·尼尔', 27, '2012-02-01 00:00:00', NULL, 0, '2023-08-10 09:37:35', '2023-08-10 09:37:35');
INSERT INTO `video_info` VALUES ('1689451900174381058', '第五波', '《第五波》是2016年的一部美国科幻动作电影，由J·布莱克森执导，以及由苏珊娜·葛兰特、阿奇瓦·高斯曼及杰夫·平克纳担任编剧，改编自瑞克·杨西于2013年的同名小说。电影由科洛·莫瑞兹、尼克·罗宾森、荣·利文斯通、玛姬·丝弗、阿莱克斯·罗、玛丽亚·贝罗、麦卡·梦露、列夫·施赖伯等人主演。', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/848de928-0a90-4191-8242-a309de173d04_MV5BOTI5NDQ2N2ItZTAyMi00YjIxLTkzMWMtN2UzODJlN2M2YTYyXkEyXkFqcGdeQXVyNzExMzc0MDg@._V1_.jpg', 'J·布莱克森', '科洛·莫瑞兹、尼克·罗宾森、荣·利文斯通、玛姬·丝弗、阿莱克斯·罗、玛丽亚·贝罗、麦卡·梦露、列夫·施赖', 26, '2016-01-01 00:00:00', NULL, 0, '2023-08-10 09:41:36', '2023-08-10 09:41:36');
INSERT INTO `video_info` VALUES ('1689454145213345793', '全面回忆', '《全面回忆》是一部2012年美国科幻动作片，根据1990年的英文同名电影和菲利普·狄克的1966年中篇小说《We Can Remember It for You Wholesale》改编而成。', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/9ec42129-61f5-4047-8ef2-d09dd679ff11_total-recall-movie (21).jpg', '保罗·范霍文', '菲利普·迪克 罗纳德·舒塞特 丹·欧班农 Jon Povill 加里·戈德曼 雷切尔·蒂科汀 莎朗·斯通 罗尼·考克斯 迈克尔·艾恩塞德 马绍尔·贝尔 梅尔约翰逊 迈克尔切姆平 罗伊·布罗克斯史密斯 雷·贝克 萝丝玛丽·邓斯莫尔 戴维·奈尔 阿列卡夏罗宾逊 迪恩·诺里斯 马克卡尔顿 黛比·李·卡林顿 Lycia Naff 罗伯特·康斯坦佐 Michael LaGuardia Priscilla Allen 马克·阿莱莫 迈克尔·格雷戈里', 26, '2012-01-01 00:00:00', NULL, 0, '2023-08-10 09:50:31', '2023-08-10 09:50:31');
INSERT INTO `video_info` VALUES ('1689455440183734273', '最长的旅程', '《最长的旅程》是一部美国2015年浪漫剧情片，为小乔治·提曼所执导，克雷格·波顿编剧。电影改编自尼可拉斯·史派克的2013年小说《The Longest Ride》。由布丽特妮·罗伯森、斯科特·伊斯威特、杰克·休斯顿、奥娜·卓别林、亚伦·艾达、梅莉莎·班诺伊、洛莉塔·黛维琪和葛萝莉亚·鲁宾主演。为二十世紀福克斯负责发行，美国于2015年4月10日上映。', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/0a7cde10-d827-44f3-a908-05a646ee157f_69917b294a20135e07043db1e09b27e5.jpg', '小乔治·提曼', '布丽特妮·罗伯森、斯科特·伊斯威特、杰克·休斯顿、奥娜·卓别林、亚伦·艾达、梅莉莎·班诺伊、洛莉塔·黛维琪和葛萝莉亚·鲁宾', 27, '2015-01-01 00:00:00', NULL, 0, '2023-08-10 09:55:40', '2023-08-10 09:55:40');
INSERT INTO `video_info` VALUES ('1689457959261745154', '蜘蛛侠：纵横宇宙', '《蜘蛛侠：纵横宇宙》（英语：Spider-Man: Across the Spider-Verse，香港译《蜘蛛侠：飞跃蜘蛛宇宙》，台湾译《蜘蛛人：穿越新宇宙》）是一部2023年美国电脑动画超级英雄电影，2018年电影《蜘蛛侠：平行宇宙》的续集，亦是《蜘蛛宇宙系列》的第二部电影。本片由瓦昆·杜斯·山托斯、肯普·包沃斯和贾斯汀·K·汤普森共同执导，大卫·卡拉汉、菲尔·罗德与克里斯托弗·米勒共同撰写剧本；声演阵容包括沙梅克·摩尔、海莉·斯坦菲尔德、布莱恩·泰里·亨利、萝伦·维莱斯、杰克·强森、杰森·薛兹曼、伊萨·雷、卡兰·索尼、谢伊·惠格姆、格里塔·李、丹尼尔·卡卢亚、马赫沙拉·阿里以及奥斯卡·伊萨克。本片2023年6月2日在美国上映。', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/fce67f1f-0d2c-4c9f-a5cf-4ce53b3c3179_spider-man-into-the-spider-verse-miles-morales-spider-man-2880x1800-2948.jpg', '瓦昆·杜斯·山托斯、肯普·包沃斯和贾斯汀·K·汤普森', '沙梅克·摩尔、海莉·斯坦菲尔德、布莱恩·泰里·亨利、萝伦·维莱斯、杰克·强森、杰森·薛兹曼、伊萨·雷、卡兰·索尼、谢伊·惠格姆、格里塔·李、丹尼尔·卡卢亚、马赫沙拉·阿里以及奥斯卡·伊萨克', 28, '2023-06-02 00:00:00', NULL, 0, '2023-08-10 10:05:41', '2023-08-10 10:05:41');
INSERT INTO `video_info` VALUES ('1689463385999040514', '蜘蛛侠：平行宇宙', '《蜘蛛侠：平行宇宙》（英语：Spider-Man: Into the Spider-Verse）是一部于2018年上映的美国电脑动画超级英雄电影，本片为蜘蛛宇宙系列的第一部电影，由哥伦比亚影业、漫威娱乐和索尼动画联合制作，并由索尼影视娱乐负责发行。本片由鲍伯·培斯奇提、彼得·拉姆齐与罗德斯尼·罗斯曼执导，并由菲尔·罗德与克里斯托弗·米勒撰写剧本，剧情围绕着由布瑞恩·马克·班迪斯与莎拉·皮谢利创作的漫威漫画角色迈尔斯·莫拉莱斯／蜘蛛侠展开。本片由沙梅克·摩尔、海莉·斯坦菲尔德、列夫·施赖伯、马赫沙拉·阿里、布莱恩·泰里·亨利和杰克·强森担任主要配音员。\r\n\r\n由菲尔·罗德和克里斯托弗·米勒共同筹拍的一部蜘蛛侠动画电影的计划于2014年首度公开，并于2015年4月受到证实。沙梅克·摩尔与列夫·施赖伯于2017年4月加入剧组，而马赫沙拉·阿里和布莱恩·泰里·亨利则于同年6月签约参演。\r\n\r\n本片的片名于12月公布。该片于2018年12月14日在美国上映。电影发行后口碑和票房双收，动画效果、动作场面、角色设计、编剧皆获得正面评价，本片同时获颁众多奖项，这其中包含第91届奥斯卡金像奖的最佳动画长片奖、第46届安妮奖的七项奖项、第76届金球奖最佳动画。', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/803380b7-a437-4c28-a038-4a0d96587fd7_994927.jpg', '鲍伯·培斯奇提、彼得·拉姆齐与罗德斯尼·罗斯曼', '沙梅克·摩尔、海莉·斯坦菲尔德、列夫·施赖伯、马赫沙拉·阿里、布莱恩·泰里·亨利和杰克·强森', 28, '2018-12-04 00:00:00', NULL, 0, '2023-08-10 10:27:15', '2023-08-10 10:27:15');

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
INSERT INTO `video_series_info` VALUES ('1682564048132014082', '1682551371754180610', 1, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/89a91135-f3f3-4147-9ef6-2240702d1acb_nilou.mp4', 16, 0, '2023-07-22 09:31:44', '2023-07-22 09:31:44');
INSERT INTO `video_series_info` VALUES ('1683373881982767105', '1683373796746121218', 5, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/%5BUHA-WINGS%5D%20%5BKimetsu%20no%20Yaiba%20-%20Katanakaji%20no%20Sato-hen%5D%5B05%5D%5Bx264%201080p%5D%5BCHS%5D.mp4', 16, 0, '2023-07-24 15:09:44', '2023-07-24 15:09:44');
INSERT INTO `video_series_info` VALUES ('1683374990189187074', '1683373796746121218', 6, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-22/%5BUHA-WINGS%5D%20%5BKimetsu%20no%20Yaiba%20-%20Katanakaji%20no%20Sato-hen%5D%5B06%5D%5Bx264%201080p%5D%5BCHS%5D.mp4', 16, 0, '2023-07-24 15:14:08', '2023-07-24 15:14:08');
INSERT INTO `video_series_info` VALUES ('1689451327383449601', '1689450889653301249', 1, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/a7f4c092-b5d0-4c15-98ce-8b038c5e0d75_[UHA-WINGS] [Kimetsu no Yaiba - Katanakaji no Sato-hen][05][x264 1080p][CHS]_Trim_Trim.mp4', 16, 0, '2023-08-10 09:39:20', '2023-08-10 09:39:20');
INSERT INTO `video_series_info` VALUES ('1689452010799149057', '1689451900174381058', 1, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/a7f4c092-b5d0-4c15-98ce-8b038c5e0d75_%5BUHA-WINGS%5D%20%5BKimetsu%20no%20Yaiba%20-%20Katanakaji%20no%20Sato-hen%5D%5B05%5D%5Bx264%201080p%5D%5BCHS%5D_Trim_Trim_1.mp4', 16, 0, '2023-08-10 09:42:03', '2023-08-10 09:42:03');
INSERT INTO `video_series_info` VALUES ('1689454269515739137', '1689454145213345793', 1, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/a7f4c092-b5d0-4c15-98ce-8b038c5e0d75_%5BUHA-WINGS%5D%20%5BKimetsu%20no%20Yaiba%20-%20Katanakaji%20no%20Sato-hen%5D%5B05%5D%5Bx264%201080p%5D%5BCHS%5D_Trim_Trim_2.mp4', 16, 0, '2023-08-10 09:51:01', '2023-08-10 09:51:01');
INSERT INTO `video_series_info` VALUES ('1689455536757583873', '1689455440183734273', 1, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/a7f4c092-b5d0-4c15-98ce-8b038c5e0d75_%5BUHA-WINGS%5D%20%5BKimetsu%20no%20Yaiba%20-%20Katanakaji%20no%20Sato-hen%5D%5B05%5D%5Bx264%201080p%5D%5BCHS%5D_Trim_Trim_3.mp4', 16, 0, '2023-08-10 09:56:03', '2023-08-10 09:56:03');
INSERT INTO `video_series_info` VALUES ('1689458313504256001', '1689457959261745154', 1, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/a7f4c092-b5d0-4c15-98ce-8b038c5e0d75_%5BUHA-WINGS%5D%20%5BKimetsu%20no%20Yaiba%20-%20Katanakaji%20no%20Sato-hen%5D%5B05%5D%5Bx264%201080p%5D%5BCHS%5D_Trim_Trim_4.mp4', 16, 0, '2023-08-10 10:07:05', '2023-08-10 10:07:05');
INSERT INTO `video_series_info` VALUES ('1689463579691999234', '1689463385999040514', 1, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-10/a7f4c092-b5d0-4c15-98ce-8b038c5e0d75_%5BUHA-WINGS%5D%20%5BKimetsu%20no%20Yaiba%20-%20Katanakaji%20no%20Sato-hen%5D%5B05%5D%5Bx264%201080p%5D%5BCHS%5D_Trim_Trim_5.mp4', 16, 0, '2023-08-10 10:28:01', '2023-08-10 10:28:01');

SET FOREIGN_KEY_CHECKS = 1;
