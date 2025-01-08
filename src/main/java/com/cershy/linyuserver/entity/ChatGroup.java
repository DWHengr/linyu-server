package com.cershy.linyuserver.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 聊天群表
 * </p>
 *
 * @author heath
 * @since 2024-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "chat_group", autoResultMap = true)
public class ChatGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    /**
     * 群号
     */
    @TableField("chat_group_number")
    private String chatGroupNumber;

    /**
     * 创建用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 群主id
     */
    @TableField("owner_user_id")
    private String ownerUserId;

    /**
     * 群头像
     */
    @TableField("portrait")
    private String portrait;

    /**
     * 群名名称
     */
    @TableField("name")
    private String name;

    /**
     * 群公告
     */
    @TableField(value = "notice", typeHandler = JacksonTypeHandler.class)
    private ChatGroupNotice notice;

    /**
     * 成员数
     */
    @TableField("member_num")
    private Integer memberNum;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private String groupRemark;
}
