package com.chauncy.cloud.sysadmin.organization.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import com.chauncy.cloud.sysadmin.organization.service.IUserRoleRelationService;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.cloud.common.base.BaseController;

/**
 * <p>
 * 用户和角色关系表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@RestController
@RequestMapping("user-role-relation-po")
@Api(tags = "用户和角色关系表")
public class UserRoleRelationController extends BaseController {

 @Autowired
 private IUserRoleRelationService service;


}
