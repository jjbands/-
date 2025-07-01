-- 地址管理功能调试SQL脚本
-- 请按顺序执行以下SQL语句来排查问题

-- 1. 检查数据库连接和表是否存在
SHOW TABLES LIKE 'users';
SHOW TABLES LIKE 'shipping_address';

-- 2. 检查用户表结构和数据
DESCRIBE users;
SELECT * FROM users LIMIT 5;

-- 3. 检查地址表结构
DESCRIBE shipping_address;

-- 4. 检查地址表是否有数据
SELECT COUNT(*) as total_addresses FROM shipping_address;
SELECT * FROM shipping_address LIMIT 5;

-- 5. 查找特定用户（假设用户账号为 'admin'）
SELECT * FROM users WHERE account = 'admin';

-- 6. 查找该用户的地址
SELECT * FROM shipping_address 
WHERE user_id = (SELECT id FROM users WHERE account = 'admin');

-- 7. 检查是否有软删除的地址
SELECT * FROM shipping_address 
WHERE user_id = (SELECT id FROM users WHERE account = 'admin') 
AND isDel = 1;

-- 8. 检查默认地址
SELECT * FROM shipping_address 
WHERE user_id = (SELECT id FROM users WHERE account = 'admin') 
AND dfault = 1;

-- 9. 插入测试数据（如果需要）
-- 注意：请先确认用户ID存在
INSERT INTO shipping_address (
    user_id, name, phone, mobile, province, city, district, 
    addr, zip, dfault, isDel, created, updated
) VALUES (
    (SELECT id FROM users WHERE account = 'admin' LIMIT 1),
    '测试用户',
    '010-12345678',
    '13800138000',
    '北京市',
    '北京市',
    '朝阳区',
    '测试地址123号',
    '100020',
    1,
    0,
    NOW(),
    NOW()
);

-- 10. 验证插入结果
SELECT * FROM shipping_address 
WHERE user_id = (SELECT id FROM users WHERE account = 'admin')
ORDER BY created DESC;

-- 11. 检查外键约束
SELECT 
    TABLE_NAME,
    COLUMN_NAME,
    CONSTRAINT_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = 'machinery_mall'
AND TABLE_NAME = 'shipping_address';

-- 12. 检查索引
SHOW INDEX FROM shipping_address;

-- 13. 模拟API查询的SQL（与Mapper中的查询一致）
SELECT * FROM shipping_address
WHERE user_id = (SELECT id FROM users WHERE account = 'admin') 
AND isDel = 0
ORDER BY dfault DESC, created DESC; 