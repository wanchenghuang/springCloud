package com.chauncy.cloud.data.domain.po.organization;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@Data
@Accessors(chain = true)
@TableName("users")
@ApiModel(value = "UsersPo对象", description = "用户表")
public class UsersPo implements Serializable {

    private static final long serialVersionUID = 220401161187822517L;

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @ApiModelProperty(value = "更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户密码密文")
    private String password;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "用户手机")
    private String mobile;

    @ApiModelProperty(value = "简介")
    private String description;

    @ApiModelProperty(value = "是否已删除Y：已删除，N：未删除")
    @TableLogic
    private String deleted;

    @ApiModelProperty(value = "是否有效用户")
    private Boolean enabled;

    @ApiModelProperty(value = "账号是否未过期")
    private Boolean accountNonExpired;

    @ApiModelProperty(value = "密码是否未过期")
    private Boolean credentialsNonExpired;

    @ApiModelProperty(value = "是否未锁定")
    private Boolean accountNonLocked;

    @TableField(exist = false)
    private Set<String> roleIds;


}
