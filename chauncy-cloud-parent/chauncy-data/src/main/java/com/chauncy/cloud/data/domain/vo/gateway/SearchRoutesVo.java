package com.chauncy.cloud.data.domain.vo.gateway;

import com.alibaba.fastjson.annotation.JSONField;
import com.chauncy.cloud.data.domain.dto.gateway.save.FilterDefinitionDto;
import com.chauncy.cloud.data.domain.dto.gateway.save.PredicateDefinitionDto;
import com.chauncy.cloud.data.domain.po.gateway.GatewayRoutePo;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author cheng
 * @create 2020-03-21 13:09
 */
@Data
@Slf4j
@ApiModel(description = "路由信息")
public class SearchRoutesVo {

    @ApiModelProperty(value = "路由主键ID")
    private Long id;

    @ApiModelProperty(value = "路由id")
    private String routeId;

    @ApiModelProperty(value = "uri路径")
    private String uri;

    @ApiModelProperty(value = "判定器",hidden = true)
    @JSONField(serialize = false)
    private String predicates;

    @ApiModelProperty(value = "过滤器",hidden = true)
    @JSONField(serialize = false)
    private String filters;

    @ApiModelProperty(value = "判定器")
    private List<PredicateDefinitionDto> predicateList = new ArrayList<>();

    @ApiModelProperty(value = "过滤器")
    private List<FilterDefinitionDto> filterList = new ArrayList<>();

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

    /*public SearchRoutesVo(GatewayRoutePo gatewayRoute){

        this.id = gatewayRoute.getId();
        this.routeId = gatewayRoute.getRouteId();
        this.uri = gatewayRoute.getUri();
        this.description = gatewayRoute.getDescription();
        this.status = gatewayRoute.getStatus();
        this.orders = gatewayRoute.getOrders();
        this.createdBy = gatewayRoute.getCreatedBy();
        this.createdTime = gatewayRoute.getCreatedTime();
        this.updatedBy = gatewayRoute.getUpdatedBy();
        this.updatedTime = gatewayRoute.getUpdatedTime();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.filterList = objectMapper.readValue(gatewayRoute.getFilters(), new TypeReference<List<FilterDefinitionDto>>() {
            });
            this.predicateList = objectMapper.readValue(gatewayRoute.getPredicates(), new TypeReference<List<PredicateDefinitionDto>>() {
            });
        }catch (IOException e){
            log.error("网关路由对象转换失败", e);
        }
    }*/
}
