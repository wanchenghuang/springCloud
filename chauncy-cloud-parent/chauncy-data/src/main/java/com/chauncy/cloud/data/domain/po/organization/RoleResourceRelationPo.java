package com.chauncy.cloud.data.domain.po.organization;

import com.baomidou.mybatisplus.annotation.*;
import com.chauncy.cloud.data.domain.po.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 角色和资源关系表
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@Data
@Accessors(chain = true)
@TableName("role_resource_relation")
@ApiModel(value = "RoleResourceRelationPo对象", description = "角色和资源关系表")
public class RoleResourceRelationPo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private String resourceId;

    @ApiModelProperty(value = "资源id")
    private String roleId;

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
}
