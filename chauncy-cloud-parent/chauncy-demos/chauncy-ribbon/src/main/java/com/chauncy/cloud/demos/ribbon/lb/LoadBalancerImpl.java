package com.chauncy.cloud.demos.ribbon.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author cheng
 * @create 2020-04-06 17:39
 */
@Service
public class LoadBalancerImpl implements LoadBalancer {

    private AtomicInteger nextServerCyclicCounter = new AtomicInteger(0);

    public final int incrementAndGetModulo(){

        int current;
        int next;
        do {
            current = this.nextServerCyclicCounter.get();
            next = current >= 2147483647 ? 0 : current+1; //2147483647整型的最大值，考虑int越界

        } while(!this.nextServerCyclicCounter.compareAndSet(current, next));//true表示更新成功，false表示更新失败，继续重试
        System.out.printf("*****第几次访问，次数next: %s.%n",next);

        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {

        int index = incrementAndGetModulo() % serviceInstances.size();

        return serviceInstances.get(index);

    }
}

