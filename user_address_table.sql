-- 用户收货地址表（已存在于machinery_mall.sql中）
-- 以下是shipping_address表的完整结构，供参考

/*
CREATE TABLE `shipping_address` (
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
*/

-- 插入测试数据（可选）
-- INSERT INTO `shipping_address` (`user_id`, `name`, `phone`, `mobile`, `province`, `city`, `district`, `addr`, `zip`, `dfault`, `isDel`) VALUES
-- (1, '张三', '010-12345678', '13800138000', '北京市', '北京市', '朝阳区', '三里屯街道1号', '100020', 1, 0),
-- (1, '李四', '021-87654321', '13800138001', '上海市', '上海市', '浦东新区', '陆家嘴街道2号', '200120', 0, 0),
-- (2, '王五', '0755-12345678', '13800138002', '广东省', '深圳市', '南山区', '科技园街道3号', '518000', 1, 0); 