package com.chauncy.cloud.thread.imports.controller;


import com.chauncy.cloud.thread.imports.service.IPointService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.cloud.common.base.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-07-16
 */
@RestController
@RequestMapping("point-po")
@Api(tags = "")
public class PointController extends BaseController {

 @Autowired
 private IPointService service;


}
