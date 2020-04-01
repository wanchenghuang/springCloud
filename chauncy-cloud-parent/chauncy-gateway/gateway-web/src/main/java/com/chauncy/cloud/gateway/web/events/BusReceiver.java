package com.chauncy.cloud.gateway.web.events;

import com.chauncy.cloud.gateway.web.service.IRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

/**
 * @Author cheng
 * @create 2020-03-30 23:22
 */
//MessageListenerAdapter messageListenerAdapter(BusReceiver busReceiver, MessageConverter messageConverter)
//将BusReceiver作为一个对象传给MessageListenerAdapter,handleMessage为MessageListenerAdapter处理消息的方法
//适配器模式
@Component
@Slf4j
public class BusReceiver {

    @Autowired
    private IRouteService routeService;

    public void handleMessage(RouteDefinition routeDefinition) {
        log.info("Received Message:<{}>", routeDefinition);
        // 动态添加路由
        //待实现动态删除路由/修改路由
        routeService.save(routeDefinition);

        //动态刷新，影响效率
//        routeService.refresh();

    }
}
