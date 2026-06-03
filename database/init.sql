-- 创建数据库
CREATE DATABASE IF NOT EXISTS food_delivery CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE food_delivery;

-- 用户表
CREATE TABLE IF NOT EXISTS user (
    person_id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_type TINYINT DEFAULT 1 COMMENT '0管理员,1普通用户,2商家,3骑手',
    phone VARCHAR(20),
    avatar VARCHAR(200),
    balance DECIMAL(10,2) DEFAULT 0.00 COMMENT '账户余额',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 收货地址表
CREATE TABLE IF NOT EXISTS address (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    receiver_name VARCHAR(50),
    phone VARCHAR(20),
    province VARCHAR(50),
    city VARCHAR(50),
    district VARCHAR(50),
    detail VARCHAR(200),
    is_default TINYINT DEFAULT 0 COMMENT '1=默认地址，0=普通地址',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(person_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收货地址信息';

-- 商家表
CREATE TABLE IF NOT EXISTS restaurant (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL UNIQUE COMMENT '绑定的商家账号ID',
    name VARCHAR(100) NOT NULL COMMENT '店铺名称',
    logo VARCHAR(200) COMMENT '店铺头像图片路径',
    phone VARCHAR(20) COMMENT '商家联系电话',
    address VARCHAR(200) COMMENT '店铺实体地址',
    status TINYINT DEFAULT 0 COMMENT '0待审核 1营业中 2已关停',
    business_hours VARCHAR(100) COMMENT '营业时间',
    avg_rating DECIMAL(2,1) DEFAULT 5.0 COMMENT '店铺平均分',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(person_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='外卖商家表';

-- 菜品分类
CREATE TABLE IF NOT EXISTS category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    restaurant_id INT NOT NULL COMMENT '所属店铺ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    sort_order INT DEFAULT 0 COMMENT '分类排序权重',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品分类';

-- 菜品表
CREATE TABLE IF NOT EXISTS dish (
    id INT PRIMARY KEY AUTO_INCREMENT,
    restaurant_id INT NOT NULL COMMENT '所属商家',
    category_id INT NULL COMMENT '所属分类',
    name VARCHAR(100) NOT NULL COMMENT '菜品名称',
    price DECIMAL(10,2) NOT NULL COMMENT '售卖单价',
    original_price DECIMAL(10,2) COMMENT '原价（划线价）',
    image VARCHAR(200) COMMENT '菜品图片地址',
    description VARCHAR(500) COMMENT '菜品简介',
    sales INT DEFAULT 0 COMMENT '累计销量',
    status TINYINT DEFAULT 1 COMMENT '0下架 1上架在售',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品数据表';

-- 购物车
CREATE TABLE IF NOT EXISTS cart (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL COMMENT '所属用户',
    dish_id INT NOT NULL COMMENT '选购菜品',
    quantity INT NOT NULL DEFAULT 1 COMMENT '选购份数',
    add_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '加入购物车时间',
    FOREIGN KEY (user_id) REFERENCES user(person_id) ON DELETE CASCADE,
    FOREIGN KEY (dish_id) REFERENCES dish(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户购物车';

-- 订单主表
CREATE TABLE IF NOT EXISTS orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(32) NOT NULL UNIQUE COMMENT '全局唯一订单编号',
    user_id INT NOT NULL COMMENT '下单用户ID',
    restaurant_id INT NOT NULL COMMENT '下单商家ID',
    rider_id INT NULL COMMENT '接单骑手ID',
    address_id INT NOT NULL COMMENT '收货地址ID',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '商品总价',
    delivery_fee DECIMAL(10,2) DEFAULT 0 COMMENT '配送费',
    discount_amount DECIMAL(10,2) DEFAULT 0 COMMENT '优惠减免金额',
    pay_amount DECIMAL(10,2) NOT NULL COMMENT '用户实付金额',
    status TINYINT DEFAULT 0 COMMENT '0待支付,1待接单,2待取餐,3配送中,4已完成,5已取消',
    remark VARCHAR(200) COMMENT '用户下单备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
    pay_time DATETIME NULL COMMENT '支付时间',
    accept_time DATETIME NULL COMMENT '商家接单时间',
    pickup_time DATETIME NULL COMMENT '骑手取餐时间',
    delivery_time DATETIME NULL COMMENT '骑手送达时间',
    finish_time DATETIME NULL COMMENT '订单完成时间',
    cancel_time DATETIME NULL COMMENT '订单取消时间',
    FOREIGN KEY (user_id) REFERENCES user(person_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主信息';

-- 订单明细
CREATE TABLE IF NOT EXISTS order_item (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL COMMENT '归属订单',
    dish_id INT NOT NULL COMMENT '对应菜品',
    dish_name VARCHAR(100) NOT NULL COMMENT '下单时菜品名称（冗余）',
    dish_price DECIMAL(10,2) NOT NULL COMMENT '下单时单价',
    quantity INT NOT NULL COMMENT '购买份数',
    total_price DECIMAL(10,2) NOT NULL COMMENT '单品小计金额',
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单菜品明细';

-- 配送记录表
CREATE TABLE IF NOT EXISTS delivery_record (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL COMMENT '关联订单',
    rider_id INT NOT NULL COMMENT '配送骑手',
    pickup_time DATETIME NULL COMMENT '骑手到店取餐时间',
    delivery_time DATETIME NULL COMMENT '送达用户时间',
    distance DECIMAL(10,2) DEFAULT 0 COMMENT '配送里程(km)',
    status TINYINT COMMENT '配送状态',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (rider_id) REFERENCES user(person_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='骑手配送记录';

-- 创建索引
CREATE INDEX idx_user_type ON user(user_type);
CREATE INDEX idx_restaurant_user ON restaurant(user_id);
CREATE INDEX idx_order_user ON orders(user_id);
CREATE INDEX idx_order_restaurant ON orders(restaurant_id);
CREATE INDEX idx_order_status ON orders(status);
CREATE INDEX idx_dish_restaurant ON dish(restaurant_id);
CREATE INDEX idx_cart_user ON cart(user_id);
CREATE INDEX idx_address_user ON address(user_id);

-- 插入测试数据
INSERT INTO user (user_name, password, user_type, phone, avatar, balance) VALUES
('admin', '123456', 0, '13800000000', 'http://example.com/admin.jpg', 0),
('customer1', '123456', 1, '13800000001', 'http://example.com/customer1.jpg', 500.00),
('merchant1', '123456', 2, '13800000002', 'http://example.com/merchant1.jpg', 1000.00),
('rider1', '123456', 3, '13800000003', 'http://example.com/rider1.jpg', 200.00);

-- 查看创建的账号
SELECT person_id, user_name, password, user_type, phone, balance FROM user;