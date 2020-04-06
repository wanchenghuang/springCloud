package com.chauncy.cloud.demos.ribbon.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.chauncy.cloud.common.base.Result;
import com.chauncy.cloud.common.enums.system.exception.Code;
import com.chauncy.cloud.common.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author cheng
 * @create 2020-04-06 13:51
 *
 * sentinel提供服务降级、熔断处理、流量控制等
 */
@Service
public class ClassService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * @Author chauncy
     * @Date 2020-04-06 15:29
     * @param
     * @return
     *   getForObject:返回对象为响应体中数据转换的对象，基本上可以理解为json
     *   getForEntity:返回对象为ResponseEntity对象，包含了响应中的一些重要信息，比如响应头、
     *   响应状态码、响应体等
     **/
    @SentinelResource(fallback = "getUsersFallback")
    public Result getUsers(){
//        return restTemplate.getForObject("http://web-test/tb-user-po/query-all-user",Result.class);
        return JSONUtils.parseObject(restTemplate.getForEntity("http://web-test/tb-user-po/query-all-user",String.class).getBody(),Result.class);
    }


    public Result getUsersFallback() {
        return Result.error(Code.ERROR);
    }

}
