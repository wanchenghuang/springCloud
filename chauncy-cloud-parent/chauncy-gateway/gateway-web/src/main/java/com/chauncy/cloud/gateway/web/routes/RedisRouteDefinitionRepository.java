package com.chauncy.cloud.gateway.web.routes;

import com.chauncy.cloud.common.constant.Constants;
import com.chauncy.cloud.common.utils.JSONUtils;
import com.chauncy.cloud.common.utils.RedisUtil;
import com.chauncy.cloud.gateway.web.service.IRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Author cheng
 * @create 2020-03-29 14:06
 *
 * 自定义初始化路由信息到RouteDefinition，默认是InMemoryRouteDefinitionRepository
 */
@Component
@Slf4j
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    @Autowired
    private IRouteService routeService;

    @Autowired
    private RedisUtil redisUtils;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
//        List<String> routes = redisUtils.hValues(Constants.GATEWAY_ROUTES);
//        return Flux.fromStream(
//                routes.stream().map(r -> JSONUtils.toBean(r, RouteDefinition.class))
//        );
        return Flux.fromIterable(routeService.getRouteDefinitions());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(routeDefinition -> {
            routeService.save(routeDefinition);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            routeService.delete(id);
            return Mono.empty();
        });
    }
}
