package com.cershy.linyuserver.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.cershy.linyuserver.entity.ext.MsgContent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 聊天列表
 * </p>
 *
 * @author heath
 * @since 2024-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "chat_list", autoResultMap = true)
public class ChatList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 会话目标id
     */
    @TableField("from_id")
    private String fromId;

    /**
     * 是否置顶
     */
    @TableField("is_top")
    private Boolean isTop;

    /**
     * 最后消息内容
     */
    @TableField(value = "last_msg_content", typeHandler = JacksonTypeHandler.class)
    private MsgContent lastMsgContent;


    /**
     * 未读消息数
     */
    @TableField("unread_num")
    private Integer unreadNum;


    /**
     * 状态
     */
    @TableField("status")
    private String status;

    /**
     * 类型
     */
    @TableField("`type`")
    private String type;

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
    private String chatBackground;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String remark;

    @TableField(exist = false)
    private String portrait;
}
