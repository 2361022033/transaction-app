-- 新建数据库
-- create
-- database book_transaction;

-- 用户信息表
drop table IF EXISTS user_info;
CREATE TABLE user_info
(
    id              bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
    user_name       VARCHAR(32)  NOT NULL COMMENT '用户名',
    password        VARCHAR(256) NOT NULL COMMENT '密码',
    nick_name       VARCHAR(32)  NOT NULL COMMENT '昵称',
    real_name       VARCHAR(32)           DEFAULT NULL COMMENT '真实姓名',
    sex             integer               DEFAULT 0 NOT NULL COMMENT '性别:0-女;1-男',
    avatar          VARCHAR(256)          DEFAULT NULL COMMENT '头像',
    introduction    VARCHAR(256)          DEFAULT NULL COMMENT '个性签名',
    balance         DECIMAL(10, 4)        DEFAULT 0 NOT NULL COMMENT '账户余额',
    bug_number      integer unsigned DEFAULT 0 NOT NULL COMMENT '已买数量',
    sold_out_number integer unsigned DEFAULT 0 NOT NULL COMMENT '已售数量',
    on_sale_number  integer unsigned DEFAULT 0 NOT NULL COMMENT '在售数量',
    create_time     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    is_deleted      tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否删除',
    PRIMARY KEY (id),
    UNIQUE KEY `idx_user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- 图书信息表
drop table IF EXISTS book_info;
CREATE TABLE book_info
(
    id               bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
    book_name        VARCHAR(256)   NOT NULL COMMENT '书名',
    writer           VARCHAR(256)   NOT NULL COMMENT '作者',
    publishing_house VARCHAR(256)            DEFAULT NULL COMMENT '出版社',
    description      VARCHAR(1000)           DEFAULT NULL COMMENT '描述',
    price_original   DECIMAL(10, 4) NOT NULL COMMENT '原价',
    price_now        DECIMAL(10, 4) NOT NULL COMMENT '在售价格',
    first_type_code  VARCHAR(32)    NOT NULL COMMENT '一级类型编码',
    first_type_name  VARCHAR(32)    NOT NULL COMMENT '一级类型名称',
    second_type_code VARCHAR(32)    NOT NULL COMMENT '二级类型编码',
    second_type_name VARCHAR(32)    NOT NULL COMMENT '二级类型名称',
    third_type_code  VARCHAR(32)    NOT NULL COMMENT '三级类型编码',
    third_type_name  VARCHAR(32)    NOT NULL COMMENT '三级类型名称',
    image_one        VARCHAR(256)            DEFAULT NULL COMMENT '图片一地址',
    image_two        VARCHAR(256)            DEFAULT NULL COMMENT '图片二地址',
    image_three      VARCHAR(256)            DEFAULT NULL COMMENT '图片三地址',
    image_four       VARCHAR(256)            DEFAULT NULL COMMENT '图片四地址',
    video            VARCHAR(256)            DEFAULT NULL COMMENT '视频地址',
    saller_id        bigint unsigned NOT NULL COMMENT '卖家id',
    browse_number    integer unsigned DEFAULT 0 NOT NULL COMMENT '浏览数',
    favorite_number  integer unsigned DEFAULT 0 NOT NULL COMMENT '收藏数',
    status           integer unsigned DEFAULT 1 NOT NULL COMMENT '状态:1(在售),0(已售)',
    is_deleted       tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否删除',
    create_time      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书信息表';


-- 图书类型配置表
drop table IF EXISTS book_type_config;
CREATE TABLE book_type_config
(
    id               bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
    first_type_code  VARCHAR(32) NOT NULL COMMENT '一级类型编码',
    first_type_name  VARCHAR(32) NOT NULL COMMENT '一级类型名称',
    second_type_code VARCHAR(32) NOT NULL COMMENT '二级类型编码',
    second_type_name VARCHAR(32) NOT NULL COMMENT '二级类型名称',
    third_type_code  VARCHAR(32) NOT NULL COMMENT '三级类型编码',
    third_type_name  VARCHAR(32) NOT NULL COMMENT '三级类型名称',
    create_time      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    is_deleted       tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否删除',
    PRIMARY KEY (id),
    UNIQUE KEY `idx_third_type_code` (`third_type_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书类型配置表';


-- 订单表
drop table IF EXISTS book_order;
CREATE TABLE book_order
(
    id          bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK订单号',
    book_id     bigint unsigned NOT NULL COMMENT '图书商品编号',
    price_now   DECIMAL(10, 4) NOT NULL COMMENT '售价',
    price_now   DECIMAL(10, 4) NOT NULL COMMENT '最终金额',
    buyer_id    bigint unsigned NOT NULL COMMENT '买家id',
    saller_id   bigint unsigned NOT NULL COMMENT '卖家id',
    voucher_id  bigint unsigned COMMENT '购物券ID',
    status      integer unsigned DEFAULT 1 NOT NULL COMMENT '状态:待支付/已支付/手动取消/超时',
    out_time    DATETIME                DEFAULT NULL COMMENT '超时时间',
    create_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 物流表