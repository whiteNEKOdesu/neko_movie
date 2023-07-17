/*
 Navicat Premium Data Transfer

 Source Server         : vm_group_neko_convenient_3308
 Source Server Type    : MySQL
 Source Server Version : 50738 (5.7.38)
 Source Host           : 192.168.30.131:3308
 Source Schema         : nacos_config

 Target Server Type    : MySQL
 Target Server Version : 50738 (5.7.38)
 File Encoding         : 65001

 Date: 17/07/2023 10:25:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (5, 'neko-movie-third-party-dev.yaml', 'NEKO-MOVIE-THIRD-PARTY', 'spring:\n  mail:\n    host: smtp.qq.com    # 邮箱服务地址\n    port: 587\n    username: 2665249580@qq.com\n    password: pafhbsjewfkaeahc\n    protocol: smtp    # 协议类型\n    default-encoding: UTF-8\nalibaba:\n  cloud:\n    access-key: LTAI5tBonVMHDDUwqkcCGzCr\n    secret-key: 6t9lVo8P53XRwrbucifUJETah7cwUm\n    oss:\n      endpoint: oss-cn-shanghai.aliyuncs.com\nneko:\n  alibaba:\n    cloud:\n      bucket: neko-bucket\n      callback-url: http://k43ejb.natappfree.cc/third_party/oss/callback\n      dir: neko/neko_movie', '72cd7044024c4f2e0ae04c2ee576422c', '2023-07-16 05:13:35', '2023-07-16 05:14:14', 'nacos', '192.168.30.1', '', '3de83da7-02c3-46e1-8c59-930031b9c30b', '第三方服务配置', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (7, 'neko-movie-video-dev.yaml', 'NEKO-MOVIE-VIDEO', 'alipay:\n  app-id: 2021000122615174\n  gateway-url: https://openapi.alipaydev.com/gateway.do\n  sign-type: RSA2\n  merchant-private-key: MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCNZ5AsTx6txuJUFIktVK5VsPKRYxZZgWPD18Z4ptvLADeJwhc2yXkKsTeV8Km4+jM3y/D1jgyiE03G2bKAHW5F11jWf/UBmXr6W++0Hoz4aPLbKWC7hkqkKjK6Aqo1a6g6OWPZgsgH/WwMVfv9Q5u10jTkaLM8rlahcMfcMNomRS5p/ipCn0JNu3Sjq/sFyl3fQvdyk0g18zw39n1N5Zq/cQM4y0aefHdRHmNrckU1eES96zZHr7knchSbczKNn2CSEymUZh/e6CmJ/fSc7jhXgjgO4WjkB4eH6/ZA6Lu4mk7z92JNqZBa21GDAi48gp4wqbbnhcex7NN9NRKBwsApAgMBAAECggEARdYDB08gGLUj7GCkCyZKX3nfotwsGpJjokJ7+/R0ksKNq1SXQHCqfhReHohYWkn/Z6wqWiwIozcjb3TuyoU+g/HCJ1XUvDB/4wvqy6tW9gxm/MRnyNWfIYrRuLo+M9nyoKbdYHxNrFyFs2X36PIuAdNKWhA3jV2Q3XSk0GO0Kbe/LJxon1g7LA+XadeNU5lZyTDGwHYgUZ0c34oKBwL96GyQnW0IQZVItOvfxIuKugA1cS3qug4gynwkUpn028s5CBHMexZuY4oSNp475U5AjHzt4HdaFL5GI33Neu76q1iPbd4POW7mCevr1VT8BM/41PCCR4haNWQjEWCuRsApAQKBgQDAuCZeoaZKyzAKU6EbnOXdbU8A/8ueNoA9YwDI28mZJVHD1C5b7y0YNALd2uCzJloZ5659yA+SWaL5RepjNteJjj4HX6ezpUOJ2MiwGJa87R6xZKlzMVBU2Vj6zSJHqYVNqMbhq1tjg2YuV/utIr2vN7zyk6lU9mJYLtN2WIBp4QKBgQC71e2tt8KmT5Q49IxOkNI8c9zSEFFyFMr/jetSyhGG7v4vvZhjIWnBL/FeICzX9QKBSk2dnXXoxkGUVyNGVA0gbTusV7oyB2JAQNZWFk53doaXyxZ5tBK9tJPoaKSo/D2EqeQ1b72EtxvgBCUQIkWWvK1XL9xnG6LWsacclrxvSQKBgBw+oPj7jgimMNtCmHkYjKPgMlT+KFR+vlrA2MuXUruMOaiOv2Cf9Cb48HadbpMzCr+DMhKjMI0NcBJCifCSiBJT84lXHpf4n6ZjTD5qzCTSR85N53vHfXOCC0VurRh9otjX5JYMbC9wgZhHMs6UKeK1M48FzypxlcZAzqMoo2FhAoGATEdF7APx4gPzF2YIDGe3WKFcjVnfGUrkXP9PDyHMGFW5l72QwsDw52kGxcifLVvsFoEMH/OvzEWmoAz64DaF1iNF1mkRzfs9pQYvhGoul0jkw4PPrsC3054faoQESjOU7+jmKeUku154zXIhcvnX9KzOX9ep051fdXxTdsKn5zECgYAmIXfyg+Ka+DFWRSWuHVPVflCY8faXAG54UBj8b291fVPi0e3DJnzOyzgtgofQn6L2kvo8zibO5IjT3PtlAKMbW/+5bMaj1TdYT+I578jD3qRBa4xm+buPePswMYx8b9GBkEMwRwhpegiMaqF3T17eV+UYnRNTbpgEh6OwWTmVEw==\n  alipay-public-key: MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApdI/vco7lPqKn7LTxvNvp/t8+/K7LWbvHWpI/0UhLIncgv8lixScj7eAVmHedGlkjfFZ9F3Nmbiw8obtEX59B0KfSzhvaRhn4Df4B5YlJq5l8XTQTY5FWQrhG5wvuZ9HOSk0Q8g3q7tH0ChUkCSqWry3WbDLj0nlZ8LRgDXjynVAolsK50qekxCurqNoBndT5/KaRfIi4nqY23nAOoV18K/6ZSIHUntaOv6lF6NxdEfhVNYK5i3QV1RZP9h8dgV9B2MGEVj+IJLqk7l0Pk78e+QDiXLDSCv9iMyUdVDUGtgw+Fr08FOjWXH6Qc7wWyvyQAJx5XbnQ5HvjkBjBWE+yQIDAQAB\n  notify-url: http://xet6em.natappfree.cc/order/order_info/alipay_listener\n  return-url: http://localhost:8080/#/order_complete\n  charset: utf-8\n  timeout: 2m\nneko:\n  movie:\n    thread:\n      pool:\n        core-size: 20\n        max-size: 200\n        keep-alive-time: 10', '1c5e84d9b3190a603059bc6bb07813a0', '2023-07-16 06:00:22', '2023-07-16 06:01:28', 'nacos', '192.168.30.1', '', '3de83da7-02c3-46e1-8c59-930031b9c30b', '视频服务配置', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (8, 'neko-movie-member-dev.yaml', 'NEKO-MOVIE-MEMBER', 'neko:\n  movie:\n    rsa:\n      public-key: MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqizfTC7Xxexfs8wNAQ6H2VjCmBWvH/zFhlYqbabSLlwalwCTKEL0rVZNavpJ77JUQ5TXVdCO+3Y2jUJNhHPV0hBw8UwOmxnUCoKI7wEdnhzLFfuwu+J/dGh3fFle0S3PZhc5a0yfTA1MPZXBZq67NwhIxbOAHSqouSu0R13Q4NwIDAQAB\n      private-key: MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKqLN9MLtfF7F+zzA0BDofZWMKYFa8f/MWGViptptIuXBqXAJMoQvStVk1q+knvslRDlNdV0I77djaNQk2Ec9XSEHDxTA6bGdQKgojvAR2eHMsV+7C74n90aHd8WV7RLc9mFzlrTJ9MDUw9lcFmrrs3CEjFs4AdKqi5K7RHXdDg3AgMBAAECgYAD+yOj9mIvugxebckgeb5boOUaKFvnkMJHLAr5bW0XVGtaziftsZirh3Vc88A5SqtWIsuavnnLzaHYnfEQHhwaq5bL7KyG/pR7ZukEEEWLWuUFfeUBujkxxaIG3J4YU9VXkW7yayfgzyV8fhH288XIFA6KMT2ByccYF2KH4ov6ZQJBANmPqq7tdaUULhsA/9n2CcVcfEQW2NY1IMgPqjOrJXw9U7pqERt7hPqFlcgk+SPRDsxLI7wtnp9EkAtWVMbyJdsCQQDIrPJW86Lf7Miv1zug4deBkmS+QAWwlYTut84WarEDpS2BV4oKvB/KAFKdeA19uyG+pcuLbkT6kVfi1cCJp/vVAkALo4XsCcQLasL0qt8FGAZ5ynLTaa17+CeizEO8s+EzfkxmYpo7sCXzCQZ0SJYTdnGmODbXMFGWD6LtGFc8tWOVAkAS9DLXf5+MouK5qQyav3oJZPha99URvq2VlorCl0Us4PcRVmFVbjtaavlioio0C+6+AQ7eloWxXPT+Gc5bsiAZAkEA0pvYSPAz5g38Fqrxxk4iUL0uRX0IFRzipHwXSe2pCCynRR2GHDzg2s3Iq/GnIU/4fcImIPYR0/AYjzQEkcvFjA==', '29a9f44119abebabae5dca8dd47de9dd', '2023-07-16 12:13:50', '2023-07-16 13:23:04', 'nacos', '192.168.30.1', '', '3de83da7-02c3-46e1-8c59-930031b9c30b', '用户服务配置', '', '', 'yaml', '', '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint(20) UNSIGNED NOT NULL,
  `nid` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (0, 1, 'neko-movie-third-party-dev.yaml', 'NEKO-MOVIE-THIRD-PARTY', '', 'alibaba:\r\n  cloud:\r\n    access-key: LTAI5tBonVMHDDUwqkcCGzCr\r\n    secret-key: 6t9lVo8P53XRwrbucifUJETah7cwUm\r\n    oss:\r\n      endpoint: oss-cn-shanghai.aliyuncs.com\r\nneko:\r\n  alibaba:\r\n    cloud:\r\n      bucket: neko-bucket\r\n      callback-url: http://k43ejb.natappfree.cc/third_party/oss/callback\r\n      dir: neko/neko_convenient', 'ec27b15f8305e714b9a6021a17c2ae80', '2023-07-16 05:13:35', '2023-07-16 05:13:35', NULL, '192.168.30.1', 'I', '3de83da7-02c3-46e1-8c59-930031b9c30b', '');
INSERT INTO `his_config_info` VALUES (5, 2, 'neko-movie-third-party-dev.yaml', 'NEKO-MOVIE-THIRD-PARTY', '', 'alibaba:\r\n  cloud:\r\n    access-key: LTAI5tBonVMHDDUwqkcCGzCr\r\n    secret-key: 6t9lVo8P53XRwrbucifUJETah7cwUm\r\n    oss:\r\n      endpoint: oss-cn-shanghai.aliyuncs.com\r\nneko:\r\n  alibaba:\r\n    cloud:\r\n      bucket: neko-bucket\r\n      callback-url: http://k43ejb.natappfree.cc/third_party/oss/callback\r\n      dir: neko/neko_convenient', 'ec27b15f8305e714b9a6021a17c2ae80', '2023-07-16 05:14:14', '2023-07-16 05:14:14', 'nacos', '192.168.30.1', 'U', '3de83da7-02c3-46e1-8c59-930031b9c30b', '');
INSERT INTO `his_config_info` VALUES (0, 3, 'neko-movie-video-dev.yaml', 'NEKO-MOVIE-VIDEO', '', 'alipay:\r\n  app-id: 2021000122615174\r\n  gateway-url: https://openapi.alipaydev.com/gateway.do\r\n  sign-type: RSA2\r\n  merchant-private-key: MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCNZ5AsTx6txuJUFIktVK5VsPKRYxZZgWPD18Z4ptvLADeJwhc2yXkKsTeV8Km4+jM3y/D1jgyiE03G2bKAHW5F11jWf/UBmXr6W++0Hoz4aPLbKWC7hkqkKjK6Aqo1a6g6OWPZgsgH/WwMVfv9Q5u10jTkaLM8rlahcMfcMNomRS5p/ipCn0JNu3Sjq/sFyl3fQvdyk0g18zw39n1N5Zq/cQM4y0aefHdRHmNrckU1eES96zZHr7knchSbczKNn2CSEymUZh/e6CmJ/fSc7jhXgjgO4WjkB4eH6/ZA6Lu4mk7z92JNqZBa21GDAi48gp4wqbbnhcex7NN9NRKBwsApAgMBAAECggEARdYDB08gGLUj7GCkCyZKX3nfotwsGpJjokJ7+/R0ksKNq1SXQHCqfhReHohYWkn/Z6wqWiwIozcjb3TuyoU+g/HCJ1XUvDB/4wvqy6tW9gxm/MRnyNWfIYrRuLo+M9nyoKbdYHxNrFyFs2X36PIuAdNKWhA3jV2Q3XSk0GO0Kbe/LJxon1g7LA+XadeNU5lZyTDGwHYgUZ0c34oKBwL96GyQnW0IQZVItOvfxIuKugA1cS3qug4gynwkUpn028s5CBHMexZuY4oSNp475U5AjHzt4HdaFL5GI33Neu76q1iPbd4POW7mCevr1VT8BM/41PCCR4haNWQjEWCuRsApAQKBgQDAuCZeoaZKyzAKU6EbnOXdbU8A/8ueNoA9YwDI28mZJVHD1C5b7y0YNALd2uCzJloZ5659yA+SWaL5RepjNteJjj4HX6ezpUOJ2MiwGJa87R6xZKlzMVBU2Vj6zSJHqYVNqMbhq1tjg2YuV/utIr2vN7zyk6lU9mJYLtN2WIBp4QKBgQC71e2tt8KmT5Q49IxOkNI8c9zSEFFyFMr/jetSyhGG7v4vvZhjIWnBL/FeICzX9QKBSk2dnXXoxkGUVyNGVA0gbTusV7oyB2JAQNZWFk53doaXyxZ5tBK9tJPoaKSo/D2EqeQ1b72EtxvgBCUQIkWWvK1XL9xnG6LWsacclrxvSQKBgBw+oPj7jgimMNtCmHkYjKPgMlT+KFR+vlrA2MuXUruMOaiOv2Cf9Cb48HadbpMzCr+DMhKjMI0NcBJCifCSiBJT84lXHpf4n6ZjTD5qzCTSR85N53vHfXOCC0VurRh9otjX5JYMbC9wgZhHMs6UKeK1M48FzypxlcZAzqMoo2FhAoGATEdF7APx4gPzF2YIDGe3WKFcjVnfGUrkXP9PDyHMGFW5l72QwsDw52kGxcifLVvsFoEMH/OvzEWmoAz64DaF1iNF1mkRzfs9pQYvhGoul0jkw4PPrsC3054faoQESjOU7+jmKeUku154zXIhcvnX9KzOX9ep051fdXxTdsKn5zECgYAmIXfyg+Ka+DFWRSWuHVPVflCY8faXAG54UBj8b291fVPi0e3DJnzOyzgtgofQn6L2kvo8zibO5IjT3PtlAKMbW/+5bMaj1TdYT+I578jD3qRBa4xm+buPePswMYx8b9GBkEMwRwhpegiMaqF3T17eV+UYnRNTbpgEh6OwWTmVEw==\r\n  alipay-public-key: MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApdI/vco7lPqKn7LTxvNvp/t8+/K7LWbvHWpI/0UhLIncgv8lixScj7eAVmHedGlkjfFZ9F3Nmbiw8obtEX59B0KfSzhvaRhn4Df4B5YlJq5l8XTQTY5FWQrhG5wvuZ9HOSk0Q8g3q7tH0ChUkCSqWry3WbDLj0nlZ8LRgDXjynVAolsK50qekxCurqNoBndT5/KaRfIi4nqY23nAOoV18K/6ZSIHUntaOv6lF6NxdEfhVNYK5i3QV1RZP9h8dgV9B2MGEVj+IJLqk7l0Pk78e+QDiXLDSCv9iMyUdVDUGtgw+Fr08FOjWXH6Qc7wWyvyQAJx5XbnQ5HvjkBjBWE+yQIDAQAB\r\n  notify-url: http://xet6em.natappfree.cc/order/order_info/alipay_listener\r\n  return-url: http://localhost:8080/#/order_complete\r\n  charset: utf-8\r\n  timeout: 2m', '1417d5d663324529ac356ffebb2b9f96', '2023-07-16 06:00:22', '2023-07-16 06:00:22', NULL, '192.168.30.1', 'I', '3de83da7-02c3-46e1-8c59-930031b9c30b', '');
INSERT INTO `his_config_info` VALUES (7, 4, 'neko-movie-video-dev.yaml', 'NEKO-MOVIE-VIDEO', '', 'alipay:\r\n  app-id: 2021000122615174\r\n  gateway-url: https://openapi.alipaydev.com/gateway.do\r\n  sign-type: RSA2\r\n  merchant-private-key: MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCNZ5AsTx6txuJUFIktVK5VsPKRYxZZgWPD18Z4ptvLADeJwhc2yXkKsTeV8Km4+jM3y/D1jgyiE03G2bKAHW5F11jWf/UBmXr6W++0Hoz4aPLbKWC7hkqkKjK6Aqo1a6g6OWPZgsgH/WwMVfv9Q5u10jTkaLM8rlahcMfcMNomRS5p/ipCn0JNu3Sjq/sFyl3fQvdyk0g18zw39n1N5Zq/cQM4y0aefHdRHmNrckU1eES96zZHr7knchSbczKNn2CSEymUZh/e6CmJ/fSc7jhXgjgO4WjkB4eH6/ZA6Lu4mk7z92JNqZBa21GDAi48gp4wqbbnhcex7NN9NRKBwsApAgMBAAECggEARdYDB08gGLUj7GCkCyZKX3nfotwsGpJjokJ7+/R0ksKNq1SXQHCqfhReHohYWkn/Z6wqWiwIozcjb3TuyoU+g/HCJ1XUvDB/4wvqy6tW9gxm/MRnyNWfIYrRuLo+M9nyoKbdYHxNrFyFs2X36PIuAdNKWhA3jV2Q3XSk0GO0Kbe/LJxon1g7LA+XadeNU5lZyTDGwHYgUZ0c34oKBwL96GyQnW0IQZVItOvfxIuKugA1cS3qug4gynwkUpn028s5CBHMexZuY4oSNp475U5AjHzt4HdaFL5GI33Neu76q1iPbd4POW7mCevr1VT8BM/41PCCR4haNWQjEWCuRsApAQKBgQDAuCZeoaZKyzAKU6EbnOXdbU8A/8ueNoA9YwDI28mZJVHD1C5b7y0YNALd2uCzJloZ5659yA+SWaL5RepjNteJjj4HX6ezpUOJ2MiwGJa87R6xZKlzMVBU2Vj6zSJHqYVNqMbhq1tjg2YuV/utIr2vN7zyk6lU9mJYLtN2WIBp4QKBgQC71e2tt8KmT5Q49IxOkNI8c9zSEFFyFMr/jetSyhGG7v4vvZhjIWnBL/FeICzX9QKBSk2dnXXoxkGUVyNGVA0gbTusV7oyB2JAQNZWFk53doaXyxZ5tBK9tJPoaKSo/D2EqeQ1b72EtxvgBCUQIkWWvK1XL9xnG6LWsacclrxvSQKBgBw+oPj7jgimMNtCmHkYjKPgMlT+KFR+vlrA2MuXUruMOaiOv2Cf9Cb48HadbpMzCr+DMhKjMI0NcBJCifCSiBJT84lXHpf4n6ZjTD5qzCTSR85N53vHfXOCC0VurRh9otjX5JYMbC9wgZhHMs6UKeK1M48FzypxlcZAzqMoo2FhAoGATEdF7APx4gPzF2YIDGe3WKFcjVnfGUrkXP9PDyHMGFW5l72QwsDw52kGxcifLVvsFoEMH/OvzEWmoAz64DaF1iNF1mkRzfs9pQYvhGoul0jkw4PPrsC3054faoQESjOU7+jmKeUku154zXIhcvnX9KzOX9ep051fdXxTdsKn5zECgYAmIXfyg+Ka+DFWRSWuHVPVflCY8faXAG54UBj8b291fVPi0e3DJnzOyzgtgofQn6L2kvo8zibO5IjT3PtlAKMbW/+5bMaj1TdYT+I578jD3qRBa4xm+buPePswMYx8b9GBkEMwRwhpegiMaqF3T17eV+UYnRNTbpgEh6OwWTmVEw==\r\n  alipay-public-key: MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApdI/vco7lPqKn7LTxvNvp/t8+/K7LWbvHWpI/0UhLIncgv8lixScj7eAVmHedGlkjfFZ9F3Nmbiw8obtEX59B0KfSzhvaRhn4Df4B5YlJq5l8XTQTY5FWQrhG5wvuZ9HOSk0Q8g3q7tH0ChUkCSqWry3WbDLj0nlZ8LRgDXjynVAolsK50qekxCurqNoBndT5/KaRfIi4nqY23nAOoV18K/6ZSIHUntaOv6lF6NxdEfhVNYK5i3QV1RZP9h8dgV9B2MGEVj+IJLqk7l0Pk78e+QDiXLDSCv9iMyUdVDUGtgw+Fr08FOjWXH6Qc7wWyvyQAJx5XbnQ5HvjkBjBWE+yQIDAQAB\r\n  notify-url: http://xet6em.natappfree.cc/order/order_info/alipay_listener\r\n  return-url: http://localhost:8080/#/order_complete\r\n  charset: utf-8\r\n  timeout: 2m', '1417d5d663324529ac356ffebb2b9f96', '2023-07-16 06:01:28', '2023-07-16 06:01:28', 'nacos', '192.168.30.1', 'U', '3de83da7-02c3-46e1-8c59-930031b9c30b', '');
INSERT INTO `his_config_info` VALUES (0, 5, 'neko-movie-member-dev.yaml', 'NEKO-MOVIE-MEMBER', '', 'neko:\r\n  convenient:\r\n    rsa:\r\n      public-key: MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqizfTC7Xxexfs8wNAQ6H2VjCmBWvH/zFhlYqbabSLlwalwCTKEL0rVZNavpJ77JUQ5TXVdCO+3Y2jUJNhHPV0hBw8UwOmxnUCoKI7wEdnhzLFfuwu+J/dGh3fFle0S3PZhc5a0yfTA1MPZXBZq67NwhIxbOAHSqouSu0R13Q4NwIDAQAB\r\n      private-key: MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKqLN9MLtfF7F+zzA0BDofZWMKYFa8f/MWGViptptIuXBqXAJMoQvStVk1q+knvslRDlNdV0I77djaNQk2Ec9XSEHDxTA6bGdQKgojvAR2eHMsV+7C74n90aHd8WV7RLc9mFzlrTJ9MDUw9lcFmrrs3CEjFs4AdKqi5K7RHXdDg3AgMBAAECgYAD+yOj9mIvugxebckgeb5boOUaKFvnkMJHLAr5bW0XVGtaziftsZirh3Vc88A5SqtWIsuavnnLzaHYnfEQHhwaq5bL7KyG/pR7ZukEEEWLWuUFfeUBujkxxaIG3J4YU9VXkW7yayfgzyV8fhH288XIFA6KMT2ByccYF2KH4ov6ZQJBANmPqq7tdaUULhsA/9n2CcVcfEQW2NY1IMgPqjOrJXw9U7pqERt7hPqFlcgk+SPRDsxLI7wtnp9EkAtWVMbyJdsCQQDIrPJW86Lf7Miv1zug4deBkmS+QAWwlYTut84WarEDpS2BV4oKvB/KAFKdeA19uyG+pcuLbkT6kVfi1cCJp/vVAkALo4XsCcQLasL0qt8FGAZ5ynLTaa17+CeizEO8s+EzfkxmYpo7sCXzCQZ0SJYTdnGmODbXMFGWD6LtGFc8tWOVAkAS9DLXf5+MouK5qQyav3oJZPha99URvq2VlorCl0Us4PcRVmFVbjtaavlioio0C+6+AQ7eloWxXPT+Gc5bsiAZAkEA0pvYSPAz5g38Fqrxxk4iUL0uRX0IFRzipHwXSe2pCCynRR2GHDzg2s3Iq/GnIU/4fcImIPYR0/AYjzQEkcvFjA==', 'b12d94f6663f9f1775f18a7e88de62b5', '2023-07-16 12:13:50', '2023-07-16 12:13:50', 'nacos', '192.168.30.1', 'I', '3de83da7-02c3-46e1-8c59-930031b9c30b', '');
INSERT INTO `his_config_info` VALUES (8, 6, 'neko-movie-member-dev.yaml', 'NEKO-MOVIE-MEMBER', '', 'neko:\r\n  convenient:\r\n    rsa:\r\n      public-key: MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqizfTC7Xxexfs8wNAQ6H2VjCmBWvH/zFhlYqbabSLlwalwCTKEL0rVZNavpJ77JUQ5TXVdCO+3Y2jUJNhHPV0hBw8UwOmxnUCoKI7wEdnhzLFfuwu+J/dGh3fFle0S3PZhc5a0yfTA1MPZXBZq67NwhIxbOAHSqouSu0R13Q4NwIDAQAB\r\n      private-key: MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKqLN9MLtfF7F+zzA0BDofZWMKYFa8f/MWGViptptIuXBqXAJMoQvStVk1q+knvslRDlNdV0I77djaNQk2Ec9XSEHDxTA6bGdQKgojvAR2eHMsV+7C74n90aHd8WV7RLc9mFzlrTJ9MDUw9lcFmrrs3CEjFs4AdKqi5K7RHXdDg3AgMBAAECgYAD+yOj9mIvugxebckgeb5boOUaKFvnkMJHLAr5bW0XVGtaziftsZirh3Vc88A5SqtWIsuavnnLzaHYnfEQHhwaq5bL7KyG/pR7ZukEEEWLWuUFfeUBujkxxaIG3J4YU9VXkW7yayfgzyV8fhH288XIFA6KMT2ByccYF2KH4ov6ZQJBANmPqq7tdaUULhsA/9n2CcVcfEQW2NY1IMgPqjOrJXw9U7pqERt7hPqFlcgk+SPRDsxLI7wtnp9EkAtWVMbyJdsCQQDIrPJW86Lf7Miv1zug4deBkmS+QAWwlYTut84WarEDpS2BV4oKvB/KAFKdeA19uyG+pcuLbkT6kVfi1cCJp/vVAkALo4XsCcQLasL0qt8FGAZ5ynLTaa17+CeizEO8s+EzfkxmYpo7sCXzCQZ0SJYTdnGmODbXMFGWD6LtGFc8tWOVAkAS9DLXf5+MouK5qQyav3oJZPha99URvq2VlorCl0Us4PcRVmFVbjtaavlioio0C+6+AQ7eloWxXPT+Gc5bsiAZAkEA0pvYSPAz5g38Fqrxxk4iUL0uRX0IFRzipHwXSe2pCCynRR2GHDzg2s3Iq/GnIU/4fcImIPYR0/AYjzQEkcvFjA==', 'b12d94f6663f9f1775f18a7e88de62b5', '2023-07-16 13:23:04', '2023-07-16 13:23:04', 'nacos', '192.168.30.1', 'U', '3de83da7-02c3-46e1-8c59-930031b9c30b', '');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (1, '1', '3de83da7-02c3-46e1-8c59-930031b9c30b', 'dev', '开发环境', 'nacos', 1680237453982, 1680237453982);
INSERT INTO `tenant_info` VALUES (2, '1', '3ec8c692-91a8-48dd-b1b0-1858ab1e0ec4', 'test', '测试环境', 'nacos', 1680237462500, 1680237462500);
INSERT INTO `tenant_info` VALUES (3, '1', 'dc02f852-4fa5-4785-8207-c44595f8b92b', 'prop', '生产环境', 'nacos', 1680237557920, 1680237557920);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
