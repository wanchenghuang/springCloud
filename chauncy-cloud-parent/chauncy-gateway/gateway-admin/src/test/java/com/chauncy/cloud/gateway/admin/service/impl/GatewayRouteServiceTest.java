package com.chauncy.cloud.gateway.admin.service.impl;

import com.chauncy.cloud.common.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author cheng
 * @create 2020-03-20 21:09
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class GatewayRouteServiceTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testSaveRedis(){

        redisUtil.set("a","chauncy");
    }
}