package com.chauncy.cloud.sysadmin.organization.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.cloud.sysadmin.organization.service.IUsersService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.cloud.common.base.BaseController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@RestController
@RequestMapping("users-po")
@Api(tags = "用户表")
public class UsersController extends BaseController {

 @Autowired
 private IUsersService service;


}
