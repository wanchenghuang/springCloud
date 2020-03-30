package com.chauncy.cloud.gateway.web.service;

import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.Collection;

/**
 * @Author cheng
 * @create 2020-03-29 14:11
 *
 * 网关服务接口
 */
public interface IRouteService {

    /**
     * @Author chauncy
     * @Date 2020-03-29 14:12
     * @param
     * @return
     *
     * 获取所有的路由定义信息
     **/
    Collection<RouteDefinition> getRouteDefinitions();

    /**
     * @Author chauncy
     * @Date 2020-03-29 14:13
     * @param  routeDefinition
     * @return
     *       保存路由定义信息
     **/
    boolean save(RouteDefinition routeDefinition);

    /**
     * @Author chauncy
     * @Date 2020-03-29 14:13
     * @param  routeId
     * @return
     *  根据路由Id删除
     **/
    boolean delete(String routeId);


}
