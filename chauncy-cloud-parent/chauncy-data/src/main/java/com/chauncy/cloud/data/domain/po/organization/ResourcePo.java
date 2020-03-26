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
 * 资源表
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@Data
@Accessors(chain = true)
@TableName("resource")
@ApiModel(value = "ResourcePo对象", description = "资源表")
public class ResourcePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源code")
    private String code;

    @ApiModelProperty(value = "资源类型")
    private String type;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "资源url")
    private String url;

    @ApiModelProperty(value = "资源方法")
    private String method;

    @ApiModelProperty(value = "简介")
    private String description;

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
