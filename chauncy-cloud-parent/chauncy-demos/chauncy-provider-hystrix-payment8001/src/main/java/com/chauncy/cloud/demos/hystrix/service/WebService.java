package com.chauncy.cloud.demos.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author cheng
 * @create 2020-04-06 21:45
 */
@Service
public class WebService {

    /**
     * @Author chauncy
     * @Date 2020-04-06 21:48
     * @param  id
     * @return
     *   正常访问
     **/
    public String webInfo_OK(Integer id){

        return "线程池： "+Thread.currentThread().getName()+" webInfo_ok,id: "+id+"-.-";
    }


    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String webInfo_TimeOut(Integer id){

        int timeNumber = 5;
//        int age = 10/0;
        try{
            TimeUnit.SECONDS.sleep(timeNumber);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "线程池： "+Thread.currentThread().getName()+" webInfo_TimeOut,id: "+id+"\t"+"-.-"+
                " 耗时(秒):"+timeNumber;

    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池： "+Thread.currentThread().getName()+" 系统繁忙或运行报错，请稍后再试,id: "+id+"timeout";
    }
}
