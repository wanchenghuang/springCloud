package com.chauncy.cloud.thread.imports.controller;


import com.chauncy.cloud.thread.imports.service.IImportDataStepService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.cloud.common.base.BaseController;

/**
 * <p>
 * 数据抽取步骤表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-07-16
 */
@RestController
@RequestMapping("import-data-step-po")
@Api(tags = "数据抽取步骤表")
public class ImportDataStepController extends BaseController {

 @Autowired
 private IImportDataStepService service;


}
