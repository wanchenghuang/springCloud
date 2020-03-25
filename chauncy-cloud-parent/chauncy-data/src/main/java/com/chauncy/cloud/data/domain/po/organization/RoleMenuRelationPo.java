package com.chauncy.cloud.data.domain.po.organization;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.chauncy.cloud.data.domain.po.BasePo;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色和菜单关系表
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("role_menu_relation")
@ApiModel(value = "RoleMenuRelationPo对象", description = "角色和菜单关系表")
public class RoleMenuRelationPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单id")
    private String menuId;

    @ApiModelProperty(value = "角色id")
    private String roleId;


}
