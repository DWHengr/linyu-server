package com.cershy.linyuserver.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("statistic")
public class Statistic implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    /**
     * 日期
     */
    @TableField("date")
    private Date date;


    /**
     * 登录数量
     */
    @TableField("login_num")
    private Integer loginNum;

    /**
     * 在线数量
     */
    @TableField("online_num")
    private Integer onlineNum;

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
