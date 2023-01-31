-- 新建数据库
create
database book_transaction;

-- 用户信息表
CREATE TABLE
    user_info
(
    id              bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
    user_name       VARCHAR(32)                        NOT NULL COMMENT '用户名',
    password        VARCHAR(256)                       NOT NULL COMMENT '密码',
    real_name       VARCHAR(32) COMMENT '真实姓名',
    sex             bigint   DEFAULT 0                 NOT NULL COMMENT '性别',
    nick_name       VARCHAR(32)                        NOT NULL COMMENT '昵称',
    avatar          VARCHAR(256) COMMENT '头像',
    introduction    VARCHAR(256) COMMENT '个性签名',
    balance         DECIMAL  DEFAULT 0                 NOT NULL COMMENT '账户余额',
    bug_number      bigint unsigned DEFAULT 0 NOT NULL COMMENT '已买数量',
    sold_out_number bigint unsigned DEFAULT 0 NOT NULL COMMENT '已售数量',
    on_sale_number  bigint unsigned DEFAULT 0 NOT NULL COMMENT '在售数量',
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '修改时间',
    is_deleted      tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否删除',
    PRIMARY KEY (id),
    CONSTRAINT uk_user_name UNIQUE (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- 图书信息表
CREATE TABLE book_info
(
    id               bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
    book_name        VARCHAR(256)   NOT NULL COMMENT '书名',
    writer           VARCHAR(256)   NOT NULL COMMENT '作者',
    publishing_house VARCHAR(256) COMMENT '出版社',
    description      VARCHAR(1000) COMMENT '描述',
    price_original   DECIMAL(10, 4) NOT NULL COMMENT '原价',
    price_now        DECIMAL(10, 4) NOT NULL COMMENT '在售价格',
    type_first       VARCHAR(32)    NOT NULL COMMENT '一级类型',
    type_second      VARCHAR(32)    NOT NULL COMMENT '二级类型',
    image_one        VARCHAR(256) COMMENT '图片一地址',
    image_two        VARCHAR(256) COMMENT '图片二地址',
    image_three      VARCHAR(256) COMMENT '图片三地址',
    image_four       VARCHAR(256) COMMENT '图片四地址',
    video            VARCHAR(256) COMMENT '视频地址',
    saller_id        bigint unsigned NOT NULL COMMENT '卖家id',
    status           bigint unsigned DEFAULT 1 NOT NULL COMMENT '状态:1(在售),0(已售)',
    is_deleted       tinyint unsigned DEFAULT 0 NOT NULL COMMENT '是否删除',
    create_time      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书信息表';