-- 测试用户		密码
-- admin		admin
-- customer		customer
-- merchant		merchant

use test_test;

DROP TABLE IF EXISTS store_status_history;
DROP TABLE IF EXISTS user_status_history;
DROP TABLE IF EXISTS product_reviews;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS store_reviews;
DROP TABLE IF EXISTS user_tokens;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS stores;
DROP TABLE IF EXISTS users;

-- 用户表
CREATE TABLE users (
    user_id VARCHAR(255) PRIMARY KEY,  -- 用户ID，主键
    username VARCHAR(255) NOT NULL UNIQUE,  -- 用户名，唯一索引
    password_hash VARCHAR(255) NOT NULL,  -- 密码哈希
    email VARCHAR(255) UNIQUE,  -- 邮箱，唯一约束
    phone_number VARCHAR(20) UNIQUE,  -- 手机号码，唯一约束
    user_type ENUM('CUSTOMER', 'MERCHANT', 'DELIVERY') NOT NULL,  -- 用户类型
    profile_picture VARCHAR(255),  -- 用户头像
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 注册日期
    last_login_date TIMESTAMP,  -- 上次登录时间
    status ENUM('ACTIVE', 'SUSPENDED', 'DISABLED', 'DELETED') NOT NULL DEFAULT 'ACTIVE',  -- 用户状态
    last_password_change TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 上次密码修改时间
    is_email_verified BOOLEAN DEFAULT FALSE,  -- 邮箱是否验证
    is_phone_verified BOOLEAN DEFAULT FALSE,  -- 手机是否验证
    is_admin BOOLEAN DEFAULT FALSE, -- 是否为管理员
    INDEX idx_users_username (username),  -- 为用户名创建索引
    INDEX idx_users_email (email),  -- 为邮箱创建索引
    INDEX idx_users_phone_number (phone_number)  -- 为手机号创建索引
);

-- 店铺表
CREATE TABLE IF NOT EXISTS stores (
    store_id VARCHAR(255) NOT NULL PRIMARY KEY,  -- 店铺ID，主键
    store_name VARCHAR(255) NOT NULL UNIQUE,  -- 店铺名称，唯一约束
    store_description TEXT,  -- 店铺描述
    store_address VARCHAR(255) NOT NULL,  -- 店铺地址
    contact_number VARCHAR(20) NOT NULL,  -- 联系电话
    store_type ENUM('RESTAURANT', 'RETAIL', 'SERVICE'),  -- 店铺类型
    store_status ENUM('PENDING', 'APPROVED', 'DISABLED') NOT NULL DEFAULT 'PENDING',  -- 店铺状态
    verification_docs VARCHAR(255),  -- 店铺验证文档
    owner_id VARCHAR(255) NOT NULL,  -- 店铺所有者ID
    visited INT NOT NULL DEFAULT 0, -- 最近访问量
    rating DOUBLE, -- 平均评分
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,  -- 创建时间
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新时间
    FOREIGN KEY (owner_id) REFERENCES users(user_id),  -- 外键，关联用户表
    INDEX idx_stores_store_name (store_name),  -- 为店铺名称创建索引
    INDEX idx_stores_store_status (store_status),  -- 为店铺状态创建索引
    INDEX idx_stores_owner_id (owner_id)  -- 为商户ID创建索引
);

-- 商品表
CREATE TABLE IF NOT EXISTS products (
    product_id VARCHAR(255) PRIMARY KEY,  -- 商品ID，主键
    product_name VARCHAR(255) NOT NULL,  -- 商品名称
    product_description TEXT,  -- 商品描述
    product_category VARCHAR(255) NOT NULL,  -- 商品类别
    price DECIMAL(10, 2) NOT NULL,  -- 商品价格
    stock_quantity INT NOT NULL DEFAULT -1,  -- 商品库存
    product_status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE',  -- 商品状态
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 创建时间
    update_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新时间
    product_image VARCHAR(255),  -- 商品图片
    store_id VARCHAR(255) NOT NULL,  -- 店铺ID
    FOREIGN KEY (store_id) REFERENCES stores(store_id),  -- 外键，关联店铺表
    INDEX idx_products_product_name (product_name),  -- 为商品名称创建索引
    INDEX idx_products_product_category (product_category),  -- 为商品分类创建索引
    INDEX idx_products_price (price),  -- 为商品价格创建索引
    INDEX idx_products_stock_quantity (stock_quantity)  -- 为商品库存创建索引
);

-- 订单表
CREATE TABLE IF NOT EXISTS orders (
    order_id VARCHAR(255) PRIMARY KEY,  -- 订单ID，主键
    customer_id VARCHAR(255) NOT NULL,  -- 顾客ID
    store_id VARCHAR(255) NOT NULL,  -- 店铺ID
    order_status ENUM('PENDING', 'PAID','CONFIRMED','SHIPPED', 'DELIVERED', 'CANCELED') NOT NULL DEFAULT 'PENDING',  -- 订单状态
    order_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 订单创建时间
    total_amount DECIMAL(10, 2) NOT NULL,  -- 订单总金额
    delivery_address TEXT NOT NULL,  -- 配送地址
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,  -- 创建时间
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 更新时间
    FOREIGN KEY (customer_id) REFERENCES Users(user_id),  -- 外键，关联用户表
    FOREIGN KEY (store_id) REFERENCES Stores(store_id),  -- 外键，关联店铺表
    INDEX idx_orders_customer_id (customer_id),  -- 为顾客ID创建索引
    INDEX idx_orders_status (order_status),  -- 为订单状态创建索引
    INDEX idx_orders_order_date (order_date)  -- 为订单日期创建索引
);

-- 订单商品表
CREATE TABLE IF NOT EXISTS order_items (
    order_item_id VARCHAR(255) PRIMARY KEY,  -- 订单商品ID，主键
    order_id VARCHAR(255) NOT NULL,  -- 订单ID
    product_id VARCHAR(255) NOT NULL,  -- 商品ID
    quantity INT NOT NULL,  -- 商品数量
    price DECIMAL(10, 2) NOT NULL,  -- 商品价格
    total_price DECIMAL(10, 2) NOT NULL,  -- 商品总价
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),  -- 外键，关联订单表
    FOREIGN KEY (product_id) REFERENCES Products(product_id),  -- 外键，关联商品表
    INDEX idx_order_items_order_id (order_id),  -- 为订单ID创建索引
    INDEX idx_order_items_product_id (product_id)  -- 为商品ID创建索引
);

-- 商品评价表
CREATE TABLE IF NOT EXISTS product_reviews (
    review_id VARCHAR(255) PRIMARY KEY,  -- 评论ID，主键
    order_id VARCHAR(255) NOT NULL,  -- 订单ID
    product_id VARCHAR(255) NOT NULL,  -- 商品ID
    customer_id VARCHAR(255) NOT NULL,  -- 顾客ID
    rating INT CHECK(rating BETWEEN 1 AND 5) NOT NULL,  -- 商品评分（1-5星）
    comment TEXT,  -- 评论内容
    review_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 评论日期
    status ENUM('NORMAL', 'DELETED') DEFAULT 'NORMAL',  -- 评论状态
    response TEXT,  -- 商户或管理员回复
    response_date DATETIME,  -- 回复时间
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),  -- 外键，关联订单表
    FOREIGN KEY (product_id) REFERENCES Products(product_id),  -- 外键，关联商品表
    FOREIGN KEY (customer_id) REFERENCES Users(user_id),  -- 外键，关联用户表
    INDEX idx_order_id (order_id),  -- 为订单ID创建索引
    INDEX idx_product_id (product_id),  -- 为商品ID创建索引
    INDEX idx_status (status)  -- 为状态创建索引
);

-- 店铺评价表
CREATE TABLE IF NOT EXISTS store_reviews (
    review_id VARCHAR(255) PRIMARY KEY,  -- 评论ID，主键
    order_id VARCHAR(255),  -- 关联的订单ID
    store_id VARCHAR(255) NOT NULL,  -- 关联的店铺ID
    customer_id VARCHAR(255) NOT NULL,  -- 提交评价的客户ID
    rating INT CHECK(rating BETWEEN 1 AND 5) NOT NULL,  -- 店铺评分（1-5星）
    comment TEXT,  -- 评论内容
    review_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 评价创建时间
    status ENUM('NORMAL', 'DELETED') DEFAULT 'NORMAL',  -- 评价状态（如正常、已删除）
    response TEXT,  -- 商户或管理员回复的评论
    response_date DATETIME,  -- 回复时间
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),  -- 外键，关联订单表
    FOREIGN KEY (store_id) REFERENCES Stores(store_id),  -- 外键，关联店铺表
    FOREIGN KEY (customer_id) REFERENCES Users(user_id),  -- 外键，关联用户表
    INDEX idx_order_id (order_id),  -- 为订单ID创建索引
    INDEX idx_store_id (store_id),  -- 为店铺ID创建索引
    INDEX idx_status (status)  -- 为状态创建索引
);

-- 店铺历史状态表
CREATE TABLE IF NOT EXISTS store_status_history (
    history_id VARCHAR(255) PRIMARY KEY,  -- 状态历史ID，主键
    store_id VARCHAR(255) NOT NULL,  -- 店铺ID
    status ENUM('PENDING', 'APPROVED', 'DISABLED') NOT NULL,  -- 店铺状态
    changed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 状态更改时间
    remarks TEXT,  -- 备注
    FOREIGN KEY (store_id) REFERENCES Stores(store_id),  -- 外键，关联店铺表
    INDEX idx_store_status_history_store_id (store_id),  -- 为店铺ID创建索引
    INDEX idx_store_status_history_status (status),  -- 为状态创建索引
    INDEX idx_store_status_history_changed_at (changed_at)  -- 为状态更改时间创建索引
);

-- 用户状态历史表
CREATE TABLE IF NOT EXISTS user_status_history (
    history_id VARCHAR(255) PRIMARY KEY,  -- 状态历史ID，主键
    user_id VARCHAR(255) NOT NULL,  -- 用户ID，外键关联用户表
    status ENUM('ACTIVE', 'SUSPENDED', 'DISABLED') NOT NULL,  -- 用户状态
    changed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 状态更改时间
    remarks TEXT,  -- 备注
    FOREIGN KEY (user_id) REFERENCES Users(user_id),  -- 外键，关联用户表
    INDEX idx_user_status_history_user_id (user_id),  -- 为用户ID创建索引
    INDEX idx_user_status_history_status (status),  -- 为状态创建索引
    INDEX idx_user_status_history_changed_at (changed_at)  -- 为状态更改时间创建索引
);

-- 用户Token表
CREATE TABLE IF NOT EXISTS user_tokens (
    token_id VARCHAR(255) PRIMARY KEY,  -- Token ID，主键
    user_id VARCHAR(255) NOT NULL,  -- 用户ID，外键关联用户表
    token VARCHAR(255) NOT NULL UNIQUE,  -- Token值，唯一约束
    expire_time TIMESTAMP NOT NULL,  -- Token过期时间
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 创建时间
    FOREIGN KEY (user_id) REFERENCES Users(user_id),  -- 外键，关联用户表
    INDEX idx_user_tokens_user_id (user_id),  -- 为用户ID创建索引
    INDEX idx_user_tokens_token (token)  -- 为Token值创建索引
);


-- users表插入数据
INSERT ignore INTO users (
    user_id,
    username,
    password_hash,
    email,
    phone_number,
    user_type,
    profile_picture,
    registration_date,
    last_login_date,
    status,
    last_password_change,
    is_email_verified,
    is_phone_verified,
    is_admin
) VALUES (
    '38cec3fa-bd72-4d4c-88a9-fb406b11f379',                        -- 用户ID
    'customer',                      -- 用户名
    'b6c45863875e34487ca3c155ed145efe12a74581e27befec5aa661b8ee8ca6dd',           -- 密码哈希
    'customer@email',          -- 邮箱
    '18012341234',                   -- 手机号码
    'CUSTOMER',                       -- 用户类型
    'customerPicture',    -- 用户头像
    '2024-12-17 20:26:20',                           -- 注册日期
    '2024-12-17 21:40:50',                           -- 上次登录时间
    'ACTIVE',                        -- 用户状态
    '2024-12-17 21:45:22',			 -- 最后密码修改时间
    TRUE,                            -- 邮箱是否验证
    TRUE,                            -- 手机是否验证
    FALSE                            -- 是否为管理员
);
INSERT INTO users (
    user_id,
    username,
    password_hash,
    email,
    phone_number,
    user_type,
    profile_picture,
    registration_date,
    last_login_date,
    status,
    last_password_change,
    is_email_verified,
    is_phone_verified,
    is_admin
) VALUES (
    '3993a8c0-a58b-4fcf-a384-42c6cfbedce8',                        -- 用户ID
    'merchant',                      -- 用户名
    '9e786cce0d95f0f608958afef7a476a21f6c6fb3dce981d2ac054b5f1e9cb921',           -- 密码哈希
    'merchant@email',          -- 邮箱
    '13012341234',                   -- 手机号码
    'MERCHANT',                       -- 用户类型
    'merchantPicture',    -- 用户头像
    '2024-12-17 20:26:43',                           -- 注册日期
    '2024-12-18 13:50:25',                           -- 上次登录时间
    'ACTIVE',                        -- 用户状态
    '2024-12-17 21:43:54',			 -- 最后密码修改时间
    TRUE,                            -- 邮箱是否验证
    TRUE,                            -- 手机是否验证
    FAlSE                            -- 是否为管理员
);
INSERT INTO users (
    user_id,
    username,
    password_hash,
    email,
    phone_number,
    user_type,
    profile_picture,
    registration_date,
    last_login_date,
    status,
    last_password_change,
    is_email_verified,
    is_phone_verified,
    is_admin
) VALUES (
    '956e202c-060c-4f96-b8fa-95c75f5b23ea',                        -- 用户ID
    'admin',                      -- 用户名
    '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',           -- 密码哈希
    'admin@email',          -- 邮箱
    '11012341234',                   -- 手机号码
    'CUSTOMER',                       -- 用户类型
    'adminPicture',    -- 用户头像
    '2024-12-17 20:24:48',                           -- 注册日期
    '2024-12-17 21:44:43',                           -- 上次登录时间
    'ACTIVE',                        -- 用户状态
    '2024-12-17 21:45:22',			 -- 最后密码修改时间
    TRUE,                            -- 邮箱是否验证
    TRUE,                            -- 手机是否验证
    TRUE                            -- 是否为管理员
);


-- stores表插入数据
INSERT INTO stores (
    store_id,
    store_name,
    store_description,
    store_address,
    contact_number,
    store_type,
    store_status,
    verification_docs,
    owner_id,
    visited,
    rating,
    created_at,
    updated_at
) VALUES (
    '226b0003-d458-42f2-a87f-479f3f96c8bd',              -- 店铺ID
    '跑腿服务',            -- 店铺名称
    '专业跑腿',     -- 店铺描述
    '西园七舍',         -- 店铺地址
    '55443322',        -- 联系电话
    'SERVICE',            -- 店铺类型
    'PENDING',          -- 店铺状态
    '',     -- 店铺验证文档
    '3993a8c0-a58b-4fcf-a384-42c6cfbedce8',              -- 店铺所有者ID
    '0',              -- 最近访问量
    '0',               -- 平均评分
    '2024-12-17 21:50:02',            -- 创建时间
    '2024-12-17 21:48:49'             -- 更新时间
);
INSERT INTO stores (
    store_id,
    store_name,
    store_description,
    store_address,
    contact_number,
    store_type,
    store_status,
    verification_docs,
    owner_id,
    visited,
    rating,
    created_at,
    updated_at
) VALUES (
    '8eca9c45-6c4b-4316-859c-f4f66bf8b48e',              -- 店铺ID
    '江安小卖部',            -- 店铺名称
    '经济实惠',     -- 店铺描述
    '江安东门',         -- 店铺地址
    '11110000',        -- 联系电话
    'RETAIL',            -- 店铺类型
    'PENDING',          -- 店铺状态
    '',     -- 店铺验证文档
    '3993a8c0-a58b-4fcf-a384-42c6cfbedce8',              -- 店铺所有者ID
    '0',              -- 最近访问量
    '0',               -- 平均评分
    '2024-12-17 21:50:02',            -- 创建时间
    '2024-12-17 21:50:02'             -- 更新时间
);
INSERT INTO stores (
    store_id,
    store_name,
    store_description,
    store_address,
    contact_number,
    store_type,
    store_status,
    verification_docs,
    owner_id,
    visited,
    rating,
    created_at,
    updated_at
) VALUES (
    'c2d3f621-139f-400e-8632-b0f8fdd63959',              -- 店铺ID
    '东北菜馆',            -- 店铺名称
    '正宗东北菜',     -- 店铺描述
    '青春广场',         -- 店铺地址
    '99887766',        -- 联系电话
    'RESTAURANT',            -- 店铺类型
    'PENDING',          -- 店铺状态
    '',     -- 店铺验证文档
    '3993a8c0-a58b-4fcf-a384-42c6cfbedce8',              -- 店铺所有者ID
    '0',              -- 最近访问量
    '0',               -- 平均评分
    '2024-12-17 21:47:33',            -- 创建时间
    '2024-12-17 21:47:33'             -- 更新时间
);

 -- products表插入数据
 INSERT INTO products (
    product_id,
    product_name,
    product_description,
    product_category,
    price,
    stock_quantity,
    product_status,
    create_date,
    update_date,
    product_image,
    store_id
) VALUES (
    '265cc7b6-99cd-4fb7-903c-77ab8bdb7852',           -- 商品ID
    '薯片',         -- 商品名称
    '大包',  -- 商品描述
    '零食',     -- 商品类别
    '6.00',               -- 商品价格
    '100',       -- 商品库存
    'ACTIVE',       -- 商品状态
    '2024-12-17 21:57:54',          -- 创建时间
    '2024-12-17 22:00:08',          -- 更新时间
    'http://localhost:8081/images/%E8%96%AF%E7%89%87_1734501297328_fangBianMian.jpg',        -- 商品图片
    '8eca9c45-6c4b-4316-859c-f4f66bf8b48e'              -- 店铺ID
);
 INSERT INTO products (
    product_id,
    product_name,
    product_description,
    product_category,
    price,
    stock_quantity,
    product_status,
    create_date,
    update_date,
    product_image,
    store_id
) VALUES (
    '368d7dbc-5ebc-49ff-8fd8-9bfad4bccf34',           -- 商品ID
    '代取快递',         -- 商品名称
    '使命必达',  -- 商品描述
    '代取服务',     -- 商品类别
    '5.00',               -- 商品价格
    '40',       -- 商品库存
    'ACTIVE',       -- 商品状态
    '2024-12-17 22:02:27',          -- 创建时间
    '2024-12-17 22:02:27',          -- 更新时间
    'http://localhost:8081/images/%E4%BB%A3%E5%8F%96%E5%BF%AB%E9%80%92_1734501894983_daiQuKuaiDi.jpg',        -- 商品图片
    '226b0003-d458-42f2-a87f-479f3f96c8bd'              -- 店铺ID
);
INSERT INTO products (
    product_id,
    product_name,
    product_description,
    product_category,
    price,
    stock_quantity,
    product_status,
    create_date,
    update_date,
    product_image,
    store_id
) VALUES (
    'b7c3c12a-39f3-49c7-b13d-2af9bc162c62',           -- 商品ID
    '代买代送',         -- 商品名称
    '使命必达',  -- 商品描述
    '代买服务',     -- 商品类别
    '5.00',               -- 商品价格
    '100',       -- 商品库存
    'ACTIVE',       -- 商品状态
    '2024-12-17 22:02:11',          -- 创建时间
    '2024-12-18 14:12:22',          -- 更新时间
    'http://localhost:8081/images/%E4%BB%A3%E4%B9%B0%E5%95%86%E5%93%81_1734501369686_daiMaiDaiSong.jpg',        -- 商品图片
    '226b0003-d458-42f2-a87f-479f3f96c8bd'              -- 店铺ID
);
INSERT INTO products (
    product_id,
    product_name,
    product_description,
    product_category,
    price,
    stock_quantity,
    product_status,
    create_date,
    update_date,
    product_image,
    store_id
) VALUES (
    '38d11f32-8a71-4ac6-abae-6e5eadf9e3e5',           -- 商品ID
    '溜肉段',         -- 商品名称
    '招牌菜',  -- 商品描述
    '荤菜',     -- 商品类别
    '20.00',               -- 商品价格
    '100',       -- 商品库存
    'ACTIVE',       -- 商品状态
    '2024-12-17 21:55:05',          -- 创建时间
    '2024-12-17 21:55:05',          -- 更新时间
    'http://localhost:8081/images/%E6%BA%9C%E8%82%89%E6%AE%B5_1734501321281_liuRouDuan.jpg',        -- 商品图片
    'c2d3f621-139f-400e-8632-b0f8fdd63959'              -- 店铺ID
);
INSERT INTO products (
    product_id,
    product_name,
    product_description,
    product_category,
    price,
    stock_quantity,
    product_status,
    create_date,
    update_date,
    product_image,
    store_id
) VALUES (
    '6b2adcc6-1020-470f-9b35-fd7ca9867414',           -- 商品ID
    '地三鲜',         -- 商品名称
    '招牌菜',  -- 商品描述
    '素菜',     -- 商品类别
    '10.00',               -- 商品价格
    '150',       -- 商品库存
    'ACTIVE',       -- 商品状态
    '2024-12-17 21:55:56',          -- 创建时间
    '2024-12-17 21:55:56',          -- 更新时间
    'http://localhost:8081/images/%E5%9C%B0%E4%B8%89%E9%B2%9C_1734501638479_diSanXian.jpg',        -- 商品图片
    'c2d3f621-139f-400e-8632-b0f8fdd63959'              -- 店铺ID
);
INSERT INTO products (
    product_id,
    product_name,
    product_description,
    product_category,
    price,
    stock_quantity,
    product_status,
    create_date,
    update_date,
    product_image,
    store_id
) VALUES (
    '9a530b86-ca2b-4313-b17d-bc0703dcaf5c',           -- 商品ID
    '方便面',         -- 商品名称
    '桶装',  -- 商品描述
    '速食',     -- 商品类别
    '5.00',               -- 商品价格
    '100',       -- 商品库存
    'ACTIVE',       -- 商品状态
    '2024-12-17 21:57:12',          -- 创建时间
    '2024-12-18 14:12:02',          -- 更新时间
    'http://localhost:8081/images/%E6%B3%A1%E9%9D%A2_1734501280356_fangBianMian.jpg',        -- 商品图片
    '8eca9c45-6c4b-4316-859c-f4f66bf8b48e'              -- 店铺ID
);