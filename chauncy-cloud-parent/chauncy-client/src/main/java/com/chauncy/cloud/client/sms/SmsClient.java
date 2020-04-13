package com.chauncy.cloud.client.sms;

import com.chauncy.cloud.client.config.MyFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author cheng
 * @create 2020-04-10 14:53
 *
 * 待实现手机验证码模块
 */
@FeignClient(name = "sms",configuration = MyFeignClientConfig.class)
public interface SmsClient {

    @GetMapping(value = "/sms/{mobile}")
    String getSmsCode(@PathVariable("mobile") String mobile, @RequestParam("businessType") String businessType);

}
