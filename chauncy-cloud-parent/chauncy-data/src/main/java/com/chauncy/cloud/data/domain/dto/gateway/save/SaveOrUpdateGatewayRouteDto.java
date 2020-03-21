package com.chauncy.cloud.data.domain.dto.gateway.save;

import com.chauncy.cloud.data.domain.dto.base.BaseDto;
import com.chauncy.cloud.data.domain.po.gateway.GatewayRoutePo;
import com.chauncy.cloud.data.valid.NeedExistConstraint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author cheng
 * @create 2020-03-19 21:26
 */
@ApiModel(description = "保存网关路由")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class SaveOrUpdateGatewayRouteDto extends BaseDto<GatewayRoutePo> {

    @NotEmpty(message = "网关断言不能为空")
    @ApiModelProperty(value = "网关断言")
    private List<PredicateDefinitionDto> predicates;

    @ApiModelProperty(value = "网关过滤器信息")
    private List<FilterDefinitionDto> filters;

    @NotBlank(message = "uri不能为空")
    @ApiModelProperty(value = "网关uri")
//    @NeedExistConstraint(tableName = "gateway_route",isNeedExists = false,field = "uri",message = "网关uri不能重复")
    private String uri;

    @NotBlank(message = "路由id不能为空")
    @ApiModelProperty(value = "网关路由id")
//    @NeedExistConstraint(tableName = "gateway_route",isNeedExists = false,field = "route_id",message = "网关路由id不能重复")
    private String routeId;

    @ApiModelProperty(value = "排序")
    private Integer orders = 0;

    @ApiModelProperty(value = "网关路由描述信息")
    private String description;

    @Override
    public GatewayRoutePo toPo(Class<GatewayRoutePo> clazz) {
        GatewayRoutePo gatewayRoute = new GatewayRoutePo();
        BeanUtils.copyProperties(this, gatewayRoute);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //将对象转为json字符串
            gatewayRoute.setFilters(objectMapper.writeValueAsString(this.getFilters()));
            gatewayRoute.setPredicates(objectMapper.writeValueAsString(this.getPredicates()));
        } catch (JsonProcessingException e) {
            log.error("网关filter或predicates配置json转换异常", e);
        }
        return gatewayRoute;
    }

    @Override
    public GatewayRoutePo toPo(Long id, Class<GatewayRoutePo> clazz) {
        GatewayRoutePo gatewayRoute = new GatewayRoutePo();
        BeanUtils.copyProperties(this, gatewayRoute);
        gatewayRoute.setId(id);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //将对象转为json字符串
            gatewayRoute.setFilters(objectMapper.writeValueAsString(this.getFilters()));
            gatewayRoute.setPredicates(objectMapper.writeValueAsString(this.getPredicates()));
        } catch (JsonProcessingException e) {
            log.error("网关filter或predicates配置json转换异常", e);
        }
        return gatewayRoute;
    }
}
