package com.chauncy.cloud.data.domain.po.gateway;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.chauncy.cloud.data.domain.po.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 网关路由表
 * </p>
 *
 * @author admin
 * @since 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("gateway_route")
@ApiModel(value = "GatewayRoutePo对象", description = "网关路由表")
@NoArgsConstructor
@AllArgsConstructor
public class GatewayRoutePo extends BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "路由id")
    private String routeId;

    @ApiModelProperty(value = "uri路径")
    private String uri;

    @ApiModelProperty(value = "判定器")
    private String predicates;

    @ApiModelProperty(value = "过滤器")
    private String filters;

    @ApiModelProperty(value = "排序")
    private Integer orders;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态：Y-有效，N-无效")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "更新人")
    private String updatedBy;


}
