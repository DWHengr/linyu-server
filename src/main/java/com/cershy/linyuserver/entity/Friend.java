package com.cershy.linyuserver.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 好友表
 * </p>
 *
 * @author heath
 * @since 2024-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("friend")
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 好友id
     */
    @TableField("friend_id")
    private String friendId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 分组id
     */
    @TableField("group_id")
    private String groupId;

    /**
     * 是否拉黑
     */
    @TableField("is_back")
    private Boolean isBack;

    /**
     * 是否特别关心
     */
    @TableField("is_concern")
    private Boolean isConcern;

    /**
     * 状态
     */
    @TableField("status")
    private String status;

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

    /**
     * 聊天背景
     */
    @TableField("chat_background")
    private String chatBackground;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String portrait;

    @TableField(exist = false)
    private String account;

    @TableField(exist = false)
    private String unreadNum;
}
