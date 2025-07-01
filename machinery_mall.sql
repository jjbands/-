/*
 Navicat Premium Data Transfer

 Source Server         : MYSQL
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : machinery_mall

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 28/06/2025 14:20:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_items
-- ----------------------------
DROP TABLE IF EXISTS `order_items`;
CREATE TABLE `order_items`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '订单项ID',
  `uid` int(0) NOT NULL COMMENT '用户编号',
  `order_id` int(0) NOT NULL COMMENT '所属订单Id',
  `goods_id` int(0) NOT NULL COMMENT '商品编号',
  `goods_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `icon_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品主图',
  `price` decimal(20, 2) NOT NULL COMMENT '商品单价',
  `quantity` int(0) NOT NULL COMMENT '购买数量',
  `total_price` decimal(20, 2) NOT NULL COMMENT '订单项总价格',
  `created` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id`) USING BTREE,
  INDEX `idx_uid`(`uid`) USING BTREE,
  INDEX `idx_goods_id`(`goods_id`) USING BTREE,
  CONSTRAINT `fk_item_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_item_product` FOREIGN KEY (`goods_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单项表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` bigint(0) NOT NULL COMMENT '订单编号',
  `uid` int(0) NOT NULL COMMENT '用户编号',
  `addr_id` int(0) NOT NULL COMMENT '收货地址编号',
  `amount` decimal(20, 2) NOT NULL COMMENT '订单付款金额',
  `type` int(0) NOT NULL COMMENT '付款类型，1-在线支付，2-货到付款',
  `freight` int(0) NOT NULL DEFAULT 0 COMMENT '运费',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '订单状态：1-未付款，2-已付款，3-已发货，4-交易成功，5-交易关闭，6-已取消',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `finish_time` datetime(0) NULL DEFAULT NULL COMMENT '交易完成时间',
  `close_time` datetime(0) NULL DEFAULT NULL COMMENT '交易关闭时间',
  `created` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_order_no`(`order_no`) USING BTREE,
  INDEX `idx_uid`(`uid`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE,
  INDEX `fk_order_address`(`addr_id`) USING BTREE,
  CONSTRAINT `fk_order_address` FOREIGN KEY (`addr_id`) REFERENCES `shipping_address` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_order_user` FOREIGN KEY (`uid`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `parent_id` int(0) NOT NULL DEFAULT 0 COMMENT '父类ID，0为根节点',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类别名称',
  `sort_order` int(0) NULL DEFAULT NULL COMMENT '同类展示顺序',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态编码，1有效，0无效',
  `level` int(0) NULL DEFAULT NULL COMMENT '节点级别,顶层节点为0',
  `created` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES (1, 0, '螺丝类', NULL, 1, NULL, '2025-06-26 13:52:06', '2025-06-26 14:38:25');
INSERT INTO `product_category` VALUES (2, 0, '工程机械配件', 2, 1, 1, '2025-06-26 14:26:47', '2025-06-26 14:26:47');
INSERT INTO `product_category` VALUES (3, 1, '泵类', 1, 1, 2, '2025-06-26 14:26:47', '2025-06-26 14:26:47');
INSERT INTO `product_category` VALUES (4, 1, '阀类', 2, 1, 2, '2025-06-26 14:26:47', '2025-06-26 14:26:47');
INSERT INTO `product_category` VALUES (5, 0, '液压元件', 1, 1, 1, '2025-06-26 14:26:47', '2025-06-26 14:26:47');

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `product_id` int(0) NOT NULL COMMENT '产品类型编号',
  `parts_id` int(0) NOT NULL COMMENT '配件分类',
  `icon_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品主图片',
  `sub_images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '图片地址，一组小图',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品详情',
  `spec_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '规格参数',
  `price` decimal(20, 2) NOT NULL COMMENT '价格',
  `stock` int(0) NOT NULL COMMENT '库存',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '商品状态：1-待售，2-上架，3-下架',
  `is_hot` int(0) NOT NULL DEFAULT 2 COMMENT '是否热销，1-是，2-否',
  `created` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE,
  INDEX `idx_parts_id`(`parts_id`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE,
  CONSTRAINT `fk_product_category` FOREIGN KEY (`product_id`) REFERENCES `product_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_product_parts` FOREIGN KEY (`parts_id`) REFERENCES `product_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, '拖拉机', 1, 1, NULL, NULL, NULL, NULL, 1.00, 1, 2, 2, '2025-06-26 14:01:45', '2025-06-26 14:42:58');
INSERT INTO `products` VALUES (2, '柱塞泵B型', 1, 2, 'https://via.placeholder.com/250x200?text=柱塞泵B', '', '高压柱塞泵，性能稳定', '型号B', 2200.00, 30, 2, 0, '2025-06-26 14:29:33', '2025-06-26 14:34:37');
INSERT INTO `products` VALUES (3, '溢流阀C型', 4, 1, 'https://via.placeholder.com/250x200?text=溢流阀C', '', '高精度溢流阀', '型号C', 800.00, 100, 2, 0, '2025-06-26 14:29:33', '2025-06-26 14:34:37');
INSERT INTO `products` VALUES (4, '履带', 2, 3, 'https://via.placeholder.com/250x200?text=履带', '', '工程机械专用履带', '型号D', 3500.00, 20, 2, 1, '2025-06-26 14:29:33', '2025-06-26 14:34:37');
INSERT INTO `products` VALUES (5, '齿轮泵A型', 1, 1, 'https://via.placeholder.com/250x200?text=齿轮泵A', '', '高效齿轮泵，适用于液压系统', '型号A', 1200.00, 50, 2, 1, '2025-06-26 14:29:33', '2025-06-26 14:34:37');

-- ----------------------------
-- Table structure for shipping_address
-- ----------------------------
DROP TABLE IF EXISTS `shipping_address`;
CREATE TABLE `shipping_address`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  `user_id` int(0) NOT NULL COMMENT '用户编号',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收件人姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '收件人固定电话号码',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收件人手机号码',
  `province` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省份',
  `city` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '城市',
  `district` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区/县',
  `addr` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `zip` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮编',
  `dfault` tinyint(1) NULL DEFAULT 0 COMMENT '是否是默认地址，0-非默认，1-默认',
  `isDel` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0：正常，1：已删除',
  `created` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_address_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收货地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `user_id` int(0) NOT NULL COMMENT '用户ID',
  `product_id` int(0) NOT NULL COMMENT '商品ID',
  `quantity` int(0) NOT NULL COMMENT '商品数量',
  `created` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_product`(`user_id`, `product_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `fk_cart_product`(`product_id`) USING BTREE,
  CONSTRAINT `fk_cart_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '购物车表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码(MD5加密)',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `question` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码问题',
  `asw` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '找回密码答案',
  `role` int(0) NOT NULL DEFAULT 1 COMMENT '角色1-普通用户、2-管理员',
  `age` int(0) NULL DEFAULT NULL COMMENT '年龄',
  `sex` tinyint(1) NULL DEFAULT NULL COMMENT '性别，1：男、0：女',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del` tinyint(1) NOT NULL DEFAULT 0 COMMENT '正常：0、删除：1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_account`(`account`) USING BTREE,
  INDEX `idx_email`(`email`) USING BTREE,
  INDEX `idx_phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'user', '123456', NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2025-06-24 16:47:44', '2025-06-24 16:47:44', 0);
INSERT INTO `users` VALUES (2, 'hzh', 'e10adc3949ba59abbe56e057f20f883e', '3281714703@qq.com', '15266689761', 'who are you', '111', 1, NULL, NULL, '柳贯一', '2025-06-28 14:09:05', '2025-06-28 14:09:05', 0);

SET FOREIGN_KEY_CHECKS = 1;
