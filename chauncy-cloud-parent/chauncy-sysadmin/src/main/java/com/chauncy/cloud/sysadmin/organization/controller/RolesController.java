package com.chauncy.cloud.sysadmin.organization.controller;


import com.chauncy.cloud.common.base.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.cloud.sysadmin.organization.service.IRolesService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.cloud.common.base.BaseController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色表")
@Slf4j
public class RolesController extends BaseController {

    @Autowired
    private IRolesService service;


    @ApiOperation(value = "查询角色", notes = "根据用户id查询用户所拥有的角色信息")
    @GetMapping(value = "/user/{userId}")
    public Result query(@PathVariable String userId) {
        log.debug("query with userId:{}", userId);
        return success(service.queryById(userId));
    }
}
