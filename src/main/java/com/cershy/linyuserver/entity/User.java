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
 * 用户表
 * </p>
 *
 * @author heath
 * @since 2024-05-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    /**
     * 用户账号
     */
    @TableField("account")
    private String account;

    /**
     * 用户名
     */
    @TableField("name")
    private String name;

    /**
     * 头像
     */
    @TableField("portrait")
    private String portrait;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别
     */
    @TableField("sex")
    private String sex;

    /**
     * 生日
     */
    @TableField("birthday")
    private Date birthday;

    /**
     * 签名
     */
    @TableField("signature")
    private String signature;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 最后操作时间
     */
    @TableField("last_opt_time")
    private Date lastOptTime;

    /**
     * 用户状态
     */
    @TableField("status")
    private String status;

    /**
     * 是否在线
     */
    @TableField("is_online")
    private Boolean isOnline;

    /**
     * 用户角色
     */
    @TableField("role")
    private String role;


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
     * 在线设备
     */
    @TableField("online_equipment")
    private String onlineEquipment;

}
