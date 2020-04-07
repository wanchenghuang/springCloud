package com.chauncy.cloud.demos.comsumer.service;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author cheng
 * @create 2020-04-06 22:45
 */
@Component
@FeignClient(value = "cloud-provider-hystrix-payment")
public interface PaymentHystrixService {

    @GetMapping("/web/hystrix/ok/{id}")
    public String webInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("/web/hystrix/timeout/{id}")
    public String webInfo_timeout(@PathVariable("id") Integer id);
    }
