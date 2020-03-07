package com.chauncy.cloud.demos.web.controller;


import com.chauncy.cloud.demos.web.service.ITbUsersService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.cloud.common.base.BaseController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-03-06
 */
@RestController
@RequestMapping("tb-users-po")
@Api(tags = "用户表")
public class TbUsersController extends BaseController {

    @Autowired
    private ITbUsersService service;


}
