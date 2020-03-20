package com.chauncy.cloud.gateway.admin.service.impl;

import com.chauncy.cloud.common.utils.RedisUtil;
import com.chauncy.cloud.data.domain.dto.gateway.SaveOrUpdateGatewayRouteDto;
import com.chauncy.cloud.data.domain.po.gateway.GatewayRoutePo;
import com.chauncy.cloud.data.mapper.gateway.GatewayRouteMapper;
import com.chauncy.cloud.gateway.admin.service.IGatewayRouteService;
import com.chauncy.cloud.core.config.base.AbstractService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * <p>
 * 网关路由表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-03-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class GatewayRouteServiceImpl extends AbstractService<GatewayRouteMapper, GatewayRoutePo> implements IGatewayRouteService {

    @Autowired
    private GatewayRouteMapper mapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean saveRoute(SaveOrUpdateGatewayRouteDto saveOrUpdateGatewayRoute) {

        GatewayRoutePo gatewayRoute = saveOrUpdateGatewayRoute.toPo(GatewayRoutePo.class);
        boolean isSuccess = this.save(gatewayRoute);
        RouteDefinition routeDefinition = gatewayRouteToRouteDefinition(gatewayRoute);
        // 转化为gateway需要的类型，缓存到redis, 并事件通知各网关应用
        redisUtil.set(gatewayRoute.getRouteId(),routeDefinition);

        return isSuccess;
    }

    /**
     * 将数据库中json对象转换为网关需要的RouteDefinition对象
     *
     * @param gatewayRoute
     * @return RouteDefinition
     */
    private RouteDefinition gatewayRouteToRouteDefinition(GatewayRoutePo gatewayRoute) {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gatewayRoute.getRouteId());
        routeDefinition.setOrder(gatewayRoute.getOrders());
        routeDefinition.setUri(URI.create(gatewayRoute.getUri()));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            routeDefinition.setFilters(objectMapper.readValue(gatewayRoute.getFilters(), new TypeReference<List<FilterDefinition>>() {
            }));
            routeDefinition.setPredicates(objectMapper.readValue(gatewayRoute.getPredicates(), new TypeReference<List<PredicateDefinition>>() {
            }));
        } catch (IOException e) {
            log.error("网关路由对象转换失败", e);
        }
        return routeDefinition;
    }
}
