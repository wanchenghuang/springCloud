package com.chauncy.cloud.demos.hystrix.rest;

import com.chauncy.cloud.demos.hystrix.service.WebService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cheng
 * @create 2020-04-06 21:51
 */
@RestController
@Slf4j
public class WebController {

    @Autowired
    private WebService webService;

    @Value("${server.port}")
    private String port;

    @GetMapping("/web/hystrix/ok/{id}")
    @ApiOperation("ok")
    public String webInfo_OK(@PathVariable("id") Integer id){

        String result = webService.webInfo_OK(id);
        log.info("******result: "+result);
        return result;
    }

    /**
     * @Author chauncy
     * @Date 2020-04-06 22:30
     * @param  id
     * @return
     *   压测，高并发访问导致ok卡顿，要处理降级处理
     **/
    @GetMapping("/web/hystrix/timeout/{id}")
    @ApiOperation("timeout")
    public String webInfo_timeout(@PathVariable("id") Integer id){

        String result = webService.webInfo_TimeOut(id);
        log.info("******result: "+result);
        return result;
    }
}
