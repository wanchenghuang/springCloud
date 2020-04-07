package com.chauncy.cloud.demos.comsumer.rest;

import com.chauncy.cloud.demos.comsumer.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cheng
 * @create 2020-04-06 22:51
 */
@RestController
@Slf4j
public class OrdertHystrixController {

    @Autowired
    private PaymentHystrixService service;

    /**
     * @Author chauncy
     * @Date 2020-04-06 23:10
     * @param  id
     * @return
     *     使用20000个线程压测提供方的timeout接口，导致
     *     客户端卡顿转圈--降级处理
     **/
    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    @ApiOperation("ok")
    public String webInfo_OK(@PathVariable("id") Integer id){

        return service.webInfo_OK(id);
    }

    /**
     * @Author chauncy
     * @Date 2020-04-06 23:10
     * @param  id
     * @return
     *
     **/
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @ApiOperation("timeout")
    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })
    public String webInfo_timeout(@PathVariable("id") Integer id){

//        int a = 10/0;
        return service.webInfo_timeout(id);
    }

    public String paymentTimeOutFallbackMethod(@PathVariable ( "id" ) Integer id) {
        return "我是消费者 80, 对方支付系统繁忙请 10 秒种后再试或者自己运行出错请检查自己 ,o(╥ ﹏ ╥)o";
    }

}
