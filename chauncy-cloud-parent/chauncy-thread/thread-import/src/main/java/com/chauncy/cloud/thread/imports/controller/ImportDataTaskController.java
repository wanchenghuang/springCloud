package com.chauncy.cloud.thread.imports.controller;


import com.chauncy.cloud.thread.imports.service.IImportDataTaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.RestController;
import com.chauncy.cloud.common.base.BaseController;

/**
 * <p>
 * 数据抽取任务表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-07-16
 */
@RestController
@RequestMapping("import-data-task-po")
@Api(tags = "数据抽取任务表")
public class ImportDataTaskController extends BaseController {

 @Autowired
 private IImportDataTaskService service;


}
