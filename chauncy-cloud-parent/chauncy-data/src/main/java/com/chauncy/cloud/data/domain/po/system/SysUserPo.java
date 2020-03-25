package com.chauncy.cloud.data.domain.po.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.chauncy.cloud.data.domain.po.BasePo;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author admin
 * @since 2020-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value = "SysUserPo对象", description = "用户表")
public class SysUserPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "省市县地址")
    private String address;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "描述/详情/备注")
    private String description;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别 0-女 1-男 ")
    private Boolean sex;

    @ApiModelProperty(value = "状态 默认1正常 0拉黑")
    private Boolean status;

    @ApiModelProperty(value = "用户类型 0普通用户 1管理员")
    private Integer type;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "删除标志 默认0")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "所属部门id")
    private Long departmentId;

    @ApiModelProperty(value = "街道地址")
    private String street;

    @ApiModelProperty(value = "密码强度")
    private String passStrength;

    @ApiModelProperty(value = "最后修改密码的日期")
    private LocalDateTime lastPasswordResetTime;

    @ApiModelProperty(value = "系统用户类型 1->平台后台 2->其他 默认1")
    private Integer systemType;


}
