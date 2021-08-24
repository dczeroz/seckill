/*
 Navicat Premium Data Transfer

 Source Server         : my
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 192.168.1.225:3306
 Source Schema         : seckill_goods

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 22/08/2021 22:28:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sk_goods
-- ----------------------------
DROP TABLE IF EXISTS `sk_goods`;
CREATE TABLE `sk_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `goods_detail` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品详情',
  `goods_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sk_goods
-- ----------------------------
INSERT INTO `sk_goods` VALUES (1, 'Redmi Note9Pro', '红米Redmi Note 9 Pro手机游戏官网骁龙750G学生拍照智能5G小米官方旗舰店红米note9pro系列', 'https://edu-dongzdc.oss-cn-beijing.aliyuncs.com/seckill/Redmi.jpg', '红米Redmi Note 9 Pro手机游戏官网骁龙750G学生拍照智能5G小米官方旗舰店红米note9pro系列', 2799.00);
INSERT INTO `sk_goods` VALUES (2, 'iPhone 11', 'Apple/苹果iPhone 11 全网通4G手机苹果 11', 'https://edu-dongzdc.oss-cn-beijing.aliyuncs.com/seckill/iphone11.jpg', 'Apple/苹果iPhone 11 全网通4G手机苹果 11', 5299.00);
INSERT INTO `sk_goods` VALUES (3, 'Xiaomi 11 Ultra', 'Xiaomi/小米 11 Ultra 5G手机官方旗舰店正品官网至尊版系列新款11ultra手机直降', 'https://edu-dongzdc.oss-cn-beijing.aliyuncs.com/seckill/%E5%B0%8F%E7%B1%B311.jpg', 'Xiaomi/小米 11 Ultra 5G手机官方旗舰店正品官网至尊版系列新款11ultra手机直降', 5499.00);
INSERT INTO `sk_goods` VALUES (4, 'Huawei nova 8 Pro ', 'Huawei/华为nova 8 Pro 5G手机麒麟芯片66W超级快充5g官方旗舰店正品新款nova8pro', 'https://edu-dongzdc.oss-cn-beijing.aliyuncs.com/seckill/Huaweinova8Pro.jpg', 'Huawei/华为nova 8 Pro 5G手机麒麟芯片66W超级快充5g官方旗舰店正品新款nova8pro', 3858.00);
INSERT INTO `sk_goods` VALUES (5, 'OPPO Find X3 Pro ', 'OPPO Find X3 Pro oppo5g新品手机 oppo手机官方旗舰店官网正品oppofindx3pro', 'https://edu-dongzdc.oss-cn-beijing.aliyuncs.com/seckill/OPPOFindX3Pro.jpg', 'OPPO Find X3 Pro oppo5g新品手机 oppo手机官方旗舰店官网正品oppofindx3pro', 5999.00);
INSERT INTO `sk_goods` VALUES (6, 'vivo X60', 'vivo X60 曲屏版5G拍照智能手机高清蔡司镜头vivo手机官方旗舰店官网正 vivox60', 'https://edu-dongzdc.oss-cn-beijing.aliyuncs.com/seckill/vivoX60.jpg', 'vivo X60 曲屏版5G拍照智能手机高清蔡司镜头vivo手机官方旗舰店官网正 vivox60', 3499.00);

-- ----------------------------
-- Table structure for sk_goods_seckill
-- ----------------------------
DROP TABLE IF EXISTS `sk_goods_seckill`;
CREATE TABLE `sk_goods_seckill`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品id',
  `goods_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  `seckill_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '秒杀价',
  `goods_count` int(11) NULL DEFAULT NULL COMMENT '商品数量',
  `stock_count` int(11) NULL DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime(0) NULL DEFAULT NULL COMMENT '秒杀结束时间',
  `version` int(11) NULL DEFAULT NULL COMMENT '并发版本控制',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `goods_id`(`goods_id`) USING BTREE,
  CONSTRAINT `sk_goods_seckill_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `sk_goods` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sk_goods_seckill
-- ----------------------------
INSERT INTO `sk_goods_seckill` VALUES (1, 1, 1.00, 10, 10, '2021-08-08 20:58:00', '2022-08-03 21:32:52', 0);
INSERT INTO `sk_goods_seckill` VALUES (2, 2, 1.00, 10, 10, '2021-08-14 17:05:31', '2022-08-20 17:05:33', 0);
INSERT INTO `sk_goods_seckill` VALUES (3, 3, 1.00, 30, 30, '2021-08-08 17:05:48', '2022-08-20 17:05:50', 0);
INSERT INTO `sk_goods_seckill` VALUES (4, 4, 1.00, 10, 10, '2021-08-21 17:06:06', '2022-08-26 17:06:08', 0);
INSERT INTO `sk_goods_seckill` VALUES (5, 5, 1.00, 10, 10, '2021-08-21 17:06:22', '2022-08-28 17:06:24', 0);
INSERT INTO `sk_goods_seckill` VALUES (6, 6, 1.00, 10, 10, '2021-08-08 17:06:38', '2022-08-20 17:06:40', 0);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
