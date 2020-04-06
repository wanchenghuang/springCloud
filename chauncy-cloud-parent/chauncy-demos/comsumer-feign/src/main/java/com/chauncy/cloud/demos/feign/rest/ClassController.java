package com.chauncy.cloud.demos.feign.rest;

import com.chauncy.cloud.common.base.BaseController;
import com.chauncy.cloud.common.base.Result;
import com.chauncy.cloud.data.domain.dto.SearchUsersDto;
import com.chauncy.cloud.data.domain.vo.test.SearchUsersVo;
import com.chauncy.cloud.demos.feign.service.ClassService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author cheng
 * @create 2020-03-15 16:57
 */
@RestController
@RequestMapping("feign-test")
@Api(tags = "用户管理")
public class ClassController extends BaseController {

    @Autowired
    private ClassService service;

    /**
     * @Author chauncy
     * @Date 2020-03-15 17:02
     * @Description //TODO
     *
     * @Update chauncy
     *
     * @param
     * @return com.chauncy.cloud.common.base.Result<java.util.List<com.chauncy.cloud.data.domain.vo.test.SearchUsersVo>>
     **/
    @ApiOperation("测试feign调用--查询全部用户信息")
    @PostMapping("/query-all-user")
    public Result<List<SearchUsersVo>> queryAllUsers(){

        return success(service.queryAllUsers());
    }

    @PostMapping("/search-users")
    @ApiOperation("条件分页查询用户信息")
    public Result<PageInfo<SearchUsersVo>> searchUsers(@RequestBody @ApiParam(required = true,name = "searchUsersDto",value = "查询用户条件")
                                                       @Validated SearchUsersDto searchUsersDto){

        return success(service.searchUsers(searchUsersDto));
    }

    @GetMapping("/web/feign/timeout")
    @ApiOperation("测试feign调用超时")
    public String webFeignTimeout(){
        //openfeign-ribbon 客户端一般默认等待1分钟
        return service.webFeignTimeout();
    }

}
