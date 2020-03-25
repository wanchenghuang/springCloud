package com.chauncy.cloud.demos.web.controller;

import com.chauncy.cloud.common.base.BaseController;
import com.chauncy.cloud.common.base.Result;
import com.chauncy.cloud.common.utils.AutoGeneratorUtils;
import com.chauncy.cloud.demos.web.dto.GeneratorDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cheng
 * @create 2020-03-24 22:02
 */
@RestController
@Api(tags = "自动生成代码")
@RequestMapping("/")
public class GeneratorController extends BaseController {

    @ApiOperation("自动生成代码")
    @PostMapping("generate")
    public Result generate(@RequestBody @ApiParam(required = true,name = "generator",value = "自动生成代码")
                           @Validated GeneratorDto generatorDto){

        AutoGeneratorUtils.autoGenerator(generatorDto.getUsername(),generatorDto.getPassword(),
                generatorDto.getModelName(),generatorDto.getDatabase(),generatorDto.getTableName(),generatorDto.getHost(),generatorDto.getPort());

        return success();
    }
}
