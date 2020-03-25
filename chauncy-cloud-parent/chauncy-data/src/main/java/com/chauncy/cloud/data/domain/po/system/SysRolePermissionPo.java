package com.chauncy.cloud.data.domain.po.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.chauncy.cloud.data.domain.po.BasePo;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色权限关系表
 * </p>
 *
 * @author admin
 * @since 2020-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role_permission")
@ApiModel(value = "SysRolePermissionPo对象", description = "角色权限关系表")
public class SysRolePermissionPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "删除标志 默认0")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "角色id")
    private Long permissionId;

    @ApiModelProperty(value = "权限id")
    private Long roleId;


}
