package com.chauncy.cloud.demos.ribbon.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @Author cheng
 * @create 2020-04-06 17:36
 */
public interface LoadBalancer {

    ServiceInstance instances(List<ServiceInstance> serviceInstances);

}
