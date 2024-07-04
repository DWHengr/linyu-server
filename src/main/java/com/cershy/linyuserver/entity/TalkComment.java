package com.cershy.linyuserver.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 说说评论
 * </p>
 *
 * @author heath
 * @since 2024-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("talk_comment")
public class TalkComment implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId("id")
    private String id;

    /**
     * 说说id
     */
    @TableField("talk_id")
    private String talkId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

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


}
