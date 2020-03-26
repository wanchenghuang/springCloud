package com.chauncy.cloud.sysadmin.organization.controller;


import com.chauncy.cloud.common.base.Result;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.chauncy.cloud.sysadmin.organization.service.IResourceService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.cloud.common.base.BaseController;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/resource")
@Api(tags = "资源表")
@Slf4j
public class ResourceController extends BaseController {

    @Autowired
    private IResourceService service;

    @ApiOperation(value = "查询所有资源", notes = "查询所有资源信息")
    @GetMapping(value = "/all")
    public Result queryAll() {
        return success(service.getAll());
    }

    @ApiOperation(value = "查询资源", notes = "根据userId查询用户所拥有的资源信息")
    @ApiResponses(
            @ApiResponse(code = 200, message = "处理成功", response = Result.class)
    )
    @GetMapping(value = "/user/{username}")
    public Result queryByUsername(@PathVariable String username) {
        log.debug("query with username:{}", username);
        return success(service.queryByName(username));
    }
}
