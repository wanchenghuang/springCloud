package com.chauncy.cloud.demos.ribbon;

import com.chauncy.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Author cheng
 * @create 2020-04-06 13:38
 *
 * ribbon是一套基于客户端 负载均衡的工具
 * 只要提供客户端的软件负载均衡算法和服务调用
 *
 * 在调用某个服务(集群)时，利用一些算法选择合适的服务器工作,高可用--负载均衡
 *
 * 负载均衡：
 * 1、集中式：客户端-nginx，是服务器负载均衡，客户端所有请求都会交给nginx，然后有nginx实现转发请求。
 * 2、进程内：本地负载均衡ribbon，在调用微服务接口的时候，会在注册中心上获取注册信息服务列表之后缓存到JVM本地，
 * 从而在本地实现RPC远程服务调用技术.将LB逻辑集成到消费方，消费方从服务注册中心获知那些地址可用，然后自己再从这些地址中选择出一个合适的服务器
 *
 * ribbon已进入维护期，--httpclient、loadbalancer
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker//熔断
@ComponentScan(basePackages = {"com.chauncy.cloud"})
//@RibbonClient(name = "web-test",configuration = MySelfRule.class)
public class RibbonConsumerApplication {


    public static void main(String[] args) {
        SpringApplication.run(RibbonConsumerApplication.class,args);
    }
}
