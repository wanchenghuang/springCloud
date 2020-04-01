package com.chauncy.cloud.gateway.web.service.impl;

import com.chauncy.cloud.common.utils.JSONUtils;
import com.chauncy.cloud.common.utils.RedisUtil;
import com.chauncy.cloud.gateway.web.service.IRouteService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
public class RouteServiceImpl implements IRouteService,ApplicationEventPublisherAware {

    @Autowired
    private RedisUtil redisUtil;

    private Map<String,RouteDefinition> routeDefinitionMaps = new HashMap<>();

    private ApplicationEventPublisher publisher;


    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher publisher) {
        Assert.notNull(publisher, "publisher may not be null");
        this.publisher = publisher;
    }

    @Override
    public void refresh() {
        //这个this.publisher.publishEvent(new RefreshRoutesEvent(this));是spring 自带的方法
        //执行这个方法的瞬间会去找所有继承了RouteDefinitionRepository接口的类中的getRouteDefinitions方法
        //所以本项目执行的是RedisRouteDefinitionRepository.getRouteDefinitions
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        log.info("refresh the route...");
    }

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
        //影响效率
//        Map<String, RouteDefinition> allRoutes = new HashMap<>();
//        redisUtil.hGetAll(GATEWAY_ROUTES).forEach((k,v)->allRoutes.put(k,JSONUtils.toBean(v,RouteDefinition.class)));
//        return allRoutes.values();
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
