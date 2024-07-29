DROP TABLE if EXISTS `user`;
CREATE TABLE `user`
(
    `id`            varchar(64)  NOT NULL,
    `account`       varchar(64)  NOT NULL COMMENT '用户账号',
    `name`          varchar(200) NOT NULL COMMENT '用户名',
    `portrait`      text         default NULL COMMENT '头像',
    `password`      varchar(200) NOT NULL COMMENT '密码',
    `sex`           varchar(64)  default NULL COMMENT '性别',
    `birthday`      timestamp(3) default NULL COMMENT '生日',
    `signature`     text         default NULL COMMENT '签名',
    `phone`         varchar(64)  default NULL COMMENT '手机号',
    `email`         varchar(200) default NULL COMMENT '邮箱',
    `last_opt_time` timestamp(3) default NULL COMMENT '最后操作时间',
    `status`        varchar(500) COMMENT '用户状态',
    `create_time`   timestamp(3) NOT NULL COMMENT '创建时间',
    `update_time`   timestamp(3) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表' row_format=dynamic;


DROP TABLE if EXISTS `message`;
CREATE TABLE `message`
(
    `id`           varchar(64)  NOT NULL,
    `from_id`      varchar(64)  NOT NULL COMMENT '消息发送方id',
    `to_id`        varchar(64)  NOT NULL COMMENT '消息接受方id',
    `type`         varchar(64) default NULL COMMENT '消息类型',
    `is_show_time` bit         default 0 COMMENT '是否显示时间',
    `msg_content`  text        default NULL COMMENT '消息内容',
    `status`       varchar(500) COMMENT '消息状态',
    `create_time`  timestamp(3) NOT NULL COMMENT '创建时间',
    `update_time`  timestamp(3) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表' row_format=dynamic;

DROP TABLE if EXISTS `message_retraction`;
CREATE TABLE `message_retraction`
(
    `id`          varchar(64)  NOT NULL,
    `msg_id`      varchar(64)  NOT NULL COMMENT '消息id',
    `msg_content` text default NULL COMMENT '消息内容',
    `create_time` timestamp(3) NOT NULL COMMENT '创建时间',
    `update_time` timestamp(3) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息撤回内容表' row_format=dynamic;

DROP TABLE if EXISTS `group`;
CREATE TABLE `group`
(
    `id`              varchar(64)  NOT NULL,
    `user_id`         varchar(64)  NOT NULL COMMENT '用户id',
    `name`            varchar(64) default NULL COMMENT '分组名称',
    `parent_group_id` varchar(64) default NULL COMMENT '父分组id',
    `create_time`     timestamp(3) NOT NULL COMMENT '创建时间',
    `update_time`     timestamp(3) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分组表' row_format=dynamic;

DROP TABLE if EXISTS `friend`;
CREATE TABLE `friend`
(
    `id`          varchar(64)  NOT NULL,
    `user_id`     varchar(64)  NOT NULL COMMENT '用户id',
    `friend_id`   varchar(64)  NOT NULL COMMENT '好友id',
    `remark`      varchar(64) default NULL COMMENT '备注',
    `group_id`    varchar(64) default '0' COMMENT '分组id',
    `is_back`     bit         default 0 NULL COMMENT '是否拉黑',
    `is_concern`  bit         default 0 COMMENT '是否特别关心',
    `status`      varchar(500) COMMENT '状态',
    `create_time` timestamp(3) NOT NULL COMMENT '创建时间',
    `update_time` timestamp(3) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='好友表' row_format=dynamic;

DROP TABLE if EXISTS `chat_list`;
CREATE TABLE `chat_list`
(
    `id`               varchar(64)  NOT NULL,
    `user_id`          varchar(64)  NOT NULL COMMENT '用户id',
    `from_id`          varchar(64)  NOT NULL COMMENT '会话目标id',
    `is_top`           bit  default 0 NULL COMMENT '是否置顶',
    `unread_num`       int  default 0 COMMENT '未读消息数量',
    `last_msg_content` text default null COMMENT '最后消息内容',
    `status`           varchar(500) COMMENT '状态',
    `create_time`      timestamp(3) NOT NULL COMMENT '创建时间',
    `update_time`      timestamp(3) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天列表' row_format=dynamic;

DROP TABLE if EXISTS `notify`;
CREATE TABLE `notify`
(
    `id`          varchar(64)  NOT NULL,
    `form_id`     varchar(64)  NOT NULL COMMENT '发送方',
    `to_id`       varchar(64)  NOT NULL COMMENT '目标方',
    `type`        varchar(64)  default NULL COMMENT '类型',
    `status`      varchar(64) COMMENT '状态',
    `content`     text         default NULL COMMENT '通知内容',
    `unread_id`   varchar(128) default NULL COMMENT '未读方',
    `create_time` timestamp(3) NOT NULL COMMENT '创建时间',
    `update_time` timestamp(3) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知' row_format=dynamic;

DROP TABLE if EXISTS `talk`;
CREATE TABLE `talk`
(
    `id`             varchar(64)  NOT NULL,
    `user_id`        varchar(64)  NOT NULL COMMENT '用户id',
    `content`        text default NULL COMMENT '说说内容',
    `like_num`       int  default 0 COMMENT '点赞数量',
    `comment_num`    int  default 0 COMMENT '评论数量',
    `latest_comment` text default NULL COMMENT '最近的评论内容',
    `status`         varchar(64) COMMENT '状态',
    `create_time`    timestamp(3) NOT NULL COMMENT '创建时间',
    `update_time`    timestamp(3) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='说说' row_format=dynamic;

DROP TABLE if EXISTS `talk_like`;
CREATE TABLE `talk_like`
(
    `id`          varchar(64)  NOT NULL,
    `talk_id`     varchar(64)  NOT NULL COMMENT '说说id',
    `user_id`     varchar(64)  NOT NULL COMMENT '用户id',
    `status`      varchar(64) COMMENT '状态',
    `create_time` timestamp(3) NOT NULL COMMENT '创建时间',
    `update_time` timestamp(3) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='说说点赞' row_format=dynamic;

DROP TABLE if EXISTS `talk_comment`;
CREATE TABLE `talk_comment`
(
    `id`          varchar(64)  NOT NULL,
    `talk_id`     varchar(64)  NOT NULL COMMENT '说说id',
    `user_id`     varchar(64)  NOT NULL COMMENT '用户id',
    `content`     text default NULL COMMENT '评论内容',
    `status`      varchar(64) COMMENT '状态',
    `create_time` timestamp(3) NOT NULL COMMENT '创建时间',
    `update_time` timestamp(3) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='说说评论' row_format=dynamic;

DROP TABLE if EXISTS `talk_permission`;
CREATE TABLE `talk_permission`
(
    `id`          varchar(64)  NOT NULL,
    `talk_id`     varchar(64)  NOT NULL COMMENT '说说id',
    `permission`  varchar(64) default 'all' COMMENT '权限:用户id,all',
    `status`      varchar(64) COMMENT '状态',
    `create_time` timestamp(3) NOT NULL COMMENT '创建时间',
    `update_time` timestamp(3) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='说说查看权限' row_format=dynamic;


DROP TABLE if EXISTS `user_set`;
CREATE TABLE `user_set`
(
    `id`          varchar(64)  NOT NULL,
    `user_id`     varchar(64)  NOT NULL COMMENT '用户id',
    `sets`        text default NULL COMMENT '用户设置',
    `create_time` timestamp(3) NOT NULL COMMENT '创建时间',
    `update_time` timestamp(3) NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户设置表' row_format=dynamic;


insert into `notify` (`id`, `from_id`, `to_id`, `type`, `status`, `content`, `unread_id`, `create_time`, `update_time`)
values ('1', '0', '0', 'system', NULL,
        '{\"img\":\"http://139.196.241.208:9000/linyu/notify/1.png\",\"text\":\"欢迎使用林语~\"}', NULL,
        '2024-07-24 11:33:05.000', '2024-07-24 11:33:05.000');