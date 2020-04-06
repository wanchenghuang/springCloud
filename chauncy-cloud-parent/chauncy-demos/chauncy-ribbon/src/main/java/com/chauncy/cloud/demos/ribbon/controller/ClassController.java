package com.chauncy.cloud.demos.ribbon.controller;

import com.chauncy.cloud.common.base.Result;
import com.chauncy.cloud.demos.ribbon.lb.LoadBalancer;
import com.chauncy.cloud.demos.ribbon.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * @Author cheng
 * @create 2020-04-06 13:47
 */
@RestController
public class ClassController {

    @Autowired
    ClassService classService;

    @Autowired
    LoadBalancer loadBalancer;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/users")
    public Result getUsers() {
        return classService.getUsers();
    }

    /**
     * @Author chauncy
     * @Date 2020-04-06 17:58
     * @param
     * @return
     *       测试自定义负载均衡
     *
     *        rest接口第几次请求数%服务器集群总数量 = 实际调用服务器位置的下标，每次服务器重启后rest接口计数从1开始
     **/
    @GetMapping("/web/lb")
    public String getWebLb() {
        List<ServiceInstance> instances = discoveryClient.getInstances("web-test");

        if (instances == null || instances.size() <= 0) {
            return null;
        }
        ServiceInstance serviceInstance = loadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();

        return restTemplate.getForObject(uri+"/tb-user-po/web/lb",String.class);

    }
}
