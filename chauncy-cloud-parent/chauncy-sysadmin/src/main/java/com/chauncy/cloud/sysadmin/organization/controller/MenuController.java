package com.chauncy.cloud.sysadmin.organization.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.cloud.sysadmin.organization.service.IMenuService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.cloud.common.base.BaseController;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@RestController
@RequestMapping("menu-po")
@Api(tags = "菜单表")
public class MenuController extends BaseController {

 @Autowired
 private IMenuService service;


}
