//package com.chauncy.cloud.gateway.web.routes;
//
//import com.chauncy.cloud.gateway.web.service.IRouteService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.connection.MessageListener;
//
///**
// * @Author cheng
// * @create 2020-03-31 21:54
// *
// * redis 路由动态更新监听===不生效
// */
//@Slf4j
//public class RedisRouteRefreshListener implements MessageListener {
//
//    private IRouteService routeService;
//
//    //构造函数注入
//    public RedisRouteRefreshListener(IRouteService routeService) {
//        this.routeService = routeService;
//    }
//
//    @Override
//    public void onMessage(Message message, byte[] bytes) {
//        log.info("receive message <{}>", new String(message.getBody()));
//        routeService.refresh();
//
//    }
//}
