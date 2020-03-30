package com.chauncy.cloud.gateway.web.service.impl;

import com.chauncy.cloud.common.utils.JSONUtils;
import com.chauncy.cloud.common.utils.RedisUtil;
import com.chauncy.cloud.gateway.web.service.IRouteService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.security.auth.callback.Callback;
import java.util.*;

import static com.chauncy.cloud.common.constant.Constants.GATEWAY_ROUTES;

/**
 * @Author cheng
 * @create 2020-03-29 14:13
 *
 * 网关路由管理服务
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class RouteServiceImpl implements IRouteService {

    @Autowired
    private RedisUtil redisUtil;

    private Map<String,RouteDefinition> routeDefinitionMaps = new HashMap<>();

    //从reids获取所有路由信息
    @PostConstruct
    private void loadRouteDefinition(){
        log.info("loadRouteDefinition, 开始初使化路由");
        log.info("预计初始化路由，gatewayKeys:{}",redisUtil.hKeys(GATEWAY_ROUTES));
        Map<String, RouteDefinition> allRoutes = new HashMap<>();
        redisUtil.hGetAll(GATEWAY_ROUTES).forEach((k,v)->allRoutes.put(k,JSONUtils.toBean(v,RouteDefinition.class)));

        /*List<RouteDefinition> routeDefinitionList = Lists.newArrayList();
        redisUtil.hValues(GATEWAY_ROUTES).stream().forEach(a->{
            routeDefinitionList.add(JSONUtils.toBean(a,RouteDefinition.class));
        });*/

        routeDefinitionMaps.putAll(allRoutes);
        log.info("共初始化路由信息:{}",routeDefinitionMaps.size());
    }

    @Override
    public Collection<RouteDefinition> getRouteDefinitions() {
        return routeDefinitionMaps.values();
    }

    @Override
    public boolean save(RouteDefinition routeDefinition) {
        routeDefinitionMaps.put(routeDefinition.getId(),routeDefinition);
        log.info("新增路由1条：{},目前路由共{}条", routeDefinition, routeDefinitionMaps.size());
        return true;
    }

    @Override
    public boolean delete(String routeId) {
        routeDefinitionMaps.remove(routeId);
        log.info("删除路由1条：{},目前路由共{}条", routeId, routeDefinitionMaps.size());
        return true;
    }
}
