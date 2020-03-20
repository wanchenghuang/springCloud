package com.chauncy.cloud.gateway.admin.controller;


import com.chauncy.cloud.common.base.Result;
import com.chauncy.cloud.data.domain.dto.gateway.SaveOrUpdateGatewayRouteDto;
import com.chauncy.cloud.gateway.admin.service.IGatewayRouteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.cloud.common.base.BaseController;

/**
 * <p>
 * 网关路由表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-03-18
 */
@RestController
@RequestMapping("gateway-route-po")
@Api(tags = "网关路由表")
@Slf4j
public class GatewayRouteController extends BaseController {

    @Autowired
    private IGatewayRouteService service;

    @ApiOperation(value = "保存网关路由信息")
    @PostMapping("/saveRoute")
    public Result saveRoute(@RequestBody @ApiParam(required = true,name = "saveOrUpdateGatewayRoute",value = "保存网关路由信息")
                           @Validated SaveOrUpdateGatewayRouteDto saveOrUpdateGatewayRoute){

        log.info("routes:",saveOrUpdateGatewayRoute);

        return success(service.saveRoute(saveOrUpdateGatewayRoute));
    }


}
