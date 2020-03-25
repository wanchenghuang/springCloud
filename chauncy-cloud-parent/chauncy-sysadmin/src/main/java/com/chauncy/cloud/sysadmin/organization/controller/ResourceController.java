package com.chauncy.cloud.sysadmin.organization.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
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
@RequestMapping("resource-po")
@Api(tags = "资源表")
public class ResourceController extends BaseController {

 @Autowired
 private IResourceService service;


}
