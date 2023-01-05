CREATE TABLE junfukt_user
(
    id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'PK',
    user_id VARCHAR(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
    staff_no VARCHAR(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '工号',
    real_name VARCHAR(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '真实姓名',
    nick_name VARCHAR(32) COLLATE utf8mb4_general_ci COMMENT '昵称',
    avatar VARCHAR(256) COLLATE utf8mb4_general_ci COMMENT '头像',
    introduction VARCHAR(128) COLLATE utf8mb4_general_ci COMMENT '个性签名',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '修改时间',
    PRIMARY KEY (id),
    CONSTRAINT uk_user_id UNIQUE (user_id)
)
    ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

