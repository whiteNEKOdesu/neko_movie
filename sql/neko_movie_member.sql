/*
 Navicat Premium Data Transfer

 Source Server         : vm
 Source Server Type    : MySQL
 Source Server Version : 50738 (5.7.38)
 Source Host           : 192.168.30.131:3306
 Source Schema         : neko_movie_member

 Target Server Type    : MySQL
 Target Server Version : 50738 (5.7.38)
 File Encoding         : 65001

 Date: 10/08/2023 16:34:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_info
-- ----------------------------
DROP TABLE IF EXISTS `admin_info`;
CREATE TABLE `admin_info`  (
                               `admin_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员id',
                               `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员名',
                               `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
                               `salt` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码盐',
                               `user_image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
                               `operate_admin_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '指认管理员id',
                               `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                               `create_time` datetime NOT NULL COMMENT '创建时间',
                               `update_time` datetime NOT NULL COMMENT '更新时间',
                               PRIMARY KEY (`admin_id`) USING BTREE,
                               UNIQUE INDEX `idx_user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_info
-- ----------------------------
INSERT INTO `admin_info` VALUES ('1642398369596944385', 'NEKO', '55c914c5b871c4231d8034bdf2848886', '[70, -58, -15, -127, 82, 126, -113, 120, -88, 28]', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_market/2023-01-22/1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', NULL, 0, '2023-04-01 21:17:37', '2023-04-01 21:17:41');

-- ----------------------------
-- Table structure for admin_log_in_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_log_in_log`;
CREATE TABLE `admin_log_in_log`  (
                                     `log_in_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录日志id',
                                     `admin_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员id，对应admin_id表admin_id',
                                     `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录ip',
                                     `is_log_in` tinyint(1) NOT NULL COMMENT '是否登录成功',
                                     `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                     `create_time` datetime NOT NULL COMMENT '创建时间',
                                     `update_time` datetime NOT NULL COMMENT '更新时间',
                                     PRIMARY KEY (`log_in_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员登录记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_log_in_log
-- ----------------------------
INSERT INTO `admin_log_in_log` VALUES ('1642416265249206274', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 14:38:47', '2023-04-02 14:38:47');
INSERT INTO `admin_log_in_log` VALUES ('1642419861147615233', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 14:53:04', '2023-04-02 14:53:04');
INSERT INTO `admin_log_in_log` VALUES ('1642492900002623490', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-02 19:43:18', '2023-04-02 19:43:18');
INSERT INTO `admin_log_in_log` VALUES ('1642493967104864258', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-02 19:47:32', '2023-04-02 19:47:32');
INSERT INTO `admin_log_in_log` VALUES ('1642494126937206786', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-02 19:48:11', '2023-04-02 19:48:11');
INSERT INTO `admin_log_in_log` VALUES ('1642494311956254722', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-02 19:48:55', '2023-04-02 19:48:55');
INSERT INTO `admin_log_in_log` VALUES ('1642494312325353474', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-02 19:48:55', '2023-04-02 19:48:55');
INSERT INTO `admin_log_in_log` VALUES ('1642494313101299714', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-02 19:48:55', '2023-04-02 19:48:55');
INSERT INTO `admin_log_in_log` VALUES ('1642495032185360386', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-02 19:51:46', '2023-04-02 19:51:46');
INSERT INTO `admin_log_in_log` VALUES ('1642496544341004290', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-02 19:57:47', '2023-04-02 19:57:47');
INSERT INTO `admin_log_in_log` VALUES ('1642496956573978626', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-02 19:59:25', '2023-04-02 19:59:25');
INSERT INTO `admin_log_in_log` VALUES ('1642497002245754882', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-02 19:59:36', '2023-04-02 19:59:36');
INSERT INTO `admin_log_in_log` VALUES ('1642497239370731521', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-02 20:00:33', '2023-04-02 20:00:33');
INSERT INTO `admin_log_in_log` VALUES ('1642765190535692290', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-03 13:45:17', '2023-04-03 13:45:17');
INSERT INTO `admin_log_in_log` VALUES ('1642772043692883970', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-03 14:12:31', '2023-04-03 14:12:31');
INSERT INTO `admin_log_in_log` VALUES ('1642781187774611458', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-03 14:48:51', '2023-04-03 14:48:51');
INSERT INTO `admin_log_in_log` VALUES ('1642781567799525378', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-03 14:50:22', '2023-04-03 14:50:22');
INSERT INTO `admin_log_in_log` VALUES ('1642781809844420609', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-03 14:51:20', '2023-04-03 14:51:20');
INSERT INTO `admin_log_in_log` VALUES ('1642783201577402369', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-03 14:56:51', '2023-04-03 14:56:51');
INSERT INTO `admin_log_in_log` VALUES ('1643134908572368898', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-04 14:14:25', '2023-04-04 14:14:25');
INSERT INTO `admin_log_in_log` VALUES ('1643170479814569985', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-04 16:35:46', '2023-04-04 16:35:46');
INSERT INTO `admin_log_in_log` VALUES ('1644195521536983042', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-07 12:28:55', '2023-04-07 12:28:55');
INSERT INTO `admin_log_in_log` VALUES ('1644256285408993282', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-07 16:30:22', '2023-04-07 16:30:22');
INSERT INTO `admin_log_in_log` VALUES ('1644556230473125889', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-08 12:22:14', '2023-04-08 12:22:14');
INSERT INTO `admin_log_in_log` VALUES ('1646734449062195202', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-14 12:37:42', '2023-04-14 12:37:42');
INSERT INTO `admin_log_in_log` VALUES ('1646745901777047554', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-14 13:23:13', '2023-04-14 13:23:13');
INSERT INTO `admin_log_in_log` VALUES ('1647137830964649986', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-15 15:20:36', '2023-04-15 15:20:36');
INSERT INTO `admin_log_in_log` VALUES ('1647143953436336130', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-15 15:44:56', '2023-04-15 15:44:56');
INSERT INTO `admin_log_in_log` VALUES ('1647480141909098498', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-16 14:00:49', '2023-04-16 14:00:49');
INSERT INTO `admin_log_in_log` VALUES ('1647485729015627778', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-16 14:23:01', '2023-04-16 14:23:01');
INSERT INTO `admin_log_in_log` VALUES ('1647491368622260225', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-16 14:45:26', '2023-04-16 14:45:26');
INSERT INTO `admin_log_in_log` VALUES ('1647502381363003393', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-16 15:29:11', '2023-04-16 15:29:11');
INSERT INTO `admin_log_in_log` VALUES ('1647838693324505089', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-17 13:45:35', '2023-04-17 13:45:35');
INSERT INTO `admin_log_in_log` VALUES ('1648198127540269058', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-18 13:33:50', '2023-04-18 13:33:50');
INSERT INTO `admin_log_in_log` VALUES ('1648200613411975170', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-18 13:43:43', '2023-04-18 13:43:43');
INSERT INTO `admin_log_in_log` VALUES ('1648200664137887745', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-18 13:43:55', '2023-04-18 13:43:55');
INSERT INTO `admin_log_in_log` VALUES ('1648211688811438082', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-18 14:27:44', '2023-04-18 14:27:44');
INSERT INTO `admin_log_in_log` VALUES ('1650744054897516545', '1642398369596944385', '192.168.30.129', 1, 0, '2023-04-25 14:10:27', '2023-04-25 14:10:27');
INSERT INTO `admin_log_in_log` VALUES ('1652925146874355714', '1642398369596944385', '192.168.30.129', 1, 0, '2023-05-01 14:37:20', '2023-05-01 14:37:20');
INSERT INTO `admin_log_in_log` VALUES ('1653298332874858498', '1642398369596944385', '192.168.30.129', 1, 0, '2023-05-02 15:20:14', '2023-05-02 15:20:14');
INSERT INTO `admin_log_in_log` VALUES ('1660132730865745922', '1642398369596944385', '192.168.30.129', 1, 0, '2023-05-21 11:57:41', '2023-05-21 11:57:41');
INSERT INTO `admin_log_in_log` VALUES ('1661583747989921794', '1642398369596944385', '192.168.30.129', 1, 0, '2023-05-25 12:03:31', '2023-05-25 12:03:31');
INSERT INTO `admin_log_in_log` VALUES ('1661965442945662978', '1642398369596944385', '192.168.30.129', 1, 0, '2023-05-26 13:20:14', '2023-05-26 13:20:14');
INSERT INTO `admin_log_in_log` VALUES ('1661972946517831681', '1642398369596944385', '192.168.30.129', 1, 0, '2023-05-26 13:50:03', '2023-05-26 13:50:03');
INSERT INTO `admin_log_in_log` VALUES ('1661975775777206274', '1642398369596944385', '192.168.30.129', 1, 0, '2023-05-26 14:01:18', '2023-05-26 14:01:18');
INSERT INTO `admin_log_in_log` VALUES ('1661985259585019905', '1642398369596944385', '192.168.30.129', 1, 0, '2023-05-26 14:38:59', '2023-05-26 14:38:59');
INSERT INTO `admin_log_in_log` VALUES ('1662693252920324097', '1642398369596944385', '192.168.30.129', 1, 0, '2023-05-28 13:32:18', '2023-05-28 13:32:18');
INSERT INTO `admin_log_in_log` VALUES ('1662711758688677889', '1642398369596944385', '192.168.30.129', 1, 0, '2023-05-28 14:45:50', '2023-05-28 14:45:50');
INSERT INTO `admin_log_in_log` VALUES ('1662723179094798337', '1642398369596944385', '192.168.30.129', 1, 0, '2023-05-28 15:31:12', '2023-05-28 15:31:12');
INSERT INTO `admin_log_in_log` VALUES ('1666684755262836738', '1642398369596944385', '192.168.30.129', 1, 0, '2023-06-08 13:53:06', '2023-06-08 13:53:06');
INSERT INTO `admin_log_in_log` VALUES ('1667037875201495041', '1642398369596944385', '192.168.30.129', 1, 0, '2023-06-09 13:16:16', '2023-06-09 13:16:16');
INSERT INTO `admin_log_in_log` VALUES ('1667428315830427649', '1642398369596944385', '192.168.30.129', 1, 0, '2023-06-10 15:07:44', '2023-06-10 15:07:44');
INSERT INTO `admin_log_in_log` VALUES ('1667428773059895297', '1642398369596944385', '192.168.30.129', 1, 0, '2023-06-10 15:09:33', '2023-06-10 15:09:33');
INSERT INTO `admin_log_in_log` VALUES ('1668136049345372162', '1642398369596944385', '192.168.30.129', 1, 0, '2023-06-12 14:00:01', '2023-06-12 14:00:01');
INSERT INTO `admin_log_in_log` VALUES ('1668136454531915778', '1642398369596944385', '192.168.30.129', 1, 0, '2023-06-12 14:01:38', '2023-06-12 14:01:38');
INSERT INTO `admin_log_in_log` VALUES ('1672843568307318785', '1642398369596944385', '192.168.30.129', 1, 0, '2023-06-25 13:46:01', '2023-06-25 13:46:01');
INSERT INTO `admin_log_in_log` VALUES ('1673951981665779714', '1642398369596944385', '192.168.30.129', 1, 0, '2023-06-28 15:10:28', '2023-06-28 15:10:28');
INSERT INTO `admin_log_in_log` VALUES ('1673954167254679554', '1642398369596944385', '192.168.30.129', 1, 0, '2023-06-28 15:19:09', '2023-06-28 15:19:09');
INSERT INTO `admin_log_in_log` VALUES ('1673959293545611265', '1642398369596944385', '192.168.30.129', 1, 0, '2023-06-28 15:39:31', '2023-06-28 15:39:31');
INSERT INTO `admin_log_in_log` VALUES ('1677569946407784449', '1642398369596944385', '192.168.30.129', 1, 0, '2023-07-08 14:46:58', '2023-07-08 14:46:58');
INSERT INTO `admin_log_in_log` VALUES ('1683390106787905537', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-24 16:14:12', '2023-07-24 16:14:12');
INSERT INTO `admin_log_in_log` VALUES ('1683628922467528705', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-25 08:03:10', '2023-07-25 08:03:10');
INSERT INTO `admin_log_in_log` VALUES ('1683724522110369794', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-25 14:23:03', '2023-07-25 14:23:03');
INSERT INTO `admin_log_in_log` VALUES ('1683992735280885762', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-26 08:08:50', '2023-07-26 08:08:50');
INSERT INTO `admin_log_in_log` VALUES ('1683992832341274625', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-26 08:09:13', '2023-07-26 08:09:13');
INSERT INTO `admin_log_in_log` VALUES ('1684089085007831041', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-26 14:31:41', '2023-07-26 14:31:41');
INSERT INTO `admin_log_in_log` VALUES ('1684355431515893761', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-27 08:10:03', '2023-07-27 08:10:03');
INSERT INTO `admin_log_in_log` VALUES ('1684399137887375362', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-27 11:03:44', '2023-07-27 11:03:44');
INSERT INTO `admin_log_in_log` VALUES ('1684452018510438401', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-27 14:33:51', '2023-07-27 14:33:51');
INSERT INTO `admin_log_in_log` VALUES ('1684454368813817857', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-27 14:43:12', '2023-07-27 14:43:12');
INSERT INTO `admin_log_in_log` VALUES ('1684718804162146305', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-28 08:13:58', '2023-07-28 08:13:58');
INSERT INTO `admin_log_in_log` VALUES ('1684811490076459009', '1642398369596944385', '0:0:0:0:0:0:0:1', 0, 0, '2023-07-28 14:22:16', '2023-07-28 14:22:16');
INSERT INTO `admin_log_in_log` VALUES ('1684811517339435009', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-28 14:22:23', '2023-07-28 14:22:23');
INSERT INTO `admin_log_in_log` VALUES ('1685079700717756418', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-29 08:08:03', '2023-07-29 08:08:03');
INSERT INTO `admin_log_in_log` VALUES ('1685174971795615746', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-29 14:26:37', '2023-07-29 14:26:37');
INSERT INTO `admin_log_in_log` VALUES ('1685802302482485250', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-31 07:59:24', '2023-07-31 07:59:24');
INSERT INTO `admin_log_in_log` VALUES ('1685899538751037442', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-31 14:25:47', '2023-07-31 14:25:47');
INSERT INTO `admin_log_in_log` VALUES ('1686166153128357889', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-01 08:05:13', '2023-08-01 08:05:13');
INSERT INTO `admin_log_in_log` VALUES ('1686282606724833281', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-01 15:47:58', '2023-08-01 15:47:58');
INSERT INTO `admin_log_in_log` VALUES ('1688343594710138882', '1642398369596944385', '0:0:0:0:0:0:0:1', 0, 0, '2023-08-07 08:17:36', '2023-08-07 08:17:36');
INSERT INTO `admin_log_in_log` VALUES ('1688343618978381825', '1642398369596944385', '0:0:0:0:0:0:0:1', 0, 0, '2023-08-07 08:17:41', '2023-08-07 08:17:41');
INSERT INTO `admin_log_in_log` VALUES ('1688343649470971905', '1642398369596944385', '0:0:0:0:0:0:0:1', 0, 0, '2023-08-07 08:17:49', '2023-08-07 08:17:49');
INSERT INTO `admin_log_in_log` VALUES ('1688343676968828929', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-07 08:17:55', '2023-08-07 08:17:55');
INSERT INTO `admin_log_in_log` VALUES ('1688437444384440322', '1642398369596944385', '0:0:0:0:0:0:0:1', 0, 0, '2023-08-07 14:30:31', '2023-08-07 14:30:31');
INSERT INTO `admin_log_in_log` VALUES ('1688437471697747970', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-07 14:30:38', '2023-08-07 14:30:38');
INSERT INTO `admin_log_in_log` VALUES ('1688704240794578945', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-08 08:10:40', '2023-08-08 08:10:40');
INSERT INTO `admin_log_in_log` VALUES ('1688800098940706818', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-08 14:31:35', '2023-08-08 14:31:35');
INSERT INTO `admin_log_in_log` VALUES ('1689068112860729346', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-09 08:16:34', '2023-08-09 08:16:34');
INSERT INTO `admin_log_in_log` VALUES ('1689163808540516353', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-09 14:36:50', '2023-08-09 14:36:50');
INSERT INTO `admin_log_in_log` VALUES ('1689427899699982338', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-10 08:06:14', '2023-08-10 08:06:14');
INSERT INTO `admin_log_in_log` VALUES ('1689526269517029377', '1642398369596944385', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-10 14:37:07', '2023-08-10 14:37:07');

-- ----------------------------
-- Table structure for member_info
-- ----------------------------
DROP TABLE IF EXISTS `member_info`;
CREATE TABLE `member_info`  (
                                `uid` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
                                `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
                                `user_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
                                `salt` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码盐',
                                `source_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '社交登录名',
                                `user_image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
                                `gender` tinyint(1) NULL DEFAULT NULL COMMENT '性别',
                                `source` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '社交登录来源',
                                `source_uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '社交登录来源uid',
                                `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
                                `id_card_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
                                `id_card_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证图片url',
                                `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
                                `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
                                `is_ban` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否封禁',
                                `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                `create_time` datetime NOT NULL COMMENT '创建时间',
                                `update_time` datetime NOT NULL COMMENT '更新时间',
                                PRIMARY KEY (`uid`) USING BTREE,
                                UNIQUE INDEX `idx_user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member_info
-- ----------------------------
INSERT INTO `member_info` VALUES ('1642067605873348610', 'NEKO', 'f819e011ccf284ffe99b223c4603615d', '[70, -58, -15, -127, 82, 126, -113, 120, -88, 28]', NULL, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-07-25/1430d607-b0a4-4f83-aa5c-bedb89a0d888_301882efc3b3dcabaad03a7b607ab228.jpg', NULL, NULL, NULL, 'NEKO', '420881200101184053', 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_convenient/2023-05-26/0b578be8-82d4-4967-8261-9d686d963a71_1ad3ffe0-e81f-4439-8937-2fc22e2045ba_A400388D-AE79-4F23-8390-7D549A78D795.jpeg', NULL, 'NEKO@NEKO.com', 0, 0, '2023-04-01 15:33:20', '2023-07-29 17:40:33');
INSERT INTO `member_info` VALUES ('1645332216386969601', NULL, NULL, NULL, 'nekochannanodesu', NULL, NULL, 'gitee', '12356244', NULL, NULL, NULL, NULL, NULL, 0, 0, '2023-04-10 15:45:44', '2023-04-10 15:45:44');
INSERT INTO `member_info` VALUES ('1684853753762611201', 'zhou', '2b03cd2e03e8f100d50d32749c3d6e61', '[-6, -37, 120, 123, 80, 85, -11, -58, 22, -83]', NULL, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-08/0c003fb9-3ed0-4d8d-aedf-c08b255853f1_301882efc3b3dcabaad03a7b607ab228.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2531567053@qq.com', 0, 0, '2023-07-28 17:10:13', '2023-08-08 16:36:34');
INSERT INTO `member_info` VALUES ('1685850632348516354', 'xian', '213a97052dcf83d015d0580fe9a66924', '[-43, 36, -71, 122, -30, 10, 43, 19, 13, 28]', NULL, 'https://neko-bucket.oss-cn-shanghai.aliyuncs.com/neko/neko_movie/2023-08-08/f853539e-37eb-48aa-a9d3-e30059c9353d_301882efc3b3dcabaad03a7b607ab228.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1336182556@qq.com', 0, 0, '2023-07-31 11:11:27', '2023-08-08 17:22:45');

-- ----------------------------
-- Table structure for member_level_dict
-- ----------------------------
DROP TABLE IF EXISTS `member_level_dict`;
CREATE TABLE `member_level_dict`  (
                                      `member_level_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '等级id',
                                      `role_id` int(11) NOT NULL COMMENT '角色id，对应user_role表role_id',
                                      `price` decimal(20, 2) NOT NULL COMMENT '开通价格/月',
                                      `level` int(255) NOT NULL COMMENT '等级排序，最低0',
                                      `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                      `create_time` datetime NOT NULL COMMENT '创建时间',
                                      `update_time` datetime NOT NULL COMMENT '更新时间',
                                      PRIMARY KEY (`member_level_id`) USING BTREE,
                                      UNIQUE INDEX `idx_level`(`level`) USING BTREE,
                                      INDEX `idx_for_member_level_dict_role_id`(`role_id`) USING BTREE,
                                      CONSTRAINT `idx_for_member_level_dict_role_id` FOREIGN KEY (`role_id`) REFERENCES `user_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户等级字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member_level_dict
-- ----------------------------
INSERT INTO `member_level_dict` VALUES (1, 11, 0.00, 0, 0, '2023-05-02 14:22:53', '2023-05-02 14:22:57');
INSERT INTO `member_level_dict` VALUES (4, 15, 10.00, 1, 0, '2023-07-26 16:09:56', '2023-07-26 16:09:56');
INSERT INTO `member_level_dict` VALUES (5, 16, 15.00, 2, 0, '2023-07-26 16:10:09', '2023-07-26 16:10:09');

-- ----------------------------
-- Table structure for member_level_relation
-- ----------------------------
DROP TABLE IF EXISTS `member_level_relation`;
CREATE TABLE `member_level_relation`  (
                                          `relation_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关系id',
                                          `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
                                          `member_level_id` int(255) NOT NULL COMMENT '会员等级id，对应member_level_dict表member_level_id',
                                          `level_expire_time` datetime NOT NULL COMMENT '等级到期时间',
                                          `update_version` int(255) NOT NULL DEFAULT 0 COMMENT '乐观锁',
                                          `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                          `create_time` datetime NOT NULL COMMENT '创建时间',
                                          `update_time` datetime NOT NULL COMMENT '修改时间',
                                          PRIMARY KEY (`relation_id`) USING BTREE,
                                          INDEX `idx_uid`(`uid`) USING BTREE,
                                          INDEX `idx_for_member_level_relation_member_level_id`(`member_level_id`) USING BTREE,
                                          CONSTRAINT `idx_for_member_level_relation_member_level_id` FOREIGN KEY (`member_level_id`) REFERENCES `member_level_dict` (`member_level_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户，会员等级关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member_level_relation
-- ----------------------------
INSERT INTO `member_level_relation` VALUES ('1688832208925712386', '1684853753762611201', 4, '2023-09-08 16:39:10', 0, 0, '2023-08-08 16:39:10', '2023-08-08 16:39:10');
INSERT INTO `member_level_relation` VALUES ('1688843555113623554', '1685850632348516354', 4, '2023-09-08 17:24:15', 0, 0, '2023-08-08 17:24:15', '2023-08-08 17:24:15');

-- ----------------------------
-- Table structure for member_log_in_log
-- ----------------------------
DROP TABLE IF EXISTS `member_log_in_log`;
CREATE TABLE `member_log_in_log`  (
                                      `log_in_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录日志id',
                                      `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
                                      `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录ip',
                                      `is_log_in` tinyint(1) NOT NULL COMMENT '是否登录成功',
                                      `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                      `create_time` datetime NOT NULL COMMENT '创建时间',
                                      `update_time` datetime NOT NULL COMMENT '更新时间',
                                      PRIMARY KEY (`log_in_id`) USING BTREE,
                                      INDEX `idx_uid_create_time`(`uid`, `create_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户登录记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member_log_in_log
-- ----------------------------
INSERT INTO `member_log_in_log` VALUES ('1642074962732875778', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-01 16:02:34', '2023-04-01 16:02:34');
INSERT INTO `member_log_in_log` VALUES ('1642077250771812354', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-01 16:11:40', '2023-04-01 16:11:40');
INSERT INTO `member_log_in_log` VALUES ('1642077404925067266', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-01 16:12:16', '2023-04-01 16:12:16');
INSERT INTO `member_log_in_log` VALUES ('1642142873610641409', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-01 20:32:25', '2023-04-01 20:32:25');
INSERT INTO `member_log_in_log` VALUES ('1642143467943616513', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-01 20:34:47', '2023-04-01 20:34:47');
INSERT INTO `member_log_in_log` VALUES ('1642143579482742786', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-01 20:35:14', '2023-04-01 20:35:14');
INSERT INTO `member_log_in_log` VALUES ('1642143865358114817', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-01 20:36:22', '2023-04-01 20:36:22');
INSERT INTO `member_log_in_log` VALUES ('1642144692223176706', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-01 20:39:39', '2023-04-01 20:39:39');
INSERT INTO `member_log_in_log` VALUES ('1642146326885974018', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-01 20:46:09', '2023-04-01 20:46:09');
INSERT INTO `member_log_in_log` VALUES ('1642147558815969282', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-01 20:51:02', '2023-04-01 20:51:02');
INSERT INTO `member_log_in_log` VALUES ('1642379944900214786', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 12:14:27', '2023-04-02 12:14:27');
INSERT INTO `member_log_in_log` VALUES ('1642379960347836418', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 12:14:31', '2023-04-02 12:14:31');
INSERT INTO `member_log_in_log` VALUES ('1642379966224056321', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 12:14:33', '2023-04-02 12:14:33');
INSERT INTO `member_log_in_log` VALUES ('1642380003079405570', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 12:14:41', '2023-04-02 12:14:41');
INSERT INTO `member_log_in_log` VALUES ('1642406744917327873', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 14:00:57', '2023-04-02 14:00:57');
INSERT INTO `member_log_in_log` VALUES ('1642419634894274561', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 14:52:10', '2023-04-02 14:52:10');
INSERT INTO `member_log_in_log` VALUES ('1642421188779048961', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 14:58:21', '2023-04-02 14:58:21');
INSERT INTO `member_log_in_log` VALUES ('1642437178434801666', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 16:01:53', '2023-04-02 16:01:53');
INSERT INTO `member_log_in_log` VALUES ('1642437773623316482', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 16:04:15', '2023-04-02 16:04:15');
INSERT INTO `member_log_in_log` VALUES ('1642443181373018114', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 16:25:44', '2023-04-02 16:25:44');
INSERT INTO `member_log_in_log` VALUES ('1642443200205443074', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 16:25:49', '2023-04-02 16:25:49');
INSERT INTO `member_log_in_log` VALUES ('1642443218136092674', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-02 16:25:53', '2023-04-02 16:25:53');
INSERT INTO `member_log_in_log` VALUES ('1645330202575790082', '1645330202118610946', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-10 15:37:44', '2023-04-10 15:37:44');
INSERT INTO `member_log_in_log` VALUES ('1645331771396481025', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-10 15:43:58', '2023-04-10 15:43:58');
INSERT INTO `member_log_in_log` VALUES ('1645332216449884162', '1645332216386969601', '192.168.30.129', 1, 0, '2023-04-10 15:45:44', '2023-04-10 15:45:44');
INSERT INTO `member_log_in_log` VALUES ('1645658820929671170', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-11 13:23:32', '2023-04-11 13:23:32');
INSERT INTO `member_log_in_log` VALUES ('1645659199411081218', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-11 13:25:03', '2023-04-11 13:25:03');
INSERT INTO `member_log_in_log` VALUES ('1645669819602718722', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-11 14:07:15', '2023-04-11 14:07:15');
INSERT INTO `member_log_in_log` VALUES ('1646017841926840322', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-12 13:10:10', '2023-04-12 13:10:10');
INSERT INTO `member_log_in_log` VALUES ('1646739612112015362', '1642067605873348610', '192.168.30.129', 0, 0, '2023-04-14 12:58:13', '2023-04-14 12:58:13');
INSERT INTO `member_log_in_log` VALUES ('1646739644131332098', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-14 12:58:21', '2023-04-14 12:58:21');
INSERT INTO `member_log_in_log` VALUES ('1647141375298330626', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-15 15:34:41', '2023-04-15 15:34:41');
INSERT INTO `member_log_in_log` VALUES ('1647485141175533570', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-16 14:20:41', '2023-04-16 14:20:41');
INSERT INTO `member_log_in_log` VALUES ('1647486845522575362', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-16 14:27:27', '2023-04-16 14:27:27');
INSERT INTO `member_log_in_log` VALUES ('1647492662208528386', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-16 14:50:34', '2023-04-16 14:50:34');
INSERT INTO `member_log_in_log` VALUES ('1647870382218244098', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-17 15:51:30', '2023-04-17 15:51:30');
INSERT INTO `member_log_in_log` VALUES ('1647871389396463617', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-17 15:55:30', '2023-04-17 15:55:30');
INSERT INTO `member_log_in_log` VALUES ('1647876844021678081', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-17 16:17:10', '2023-04-17 16:17:10');
INSERT INTO `member_log_in_log` VALUES ('1647876979803881473', '1642067605873348610', '192.168.30.129', 0, 0, '2023-04-17 16:17:43', '2023-04-17 16:17:43');
INSERT INTO `member_log_in_log` VALUES ('1647887445397635073', '1642067605873348610', '192.168.30.129', 0, 0, '2023-04-17 16:59:18', '2023-04-17 16:59:18');
INSERT INTO `member_log_in_log` VALUES ('1647887526381256705', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-17 16:59:37', '2023-04-17 16:59:37');
INSERT INTO `member_log_in_log` VALUES ('1648178450122424321', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-18 12:15:39', '2023-04-18 12:15:39');
INSERT INTO `member_log_in_log` VALUES ('1648186339314663426', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-18 12:47:00', '2023-04-18 12:47:00');
INSERT INTO `member_log_in_log` VALUES ('1648190975002107905', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-18 13:05:25', '2023-04-18 13:05:25');
INSERT INTO `member_log_in_log` VALUES ('1648192555789107201', '1642067605873348610', '192.168.30.129', 0, 0, '2023-04-18 13:11:42', '2023-04-18 13:11:42');
INSERT INTO `member_log_in_log` VALUES ('1648195149303488514', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-18 13:22:00', '2023-04-18 13:22:00');
INSERT INTO `member_log_in_log` VALUES ('1648196390083371010', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-18 13:26:56', '2023-04-18 13:26:56');
INSERT INTO `member_log_in_log` VALUES ('1648196956767395841', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-04-18 13:29:11', '2023-04-18 13:29:11');
INSERT INTO `member_log_in_log` VALUES ('1648198914584637442', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-18 13:36:58', '2023-04-18 13:36:58');
INSERT INTO `member_log_in_log` VALUES ('1648200961459515394', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-18 13:45:06', '2023-04-18 13:45:06');
INSERT INTO `member_log_in_log` VALUES ('1648211927198900226', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-18 14:28:40', '2023-04-18 14:28:40');
INSERT INTO `member_log_in_log` VALUES ('1648559223040135170', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-19 13:28:42', '2023-04-19 13:28:42');
INSERT INTO `member_log_in_log` VALUES ('1648563651793145858', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-19 13:46:18', '2023-04-19 13:46:18');
INSERT INTO `member_log_in_log` VALUES ('1649652648007290882', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-22 13:53:35', '2023-04-22 13:53:35');
INSERT INTO `member_log_in_log` VALUES ('1649989942186610690', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-23 12:13:52', '2023-04-23 12:13:52');
INSERT INTO `member_log_in_log` VALUES ('1650051300693381121', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-23 16:17:41', '2023-04-23 16:17:41');
INSERT INTO `member_log_in_log` VALUES ('1650355381001646082', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-24 12:26:00', '2023-04-24 12:26:00');
INSERT INTO `member_log_in_log` VALUES ('1650722273792958465', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-25 12:43:54', '2023-04-25 12:43:54');
INSERT INTO `member_log_in_log` VALUES ('1650745039313248258', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-25 14:14:21', '2023-04-25 14:14:21');
INSERT INTO `member_log_in_log` VALUES ('1651487837146599426', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-27 15:25:58', '2023-04-27 15:25:58');
INSERT INTO `member_log_in_log` VALUES ('1652177213509951490', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-29 13:05:18', '2023-04-29 13:05:18');
INSERT INTO `member_log_in_log` VALUES ('1652538566464581633', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-30 13:01:12', '2023-04-30 13:01:12');
INSERT INTO `member_log_in_log` VALUES ('1652600199182393345', '1642067605873348610', '192.168.30.129', 1, 0, '2023-04-30 17:06:06', '2023-04-30 17:06:06');
INSERT INTO `member_log_in_log` VALUES ('1652904204924198914', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-01 13:14:07', '2023-05-01 13:14:07');
INSERT INTO `member_log_in_log` VALUES ('1652926146267947009', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-01 14:41:18', '2023-05-01 14:41:18');
INSERT INTO `member_log_in_log` VALUES ('1653265079140888578', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-02 13:08:06', '2023-05-02 13:08:06');
INSERT INTO `member_log_in_log` VALUES ('1653315630616596482', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-02 16:28:58', '2023-05-02 16:28:58');
INSERT INTO `member_log_in_log` VALUES ('1653619543836225538', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-03 12:36:37', '2023-05-03 12:36:37');
INSERT INTO `member_log_in_log` VALUES ('1653650098489421826', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-03 14:38:01', '2023-05-03 14:38:01');
INSERT INTO `member_log_in_log` VALUES ('1653988364724002818', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-04 13:02:10', '2023-05-04 13:02:10');
INSERT INTO `member_log_in_log` VALUES ('1654707810144993282', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-06 12:41:00', '2023-05-06 12:41:00');
INSERT INTO `member_log_in_log` VALUES ('1655071483908644865', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-07 12:46:06', '2023-05-07 12:46:06');
INSERT INTO `member_log_in_log` VALUES ('1655430247442554881', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-08 12:31:42', '2023-05-08 12:31:42');
INSERT INTO `member_log_in_log` VALUES ('1655788891216662529', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-09 12:16:49', '2023-05-09 12:16:49');
INSERT INTO `member_log_in_log` VALUES ('1656145060724527105', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-10 11:52:07', '2023-05-10 11:52:07');
INSERT INTO `member_log_in_log` VALUES ('1656213650114924545', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-10 16:24:40', '2023-05-10 16:24:40');
INSERT INTO `member_log_in_log` VALUES ('1657615308250218497', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-14 13:14:21', '2023-05-14 13:14:21');
INSERT INTO `member_log_in_log` VALUES ('1657981255939063810', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-15 13:28:30', '2023-05-15 13:28:30');
INSERT INTO `member_log_in_log` VALUES ('1658363935679578114', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-16 14:49:08', '2023-05-16 14:49:08');
INSERT INTO `member_log_in_log` VALUES ('1659056010414858241', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-18 12:39:11', '2023-05-18 12:39:11');
INSERT INTO `member_log_in_log` VALUES ('1659420678362873858', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-19 12:48:15', '2023-05-19 12:48:15');
INSERT INTO `member_log_in_log` VALUES ('1660132151858855938', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-21 11:55:23', '2023-05-21 11:55:23');
INSERT INTO `member_log_in_log` VALUES ('1660149018174455809', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-21 13:02:25', '2023-05-21 13:02:25');
INSERT INTO `member_log_in_log` VALUES ('1660902214090223618', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-23 14:55:21', '2023-05-23 14:55:21');
INSERT INTO `member_log_in_log` VALUES ('1661246538967453697', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-24 13:43:34', '2023-05-24 13:43:34');
INSERT INTO `member_log_in_log` VALUES ('1661579008992632833', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-25 11:44:41', '2023-05-25 11:44:41');
INSERT INTO `member_log_in_log` VALUES ('1661584444445712386', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-25 12:06:17', '2023-05-25 12:06:17');
INSERT INTO `member_log_in_log` VALUES ('1661957557377318914', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-26 12:48:54', '2023-05-26 12:48:54');
INSERT INTO `member_log_in_log` VALUES ('1661969682103545858', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-26 13:37:05', '2023-05-26 13:37:05');
INSERT INTO `member_log_in_log` VALUES ('1661975025902759938', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-26 13:58:19', '2023-05-26 13:58:19');
INSERT INTO `member_log_in_log` VALUES ('1661976224370601985', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-26 14:03:05', '2023-05-26 14:03:05');
INSERT INTO `member_log_in_log` VALUES ('1661984634017161217', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-26 14:36:30', '2023-05-26 14:36:30');
INSERT INTO `member_log_in_log` VALUES ('1661985998105534465', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-26 14:41:55', '2023-05-26 14:41:55');
INSERT INTO `member_log_in_log` VALUES ('1662681415973601281', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-28 12:45:15', '2023-05-28 12:45:15');
INSERT INTO `member_log_in_log` VALUES ('1662694075658219521', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-28 13:35:34', '2023-05-28 13:35:34');
INSERT INTO `member_log_in_log` VALUES ('1662712489932025857', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-28 14:48:44', '2023-05-28 14:48:44');
INSERT INTO `member_log_in_log` VALUES ('1662723349446455297', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-28 15:31:53', '2023-05-28 15:31:53');
INSERT INTO `member_log_in_log` VALUES ('1663412901282680833', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-30 13:11:55', '2023-05-30 13:11:55');
INSERT INTO `member_log_in_log` VALUES ('1663784171576807425', '1642067605873348610', '192.168.30.129', 1, 0, '2023-05-31 13:47:13', '2023-05-31 13:47:13');
INSERT INTO `member_log_in_log` VALUES ('1664146522364706817', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-01 13:47:04', '2023-06-01 13:47:04');
INSERT INTO `member_log_in_log` VALUES ('1664504444697460738', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-02 13:29:19', '2023-06-02 13:29:19');
INSERT INTO `member_log_in_log` VALUES ('1664863726093783041', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-03 13:16:59', '2023-06-03 13:16:59');
INSERT INTO `member_log_in_log` VALUES ('1665228483187560449', '1642067605873348610', '192.168.30.129', 0, 0, '2023-06-04 13:26:23', '2023-06-04 13:26:23');
INSERT INTO `member_log_in_log` VALUES ('1665228513776619521', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-04 13:26:31', '2023-06-04 13:26:31');
INSERT INTO `member_log_in_log` VALUES ('1666668480851611649', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-08 12:48:26', '2023-06-08 12:48:26');
INSERT INTO `member_log_in_log` VALUES ('1666721234072637441', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-08 16:18:03', '2023-06-08 16:18:03');
INSERT INTO `member_log_in_log` VALUES ('1667027970054787074', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-09 12:36:55', '2023-06-09 12:36:55');
INSERT INTO `member_log_in_log` VALUES ('1667081736984580097', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-09 16:10:34', '2023-06-09 16:10:34');
INSERT INTO `member_log_in_log` VALUES ('1667390019846823937', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-10 12:35:34', '2023-06-10 12:35:34');
INSERT INTO `member_log_in_log` VALUES ('1667428479651553281', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-10 15:08:24', '2023-06-10 15:08:24');
INSERT INTO `member_log_in_log` VALUES ('1667429093718630401', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-10 15:10:50', '2023-06-10 15:10:50');
INSERT INTO `member_log_in_log` VALUES ('1667768738193149953', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-11 13:40:27', '2023-06-11 13:40:27');
INSERT INTO `member_log_in_log` VALUES ('1668135543629750273', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-12 13:58:01', '2023-06-12 13:58:01');
INSERT INTO `member_log_in_log` VALUES ('1668136274306867202', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-12 14:00:55', '2023-06-12 14:00:55');
INSERT INTO `member_log_in_log` VALUES ('1668136604914491393', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-12 14:02:14', '2023-06-12 14:02:14');
INSERT INTO `member_log_in_log` VALUES ('1668854954384703490', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-14 13:36:42', '2023-06-14 13:36:42');
INSERT INTO `member_log_in_log` VALUES ('1669560709878493186', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-16 12:21:07', '2023-06-16 12:21:07');
INSERT INTO `member_log_in_log` VALUES ('1670281465742495746', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-18 12:05:08', '2023-06-18 12:05:08');
INSERT INTO `member_log_in_log` VALUES ('1670676348487643137', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-19 14:14:16', '2023-06-19 14:14:16');
INSERT INTO `member_log_in_log` VALUES ('1671432878522687489', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-21 16:20:27', '2023-06-21 16:20:27');
INSERT INTO `member_log_in_log` VALUES ('1671435863327825921', '1642067605873348610', '192.168.30.1', 1, 0, '2023-06-21 16:32:18', '2023-06-21 16:32:18');
INSERT INTO `member_log_in_log` VALUES ('1671437591280410625', '1642067605873348610', '192.168.30.1', 0, 0, '2023-06-21 16:39:10', '2023-06-21 16:39:10');
INSERT INTO `member_log_in_log` VALUES ('1671437644476768257', '1642067605873348610', '192.168.30.1', 1, 0, '2023-06-21 16:39:23', '2023-06-21 16:39:23');
INSERT INTO `member_log_in_log` VALUES ('1671736787611013121', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-22 12:28:04', '2023-06-22 12:28:04');
INSERT INTO `member_log_in_log` VALUES ('1672102973582344193', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-23 12:43:10', '2023-06-23 12:43:10');
INSERT INTO `member_log_in_log` VALUES ('1672840471317131265', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-25 13:33:43', '2023-06-25 13:33:43');
INSERT INTO `member_log_in_log` VALUES ('1672843152509186049', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-25 13:44:22', '2023-06-25 13:44:22');
INSERT INTO `member_log_in_log` VALUES ('1672843826751942658', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-25 13:47:03', '2023-06-25 13:47:03');
INSERT INTO `member_log_in_log` VALUES ('1673229425103097858', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-26 15:19:17', '2023-06-26 15:19:17');
INSERT INTO `member_log_in_log` VALUES ('1673229568862867458', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-26 15:19:51', '2023-06-26 15:19:51');
INSERT INTO `member_log_in_log` VALUES ('1673930416853143553', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 13:44:46', '2023-06-28 13:44:46');
INSERT INTO `member_log_in_log` VALUES ('1673945148314091521', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 14:43:18', '2023-06-28 14:43:18');
INSERT INTO `member_log_in_log` VALUES ('1673955721730850818', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 15:25:19', '2023-06-28 15:25:19');
INSERT INTO `member_log_in_log` VALUES ('1673956341024030722', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 15:27:47', '2023-06-28 15:27:47');
INSERT INTO `member_log_in_log` VALUES ('1673957778361679873', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 15:33:30', '2023-06-28 15:33:30');
INSERT INTO `member_log_in_log` VALUES ('1673958613808316417', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 15:36:49', '2023-06-28 15:36:49');
INSERT INTO `member_log_in_log` VALUES ('1673959681699086337', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 15:41:03', '2023-06-28 15:41:03');
INSERT INTO `member_log_in_log` VALUES ('1673966442459955201', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 16:07:55', '2023-06-28 16:07:55');
INSERT INTO `member_log_in_log` VALUES ('1673967886894366722', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 16:13:40', '2023-06-28 16:13:40');
INSERT INTO `member_log_in_log` VALUES ('1673969205260582914', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 16:18:54', '2023-06-28 16:18:54');
INSERT INTO `member_log_in_log` VALUES ('1673972470203285506', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 16:31:52', '2023-06-28 16:31:52');
INSERT INTO `member_log_in_log` VALUES ('1673972562196955137', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 16:32:14', '2023-06-28 16:32:14');
INSERT INTO `member_log_in_log` VALUES ('1673972670120591361', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 16:32:40', '2023-06-28 16:32:40');
INSERT INTO `member_log_in_log` VALUES ('1673973590149562370', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 16:36:19', '2023-06-28 16:36:19');
INSERT INTO `member_log_in_log` VALUES ('1673973667349921793', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-28 16:36:38', '2023-06-28 16:36:38');
INSERT INTO `member_log_in_log` VALUES ('1674648692881141761', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 13:18:57', '2023-06-30 13:18:57');
INSERT INTO `member_log_in_log` VALUES ('1674655388739350530', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 13:45:33', '2023-06-30 13:45:33');
INSERT INTO `member_log_in_log` VALUES ('1674655398029733890', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 13:45:35', '2023-06-30 13:45:35');
INSERT INTO `member_log_in_log` VALUES ('1674655404736425986', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 13:45:37', '2023-06-30 13:45:37');
INSERT INTO `member_log_in_log` VALUES ('1674655650765910018', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 13:46:35', '2023-06-30 13:46:35');
INSERT INTO `member_log_in_log` VALUES ('1674655953049399298', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 13:47:47', '2023-06-30 13:47:47');
INSERT INTO `member_log_in_log` VALUES ('1674656102626668546', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 13:48:23', '2023-06-30 13:48:23');
INSERT INTO `member_log_in_log` VALUES ('1674656573126914049', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 13:50:15', '2023-06-30 13:50:15');
INSERT INTO `member_log_in_log` VALUES ('1674656851888746497', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 13:51:22', '2023-06-30 13:51:22');
INSERT INTO `member_log_in_log` VALUES ('1674656984399392769', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 13:51:53', '2023-06-30 13:51:53');
INSERT INTO `member_log_in_log` VALUES ('1674657092226560001', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 13:52:19', '2023-06-30 13:52:19');
INSERT INTO `member_log_in_log` VALUES ('1674659231741030401', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:00:49', '2023-06-30 14:00:49');
INSERT INTO `member_log_in_log` VALUES ('1674659791839997953', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:03:03', '2023-06-30 14:03:03');
INSERT INTO `member_log_in_log` VALUES ('1674660689626574850', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:06:37', '2023-06-30 14:06:37');
INSERT INTO `member_log_in_log` VALUES ('1674661671836102658', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:10:31', '2023-06-30 14:10:31');
INSERT INTO `member_log_in_log` VALUES ('1674663103599521794', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:16:12', '2023-06-30 14:16:12');
INSERT INTO `member_log_in_log` VALUES ('1674663361243033601', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:17:14', '2023-06-30 14:17:14');
INSERT INTO `member_log_in_log` VALUES ('1674663849837506561', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:19:10', '2023-06-30 14:19:10');
INSERT INTO `member_log_in_log` VALUES ('1674664028179312642', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:19:53', '2023-06-30 14:19:53');
INSERT INTO `member_log_in_log` VALUES ('1674664346623455234', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:21:09', '2023-06-30 14:21:09');
INSERT INTO `member_log_in_log` VALUES ('1674664493860302849', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:21:44', '2023-06-30 14:21:44');
INSERT INTO `member_log_in_log` VALUES ('1674664998443462657', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:23:44', '2023-06-30 14:23:44');
INSERT INTO `member_log_in_log` VALUES ('1674665072464539650', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:24:02', '2023-06-30 14:24:02');
INSERT INTO `member_log_in_log` VALUES ('1674665136436064257', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:24:17', '2023-06-30 14:24:17');
INSERT INTO `member_log_in_log` VALUES ('1674665913938391041', '1642067605873348610', '192.168.30.129', 1, 0, '2023-06-30 14:27:22', '2023-06-30 14:27:22');
INSERT INTO `member_log_in_log` VALUES ('1674995668801847297', '1642067605873348610', '192.168.30.129', 1, 0, '2023-07-01 12:17:42', '2023-07-01 12:17:42');
INSERT INTO `member_log_in_log` VALUES ('1675381171686600706', '1642067605873348610', '192.168.30.129', 1, 0, '2023-07-02 13:49:33', '2023-07-02 13:49:33');
INSERT INTO `member_log_in_log` VALUES ('1675417375165493249', '1642067605873348610', '192.168.30.129', 1, 0, '2023-07-02 16:13:25', '2023-07-02 16:13:25');
INSERT INTO `member_log_in_log` VALUES ('1676085099214528513', '1642067605873348610', '192.168.30.129', 1, 0, '2023-07-04 12:26:42', '2023-07-04 12:26:42');
INSERT INTO `member_log_in_log` VALUES ('1677177634745761793', '1642067605873348610', '192.168.30.129', 1, 0, '2023-07-07 12:48:03', '2023-07-07 12:48:03');
INSERT INTO `member_log_in_log` VALUES ('1677179725975404546', '1642067605873348610', '192.168.30.129', 1, 0, '2023-07-07 12:56:22', '2023-07-07 12:56:22');
INSERT INTO `member_log_in_log` VALUES ('1677541824014700545', '1642067605873348610', '192.168.30.129', 1, 0, '2023-07-08 12:55:13', '2023-07-08 12:55:13');
INSERT INTO `member_log_in_log` VALUES ('1677569823112024065', '1642067605873348610', '192.168.30.129', 1, 0, '2023-07-08 14:46:28', '2023-07-08 14:46:28');
INSERT INTO `member_log_in_log` VALUES ('1677907488642547713', '1642067605873348610', '192.168.30.129', 1, 0, '2023-07-09 13:08:14', '2023-07-09 13:08:14');
INSERT INTO `member_log_in_log` VALUES ('1680103100926431233', '1642067605873348610', '192.168.30.129', 1, 0, '2023-07-15 14:32:49', '2023-07-15 14:32:49');
INSERT INTO `member_log_in_log` VALUES ('1680106557762736130', '1642067605873348610', '192.168.30.1', 1, 0, '2023-07-15 14:46:33', '2023-07-15 14:46:33');
INSERT INTO `member_log_in_log` VALUES ('1680569099920371713', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-16 21:24:32', '2023-07-16 21:24:32');
INSERT INTO `member_log_in_log` VALUES ('1680570054237777921', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-16 21:28:19', '2023-07-16 21:28:19');
INSERT INTO `member_log_in_log` VALUES ('1680570222676832258', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-16 21:28:59', '2023-07-16 21:28:59');
INSERT INTO `member_log_in_log` VALUES ('1680874853839716354', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-17 17:39:29', '2023-07-17 17:39:29');
INSERT INTO `member_log_in_log` VALUES ('1683399452141813762', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-24 16:51:20', '2023-07-24 16:51:20');
INSERT INTO `member_log_in_log` VALUES ('1683399718467534850', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-24 16:52:24', '2023-07-24 16:52:24');
INSERT INTO `member_log_in_log` VALUES ('1683401015061118978', '1680864379295285250', '192.168.30.129', 0, 0, '2023-07-24 16:57:33', '2023-07-24 16:57:33');
INSERT INTO `member_log_in_log` VALUES ('1683405470607343618', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-24 17:15:15', '2023-07-24 17:15:15');
INSERT INTO `member_log_in_log` VALUES ('1683405636349460481', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-24 17:15:55', '2023-07-24 17:15:55');
INSERT INTO `member_log_in_log` VALUES ('1683411575366483970', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-24 17:39:31', '2023-07-24 17:39:31');
INSERT INTO `member_log_in_log` VALUES ('1683629261178548225', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-25 08:04:31', '2023-07-25 08:04:31');
INSERT INTO `member_log_in_log` VALUES ('1683725285456920577', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-25 14:26:05', '2023-07-25 14:26:05');
INSERT INTO `member_log_in_log` VALUES ('1683745646667923458', '1642067605873348610', '0:0:0:0:0:0:0:1', 0, 0, '2023-07-25 15:46:59', '2023-07-25 15:46:59');
INSERT INTO `member_log_in_log` VALUES ('1683745752368578561', '1642067605873348610', '0:0:0:0:0:0:0:1', 0, 0, '2023-07-25 15:47:25', '2023-07-25 15:47:25');
INSERT INTO `member_log_in_log` VALUES ('1683746183102668802', '1642067605873348610', '0:0:0:0:0:0:0:1', 0, 0, '2023-07-25 15:49:07', '2023-07-25 15:49:07');
INSERT INTO `member_log_in_log` VALUES ('1683746401244225538', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-25 15:49:59', '2023-07-25 15:49:59');
INSERT INTO `member_log_in_log` VALUES ('1683747276360638465', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-25 15:53:28', '2023-07-25 15:53:28');
INSERT INTO `member_log_in_log` VALUES ('1683747393922785281', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-25 15:53:56', '2023-07-25 15:53:56');
INSERT INTO `member_log_in_log` VALUES ('1683747657618677762', '1642067605873348610', '0:0:0:0:0:0:0:1', 0, 0, '2023-07-25 15:54:59', '2023-07-25 15:54:59');
INSERT INTO `member_log_in_log` VALUES ('1683747717932769281', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-25 15:55:13', '2023-07-25 15:55:13');
INSERT INTO `member_log_in_log` VALUES ('1683750045117079554', '1642067605873348610', '192.168.30.129', 1, 0, '2023-07-25 16:04:28', '2023-07-25 16:04:28');
INSERT INTO `member_log_in_log` VALUES ('1683750644118216706', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-25 16:06:51', '2023-07-25 16:06:51');
INSERT INTO `member_log_in_log` VALUES ('1683767563210784769', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-25 17:14:05', '2023-07-25 17:14:05');
INSERT INTO `member_log_in_log` VALUES ('1683768048697278465', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-25 17:16:00', '2023-07-25 17:16:00');
INSERT INTO `member_log_in_log` VALUES ('1683769007179628545', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-25 17:19:49', '2023-07-25 17:19:49');
INSERT INTO `member_log_in_log` VALUES ('1683770612012613633', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-25 17:26:12', '2023-07-25 17:26:12');
INSERT INTO `member_log_in_log` VALUES ('1683773687335026689', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-25 17:38:25', '2023-07-25 17:38:25');
INSERT INTO `member_log_in_log` VALUES ('1684000565064986626', '1680864379295285250', '192.168.30.129', 1, 0, '2023-07-26 08:39:57', '2023-07-26 08:39:57');
INSERT INTO `member_log_in_log` VALUES ('1684006889022590977', '1684005561911283714', '192.168.30.129', 1, 0, '2023-07-26 09:05:04', '2023-07-26 09:05:04');
INSERT INTO `member_log_in_log` VALUES ('1684007070833086466', '1684005561911283714', '192.168.30.129', 1, 0, '2023-07-26 09:05:48', '2023-07-26 09:05:48');
INSERT INTO `member_log_in_log` VALUES ('1684007564674633730', '1684005561911283714', '192.168.30.129', 1, 0, '2023-07-26 09:07:45', '2023-07-26 09:07:45');
INSERT INTO `member_log_in_log` VALUES ('1684007601706143745', '1684006775516336129', '192.168.30.129', 1, 0, '2023-07-26 09:07:54', '2023-07-26 09:07:54');
INSERT INTO `member_log_in_log` VALUES ('1684007694714834945', '1684006775516336129', '192.168.30.129', 1, 0, '2023-07-26 09:08:16', '2023-07-26 09:08:16');
INSERT INTO `member_log_in_log` VALUES ('1684008231803850753', '1684005561911283714', '192.168.30.129', 1, 0, '2023-07-26 09:10:24', '2023-07-26 09:10:24');
INSERT INTO `member_log_in_log` VALUES ('1684008301441880065', '1684005561911283714', '192.168.30.129', 1, 0, '2023-07-26 09:10:41', '2023-07-26 09:10:41');
INSERT INTO `member_log_in_log` VALUES ('1684012582278594562', '1684005561911283714', '192.168.30.129', 1, 0, '2023-07-26 09:27:42', '2023-07-26 09:27:42');
INSERT INTO `member_log_in_log` VALUES ('1684020364889395201', '1684005561911283714', '192.168.30.129', 0, 0, '2023-07-26 09:58:37', '2023-07-26 09:58:37');
INSERT INTO `member_log_in_log` VALUES ('1684020443402571778', '1684006775516336129', '192.168.30.129', 0, 0, '2023-07-26 09:58:56', '2023-07-26 09:58:56');
INSERT INTO `member_log_in_log` VALUES ('1684020754531848194', '1684020746734637057', '192.168.30.129', 1, 0, '2023-07-26 10:00:10', '2023-07-26 10:00:10');
INSERT INTO `member_log_in_log` VALUES ('1684020976678965250', '1684020746734637057', '192.168.30.129', 1, 0, '2023-07-26 10:01:03', '2023-07-26 10:01:03');
INSERT INTO `member_log_in_log` VALUES ('1684089451883601921', '1684020746734637057', '192.168.30.129', 1, 0, '2023-07-26 14:33:09', '2023-07-26 14:33:09');
INSERT INTO `member_log_in_log` VALUES ('1684126996147257346', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-26 17:02:20', '2023-07-26 17:02:20');
INSERT INTO `member_log_in_log` VALUES ('1684127444652572674', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-26 17:04:07', '2023-07-26 17:04:07');
INSERT INTO `member_log_in_log` VALUES ('1684364912639377409', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 08:47:44', '2023-07-27 08:47:44');
INSERT INTO `member_log_in_log` VALUES ('1684379163546402818', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 09:44:21', '2023-07-27 09:44:21');
INSERT INTO `member_log_in_log` VALUES ('1684380118534897666', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 09:48:09', '2023-07-27 09:48:09');
INSERT INTO `member_log_in_log` VALUES ('1684380263674593282', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 09:48:44', '2023-07-27 09:48:44');
INSERT INTO `member_log_in_log` VALUES ('1684380357916409858', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 09:49:06', '2023-07-27 09:49:06');
INSERT INTO `member_log_in_log` VALUES ('1684380735441518593', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 09:50:36', '2023-07-27 09:50:36');
INSERT INTO `member_log_in_log` VALUES ('1684380898901934082', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 09:51:15', '2023-07-27 09:51:15');
INSERT INTO `member_log_in_log` VALUES ('1684384302768373762', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 10:04:47', '2023-07-27 10:04:47');
INSERT INTO `member_log_in_log` VALUES ('1684389234779115522', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 10:24:23', '2023-07-27 10:24:23');
INSERT INTO `member_log_in_log` VALUES ('1684393061901160449', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-27 10:39:35', '2023-07-27 10:39:35');
INSERT INTO `member_log_in_log` VALUES ('1684398269934882818', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-27 11:00:17', '2023-07-27 11:00:17');
INSERT INTO `member_log_in_log` VALUES ('1684398298623922178', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 11:00:24', '2023-07-27 11:00:24');
INSERT INTO `member_log_in_log` VALUES ('1684464876442882049', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 15:24:57', '2023-07-27 15:24:57');
INSERT INTO `member_log_in_log` VALUES ('1684467125252526081', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 15:33:53', '2023-07-27 15:33:53');
INSERT INTO `member_log_in_log` VALUES ('1684478146381877250', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 16:17:42', '2023-07-27 16:17:42');
INSERT INTO `member_log_in_log` VALUES ('1684499033051107329', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-27 17:40:41', '2023-07-27 17:40:41');
INSERT INTO `member_log_in_log` VALUES ('1684720472182321153', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-28 08:20:36', '2023-07-28 08:20:36');
INSERT INTO `member_log_in_log` VALUES ('1684761024525242369', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-28 11:01:44', '2023-07-28 11:01:44');
INSERT INTO `member_log_in_log` VALUES ('1684811828254801922', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-28 14:23:37', '2023-07-28 14:23:37');
INSERT INTO `member_log_in_log` VALUES ('1684816663842717697', '1684126987272110082', '192.168.30.129', 1, 0, '2023-07-28 14:42:50', '2023-07-28 14:42:50');
INSERT INTO `member_log_in_log` VALUES ('1684830846122524673', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-28 15:39:11', '2023-07-28 15:39:11');
INSERT INTO `member_log_in_log` VALUES ('1684843001009995777', '1684842982433423362', '192.168.30.129', 1, 0, '2023-07-28 16:27:29', '2023-07-28 16:27:29');
INSERT INTO `member_log_in_log` VALUES ('1684843464434450434', '1684842982433423362', '192.168.30.129', 1, 0, '2023-07-28 16:29:19', '2023-07-28 16:29:19');
INSERT INTO `member_log_in_log` VALUES ('1684853773643612161', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-28 17:10:17', '2023-07-28 17:10:17');
INSERT INTO `member_log_in_log` VALUES ('1684854166431793154', '1684853753762611201', '192.168.30.129', 0, 0, '2023-07-28 17:11:51', '2023-07-28 17:11:51');
INSERT INTO `member_log_in_log` VALUES ('1684854188028264450', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-28 17:11:56', '2023-07-28 17:11:56');
INSERT INTO `member_log_in_log` VALUES ('1684854532363845633', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-28 17:13:18', '2023-07-28 17:13:18');
INSERT INTO `member_log_in_log` VALUES ('1684857001911320578', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-28 17:23:07', '2023-07-28 17:23:07');
INSERT INTO `member_log_in_log` VALUES ('1685080393251872769', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-29 08:10:48', '2023-07-29 08:10:48');
INSERT INTO `member_log_in_log` VALUES ('1685095148586852354', '1684853753762611201', '192.168.30.129', 0, 0, '2023-07-29 09:09:26', '2023-07-29 09:09:26');
INSERT INTO `member_log_in_log` VALUES ('1685095160737751042', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-29 09:09:29', '2023-07-29 09:09:29');
INSERT INTO `member_log_in_log` VALUES ('1685182561627779073', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-29 14:56:47', '2023-07-29 14:56:47');
INSERT INTO `member_log_in_log` VALUES ('1685807990730407937', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-31 08:22:00', '2023-07-31 08:22:00');
INSERT INTO `member_log_in_log` VALUES ('1685833297088864257', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-31 10:02:34', '2023-07-31 10:02:34');
INSERT INTO `member_log_in_log` VALUES ('1685838890474266626', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-31 10:24:48', '2023-07-31 10:24:48');
INSERT INTO `member_log_in_log` VALUES ('1685840686441586689', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-31 10:31:56', '2023-07-31 10:31:56');
INSERT INTO `member_log_in_log` VALUES ('1685840758780747778', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-31 10:32:13', '2023-07-31 10:32:13');
INSERT INTO `member_log_in_log` VALUES ('1685841381790064642', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-31 10:34:41', '2023-07-31 10:34:41');
INSERT INTO `member_log_in_log` VALUES ('1685842104250548225', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-07-31 10:37:34', '2023-07-31 10:37:34');
INSERT INTO `member_log_in_log` VALUES ('1685850640946839553', '1685850632348516354', '192.168.30.129', 1, 0, '2023-07-31 11:11:29', '2023-07-31 11:11:29');
INSERT INTO `member_log_in_log` VALUES ('1685908363138834433', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-31 15:00:51', '2023-07-31 15:00:51');
INSERT INTO `member_log_in_log` VALUES ('1685910835211280386', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-31 15:10:40', '2023-07-31 15:10:40');
INSERT INTO `member_log_in_log` VALUES ('1685911714375147521', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-31 15:14:10', '2023-07-31 15:14:10');
INSERT INTO `member_log_in_log` VALUES ('1685917140890165249', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-31 15:35:44', '2023-07-31 15:35:44');
INSERT INTO `member_log_in_log` VALUES ('1685917199115493378', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-31 15:35:58', '2023-07-31 15:35:58');
INSERT INTO `member_log_in_log` VALUES ('1685917359744753665', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-31 15:36:36', '2023-07-31 15:36:36');
INSERT INTO `member_log_in_log` VALUES ('1685943350961643522', '1684853753762611201', '192.168.30.129', 1, 0, '2023-07-31 17:19:53', '2023-07-31 17:19:53');
INSERT INTO `member_log_in_log` VALUES ('1686262003351478273', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-01 14:26:05', '2023-08-01 14:26:05');
INSERT INTO `member_log_in_log` VALUES ('1686283190920077314', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-01 15:50:17', '2023-08-01 15:50:17');
INSERT INTO `member_log_in_log` VALUES ('1686302473662291969', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-01 17:06:54', '2023-08-01 17:06:54');
INSERT INTO `member_log_in_log` VALUES ('1688361823222394882', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-07 09:30:02', '2023-08-07 09:30:02');
INSERT INTO `member_log_in_log` VALUES ('1688441386312974338', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-07 14:46:11', '2023-08-07 14:46:11');
INSERT INTO `member_log_in_log` VALUES ('1688466641991565313', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:26:32', '2023-08-07 16:26:32');
INSERT INTO `member_log_in_log` VALUES ('1688467103289507841', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:28:22', '2023-08-07 16:28:22');
INSERT INTO `member_log_in_log` VALUES ('1688467476460929025', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:29:51', '2023-08-07 16:29:51');
INSERT INTO `member_log_in_log` VALUES ('1688467687665106946', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:30:42', '2023-08-07 16:30:42');
INSERT INTO `member_log_in_log` VALUES ('1688467824353280002', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:31:14', '2023-08-07 16:31:14');
INSERT INTO `member_log_in_log` VALUES ('1688467920167960578', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:31:37', '2023-08-07 16:31:37');
INSERT INTO `member_log_in_log` VALUES ('1688468573804101633', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:34:13', '2023-08-07 16:34:13');
INSERT INTO `member_log_in_log` VALUES ('1688468758122790914', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:34:57', '2023-08-07 16:34:57');
INSERT INTO `member_log_in_log` VALUES ('1688468789311635458', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:35:04', '2023-08-07 16:35:04');
INSERT INTO `member_log_in_log` VALUES ('1688468836988289025', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:35:16', '2023-08-07 16:35:16');
INSERT INTO `member_log_in_log` VALUES ('1688468924355641345', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:35:36', '2023-08-07 16:35:36');
INSERT INTO `member_log_in_log` VALUES ('1688468981876326401', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:35:50', '2023-08-07 16:35:50');
INSERT INTO `member_log_in_log` VALUES ('1688469051308834817', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-07 16:36:07', '2023-08-07 16:36:07');
INSERT INTO `member_log_in_log` VALUES ('1688706990374760449', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-08 08:21:36', '2023-08-08 08:21:36');
INSERT INTO `member_log_in_log` VALUES ('1688714847975485441', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-08 08:52:49', '2023-08-08 08:52:49');
INSERT INTO `member_log_in_log` VALUES ('1688720786531135490', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-08 09:16:25', '2023-08-08 09:16:25');
INSERT INTO `member_log_in_log` VALUES ('1688721117021319170', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-08 09:17:44', '2023-08-08 09:17:44');
INSERT INTO `member_log_in_log` VALUES ('1688723662632497154', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-08 09:27:51', '2023-08-08 09:27:51');
INSERT INTO `member_log_in_log` VALUES ('1688723744907964418', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-08 09:28:10', '2023-08-08 09:28:10');
INSERT INTO `member_log_in_log` VALUES ('1688724012240318466', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-08 09:29:14', '2023-08-08 09:29:14');
INSERT INTO `member_log_in_log` VALUES ('1688727575951613954', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-08 09:43:24', '2023-08-08 09:43:24');
INSERT INTO `member_log_in_log` VALUES ('1688737240492027906', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-08 10:21:48', '2023-08-08 10:21:48');
INSERT INTO `member_log_in_log` VALUES ('1688737463478026242', '1642067605873348610', '0:0:0:0:0:0:0:1', 1, 0, '2023-08-08 10:22:41', '2023-08-08 10:22:41');
INSERT INTO `member_log_in_log` VALUES ('1688802788382957570', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-08 14:42:16', '2023-08-08 14:42:16');
INSERT INTO `member_log_in_log` VALUES ('1688820332414197761', '1685850632348516354', '192.168.30.129', 1, 0, '2023-08-08 15:51:59', '2023-08-08 15:51:59');
INSERT INTO `member_log_in_log` VALUES ('1688823314920857601', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-08 16:03:50', '2023-08-08 16:03:50');
INSERT INTO `member_log_in_log` VALUES ('1688831808243851266', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-08 16:37:35', '2023-08-08 16:37:35');
INSERT INTO `member_log_in_log` VALUES ('1688833642450739201', '1685850632348516354', '192.168.30.129', 1, 0, '2023-08-08 16:44:52', '2023-08-08 16:44:52');
INSERT INTO `member_log_in_log` VALUES ('1688835644610142209', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-08 16:52:49', '2023-08-08 16:52:49');
INSERT INTO `member_log_in_log` VALUES ('1688836507563024386', '1685850632348516354', '192.168.30.129', 1, 0, '2023-08-08 16:56:15', '2023-08-08 16:56:15');
INSERT INTO `member_log_in_log` VALUES ('1688842122150633473', '1685850632348516354', '192.168.30.129', 1, 0, '2023-08-08 17:18:34', '2023-08-08 17:18:34');
INSERT INTO `member_log_in_log` VALUES ('1689078332315979777', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-09 08:57:11', '2023-08-09 08:57:11');
INSERT INTO `member_log_in_log` VALUES ('1689100694650408962', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-09 10:26:02', '2023-08-09 10:26:02');
INSERT INTO `member_log_in_log` VALUES ('1689531280200359938', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-10 14:57:02', '2023-08-10 14:57:02');
INSERT INTO `member_log_in_log` VALUES ('1689535960464809985', '1685850632348516354', '192.168.30.129', 1, 0, '2023-08-10 15:15:38', '2023-08-10 15:15:38');
INSERT INTO `member_log_in_log` VALUES ('1689536511743156225', '1684853753762611201', '192.168.30.129', 1, 0, '2023-08-10 15:17:49', '2023-08-10 15:17:49');

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
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
                              `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
                              `role_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
                              `type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '角色类型种类，0->普通角色类型，1->管理员角色类型，2->会员等级角色类型',
                              `create_time` datetime NOT NULL COMMENT '创建时间',
                              `update_time` datetime NOT NULL COMMENT '更新时间',
                              PRIMARY KEY (`role_id`) USING BTREE,
                              UNIQUE INDEX `idx_role_type`(`role_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 'user', 0, '2023-03-27 16:03:21', '2023-03-27 16:03:24');
INSERT INTO `user_role` VALUES (2, '*', 1, '2023-04-01 21:17:57', '2023-04-01 21:18:00');
INSERT INTO `user_role` VALUES (3, 'admin', 1, '2023-04-01 21:21:11', '2023-04-01 21:21:13');
INSERT INTO `user_role` VALUES (4, 'root', 1, '2023-04-02 13:36:49', '2023-04-02 13:36:52');
INSERT INTO `user_role` VALUES (6, 'neko_convenient_service', 0, '2023-04-07 15:12:43', '2023-04-07 15:12:43');
INSERT INTO `user_role` VALUES (7, 'market', 0, '2023-04-16 15:33:29', '2023-04-16 15:33:29');
INSERT INTO `user_role` VALUES (8, 'courier', 0, '2023-05-25 12:05:00', '2023-05-25 12:05:00');
INSERT INTO `user_role` VALUES (11, '普通会员', 2, '2023-07-26 10:00:29', '2023-07-26 10:00:31');
INSERT INTO `user_role` VALUES (15, '1级会员', 2, '2023-07-26 16:09:56', '2023-07-26 16:09:56');
INSERT INTO `user_role` VALUES (16, '2级会员', 2, '2023-07-26 16:10:09', '2023-07-26 16:10:09');

-- ----------------------------
-- Table structure for user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_role_relation`;
CREATE TABLE `user_role_relation`  (
                                       `relation_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关系id',
                                       `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
                                       `role_id` int(255) NOT NULL DEFAULT 1 COMMENT '角色id，对应user_role表role_id',
                                       `update_version` int(255) NOT NULL DEFAULT 0 COMMENT '乐观锁',
                                       `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                       `create_time` datetime NOT NULL COMMENT '创建时间',
                                       `update_time` datetime NOT NULL COMMENT '更新时间',
                                       PRIMARY KEY (`relation_id`) USING BTREE,
                                       INDEX `idx_uid`(`uid`) USING BTREE,
                                       INDEX `idx_for_user_role_relation_role_id`(`role_id`) USING BTREE,
                                       CONSTRAINT `idx_for_user_role_relation_role_id` FOREIGN KEY (`role_id`) REFERENCES `user_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户，角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role_relation
-- ----------------------------
INSERT INTO `user_role_relation` VALUES ('1642067605898514434', '1642067605873348610', 1, 0, 0, '2023-04-01 15:33:20', '2023-04-01 15:33:20');
INSERT INTO `user_role_relation` VALUES ('1642398369596944385', '1642398369596944385', 2, 0, 0, '2023-04-02 13:31:44', '2023-04-02 13:31:47');
INSERT INTO `user_role_relation` VALUES ('1645332216386969602', '1645332216386969601', 1, 0, 0, '2023-04-10 15:45:44', '2023-04-10 15:45:44');
INSERT INTO `user_role_relation` VALUES ('1647869825072066562', '1642067605873348610', 7, 0, 0, '2023-04-17 15:49:17', '2023-04-17 15:49:17');
INSERT INTO `user_role_relation` VALUES ('1648211754670399490', '1642067605873348610', 7, 0, 0, '2023-04-18 14:27:59', '2023-04-18 14:27:59');
INSERT INTO `user_role_relation` VALUES ('1661985779347415042', '1642067605873348610', 8, 0, 0, '2023-05-26 14:41:03', '2023-05-26 14:41:03');
INSERT INTO `user_role_relation` VALUES ('1684125989174550530', '1642067605873348610', 11, 0, 0, '2023-07-26 16:59:01', '2023-07-26 16:59:01');
INSERT INTO `user_role_relation` VALUES ('1684126422936887298', '1645332216386969601', 11, 0, 0, '2023-07-26 17:00:44', '2023-07-26 17:00:44');
INSERT INTO `user_role_relation` VALUES ('1684853753829720065', '1684853753762611201', 1, 0, 0, '2023-07-28 17:10:13', '2023-07-28 17:10:13');
INSERT INTO `user_role_relation` VALUES ('1684853753829720066', '1684853753762611201', 11, 0, 0, '2023-07-28 17:10:13', '2023-07-28 17:10:13');
INSERT INTO `user_role_relation` VALUES ('1685850632411430914', '1685850632348516354', 1, 0, 0, '2023-07-31 11:11:27', '2023-07-31 11:11:27');
INSERT INTO `user_role_relation` VALUES ('1685850632411430915', '1685850632348516354', 11, 0, 0, '2023-07-31 11:11:27', '2023-07-31 11:11:27');
INSERT INTO `user_role_relation` VALUES ('1688832208925712387', '1684853753762611201', 15, 0, 0, '2023-08-08 16:39:10', '2023-08-08 16:39:10');
INSERT INTO `user_role_relation` VALUES ('1688843555113623555', '1685850632348516354', 15, 0, 0, '2023-08-08 17:24:15', '2023-08-08 17:24:15');

-- ----------------------------
-- Table structure for user_weight
-- ----------------------------
DROP TABLE IF EXISTS `user_weight`;
CREATE TABLE `user_weight`  (
                                `weight_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '权限id',
                                `weight_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名',
                                `type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '权限类型种类，0->普通权限，1->会员等级权限',
                                `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                `create_time` datetime NOT NULL COMMENT '创建时间',
                                `update_time` datetime NOT NULL COMMENT '更新时间',
                                PRIMARY KEY (`weight_id`) USING BTREE,
                                UNIQUE INDEX `idx_weight_type`(`weight_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_weight
-- ----------------------------
INSERT INTO `user_weight` VALUES (1, 'base', 0, 0, '2023-03-27 16:03:51', '2023-03-27 16:03:54');
INSERT INTO `user_weight` VALUES (2, '*', 0, 0, '2023-04-01 21:18:11', '2023-04-01 21:18:14');
INSERT INTO `user_weight` VALUES (3, 'high_read', 0, 0, '2023-04-01 21:24:27', '2023-04-01 21:24:29');
INSERT INTO `user_weight` VALUES (4, 'high_write', 0, 0, '2023-04-01 21:24:43', '2023-04-01 21:24:46');
INSERT INTO `user_weight` VALUES (5, 'root_read', 0, 0, '2023-04-02 13:37:23', '2023-04-02 13:37:26');
INSERT INTO `user_weight` VALUES (6, 'root_write', 0, 0, '2023-04-02 13:37:34', '2023-04-02 13:37:36');
INSERT INTO `user_weight` VALUES (8, 'feign_use', 0, 0, '2023-04-07 15:11:52', '2023-04-07 15:11:52');
INSERT INTO `user_weight` VALUES (9, 'market_read', 0, 0, '2023-04-16 15:29:31', '2023-04-16 15:29:31');
INSERT INTO `user_weight` VALUES (10, 'market_write', 0, 0, '2023-04-16 15:29:50', '2023-04-16 15:29:50');
INSERT INTO `user_weight` VALUES (11, 'courier_read', 0, 0, '2023-05-25 12:04:27', '2023-05-25 12:04:27');
INSERT INTO `user_weight` VALUES (12, 'courier_write', 0, 0, '2023-05-25 12:04:40', '2023-05-25 12:04:40');
INSERT INTO `user_weight` VALUES (16, '基本视频观看', 1, 0, '2023-07-26 16:00:49', '2023-07-26 16:00:49');
INSERT INTO `user_weight` VALUES (17, '1级视频观看', 1, 0, '2023-07-26 16:08:59', '2023-07-26 16:08:59');
INSERT INTO `user_weight` VALUES (18, '2级视频观看', 1, 0, '2023-07-26 16:09:12', '2023-07-26 16:09:12');

-- ----------------------------
-- Table structure for weight_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `weight_role_relation`;
CREATE TABLE `weight_role_relation`  (
                                         `relation_id` int(255) NOT NULL AUTO_INCREMENT COMMENT '关系id',
                                         `weight_id` int(255) NOT NULL COMMENT '权限id，对应user_weight表weight_id',
                                         `role_id` int(255) NOT NULL COMMENT '角色id，对应user_role表role_id',
                                         `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                         `create_time` datetime NOT NULL COMMENT '创建时间',
                                         `update_time` datetime NOT NULL COMMENT '更新时间',
                                         PRIMARY KEY (`relation_id`) USING BTREE,
                                         INDEX `idx_role_id`(`role_id`) USING BTREE,
                                         INDEX `idx_for_weight_role_relation_weight_id`(`weight_id`) USING BTREE,
                                         CONSTRAINT `idx_for_weight_role_relation_role_id` FOREIGN KEY (`role_id`) REFERENCES `user_role` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
                                         CONSTRAINT `idx_for_weight_role_relation_weight_id` FOREIGN KEY (`weight_id`) REFERENCES `user_weight` (`weight_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限，角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of weight_role_relation
-- ----------------------------
INSERT INTO `weight_role_relation` VALUES (1, 1, 1, 0, '2023-03-27 16:04:06', '2023-03-27 16:04:08');
INSERT INTO `weight_role_relation` VALUES (2, 2, 2, 0, '2023-04-01 21:18:31', '2023-04-01 21:18:33');
INSERT INTO `weight_role_relation` VALUES (3, 3, 3, 0, '2023-04-01 21:25:22', '2023-04-01 21:25:25');
INSERT INTO `weight_role_relation` VALUES (4, 4, 3, 0, '2023-04-01 21:25:34', '2023-04-01 21:25:37');
INSERT INTO `weight_role_relation` VALUES (5, 5, 4, 0, '2023-04-02 13:37:58', '2023-04-02 13:38:00');
INSERT INTO `weight_role_relation` VALUES (6, 6, 4, 0, '2023-04-02 13:38:10', '2023-04-02 13:38:12');
INSERT INTO `weight_role_relation` VALUES (13, 8, 6, 0, '2023-04-07 15:12:53', '2023-04-07 15:12:53');
INSERT INTO `weight_role_relation` VALUES (14, 1, 7, 0, '2023-04-16 15:33:43', '2023-04-16 15:33:43');
INSERT INTO `weight_role_relation` VALUES (15, 9, 7, 0, '2023-04-16 15:33:43', '2023-04-16 15:33:43');
INSERT INTO `weight_role_relation` VALUES (16, 10, 7, 0, '2023-04-16 15:33:43', '2023-04-16 15:33:43');
INSERT INTO `weight_role_relation` VALUES (17, 1, 8, 0, '2023-05-25 12:05:24', '2023-05-25 12:05:24');
INSERT INTO `weight_role_relation` VALUES (18, 11, 8, 0, '2023-05-25 12:05:24', '2023-05-25 12:05:24');
INSERT INTO `weight_role_relation` VALUES (19, 12, 8, 0, '2023-05-25 12:05:24', '2023-05-25 12:05:24');
INSERT INTO `weight_role_relation` VALUES (29, 16, 11, 0, '2023-07-26 16:08:38', '2023-07-26 16:08:38');
INSERT INTO `weight_role_relation` VALUES (30, 16, 15, 0, '2023-07-26 16:10:14', '2023-07-26 16:10:14');
INSERT INTO `weight_role_relation` VALUES (31, 17, 15, 0, '2023-07-26 16:10:14', '2023-07-26 16:10:14');
INSERT INTO `weight_role_relation` VALUES (32, 16, 16, 0, '2023-07-26 16:10:19', '2023-07-26 16:10:19');
INSERT INTO `weight_role_relation` VALUES (33, 17, 16, 0, '2023-07-26 16:10:19', '2023-07-26 16:10:19');
INSERT INTO `weight_role_relation` VALUES (34, 18, 16, 0, '2023-07-26 16:10:19', '2023-07-26 16:10:19');

SET FOREIGN_KEY_CHECKS = 1;
