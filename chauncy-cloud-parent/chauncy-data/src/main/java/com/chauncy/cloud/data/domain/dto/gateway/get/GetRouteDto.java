package com.chauncy.cloud.data.domain.dto.gateway.get;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author cheng
 * @create 2020-03-21 23:59
 */
@ApiModel(description = "条件查询网关信息")
@Data
public class GetRouteDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "uri")
    private String uri;

    @ApiModelProperty(value = "路由ID")
    private String routeId;
}
