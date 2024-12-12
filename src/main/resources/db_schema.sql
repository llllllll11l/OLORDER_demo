use test;

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
DROP TABLE IF EXISTS admins;

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
    INDEX idx_users_username (username),  -- 为用户名创建索引
    INDEX idx_users_email (email),  -- 为邮箱创建索引
    INDEX idx_users_phone_number (phone_number)  -- 为手机号创建索引
);

-- 管理员表
CREATE TABLE admins (
    admin_id VARCHAR(255) NOT NULL PRIMARY KEY,  -- 管理员ID，主键
    admin_name VARCHAR(255) NOT NULL UNIQUE,  -- 管理员用户名，唯一约束
    password_hash VARCHAR(255) NOT NULL,  -- 密码哈希
    email VARCHAR(255) UNIQUE,  -- 管理员邮箱，唯一约束
    phone_number VARCHAR(20) UNIQUE,  -- 管理员手机号，唯一约束
    profile_picture VARCHAR(255),  -- 管理员头像
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 注册日期
    last_login_date TIMESTAMP,  -- 上次登录时间
    status ENUM('ACTIVE', 'DISABLED') NOT NULL DEFAULT 'ACTIVE',  -- 管理员状态
    last_password_change TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 上次密码修改时间
    INDEX idx_admins_admin_name (admin_name),  -- 为管理员用户名创建索引
    INDEX idx_admins_email (email)  -- 为管理员邮箱创建索引
);

-- 店铺表
DROP TABLE IF EXISTS stores;
CREATE TABLE IF NOT EXISTS stores (
    store_id VARCHAR(255) NOT NULL PRIMARY KEY,  -- 店铺ID，主键
    store_name VARCHAR(255) NOT NULL UNIQUE,  -- 店铺名称，唯一约束
    store_description TEXT,  -- 店铺描述
    store_address VARCHAR(255) NOT NULL,  -- 店铺地址
    contact_number VARCHAR(20) NOT NULL,  -- 联系电话
    store_type ENUM('RESTAURANT', 'RETAIL', 'SERVICE'),  -- 店铺类型
    store_status ENUM('PENDING', 'APPROVED', 'DISABLED') NOT NULL DEFAULT 'PENDING',  -- 店铺状态
    verification_docs JSON,  -- 店铺验证文档
    owner_id VARCHAR(255) NOT NULL,  -- 店铺所有者ID
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
    order_status ENUM('PENDING', 'SHIPPED', 'DELIVERED', 'CANCELED') NOT NULL DEFAULT 'PENDING',  -- 订单状态
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
use test;

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
