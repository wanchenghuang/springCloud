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
 * 资源表
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("resource")
@ApiModel(value = "ResourcePo对象", description = "资源表")
public class ResourcePo extends BasePo {

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


}
