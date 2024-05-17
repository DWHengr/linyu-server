DROP TABLE if EXISTS `user`;
CREATE TABLE `user`
(
    `id`            varchar(64)  NOT NULL,
    `account`       varchar(64)  NOT NULL COMMENT '用户账号',
    `name`          varchar(200) NOT NULL COMMENT '用户名',
    `portrait`      text         default NULL COMMENT '头像',
    `password`      varchar(200) NOT NULL COMMENT '密码',
    `sex`           varchar(64)  default NULL COMMENT '性别',
    `phone`         varchar(64)  default NULL COMMENT '手机号',
    `email`         varchar(200) default NULL COMMENT '邮箱',
    `last_opt_time` datetime     default NULL COMMENT '最后操作时间',
    `status`        varchar(500) COMMENT '用户状态',
    `create_time`   datetime     NOT NULL 0 COMMENT '创建时间',
    `update_time`   datetime     NOT NULL 0 COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表' row_format=dynamic;


DROP TABLE if EXISTS `message`;
CREATE TABLE `message`
(
    `id`          varchar(64) NOT NULL,
    `from_id`     varchar(64) NOT NULL COMMENT '消息发送方id',
    `to_id`       varchar(64) NOT NULL COMMENT '消息接受方id',
    `type`        varchar(64) NOT NULL COMMENT '消息类型',
    `msg_content` text default NULL COMMENT '消息内容',
    `status`      varchar(500) COMMENT '消息状态',
    `create_time` datetime    NOT NULL 0 COMMENT '创建时间',
    `update_time` datetime    NOT NULL 0 COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表' row_format=dynamic;