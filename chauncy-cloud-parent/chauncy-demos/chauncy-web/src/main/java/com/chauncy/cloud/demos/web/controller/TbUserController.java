package com.chauncy.cloud.demos.web.controller;


import com.chauncy.cloud.common.base.Result;
import com.chauncy.cloud.data.domain.dto.SaveTestUsersDto;
import com.chauncy.cloud.data.domain.dto.SearchUsersDto;
import com.chauncy.cloud.data.domain.po.test.TbUserPo;
import com.chauncy.cloud.data.domain.vo.test.SearchUsersVo;
import com.chauncy.cloud.data.valid.ISaveGroup;
import com.chauncy.cloud.demos.web.service.ITbUserService;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import com.chauncy.cloud.common.base.BaseController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-03-06
 */
@RestController
@RequestMapping("tb-user-po")
@Api(tags = "用户管理")
public class TbUserController extends BaseController {

    @Autowired
    private ITbUserService service;

    /**
     * @Author chauncy
     * @Date 2020-03-07 00:02
     * @Description //测试查询全部用户信息
     *
     * @Update chauncy
     *
     * @param
     * @return com.chauncy.cloud.common.base.Result<java.util.List<com.chauncy.cloud.data.domain.vo.test.SearchUsersVo>>
     **/
    @GetMapping("/query-all-user")
    @ApiOperation("测试查询全部用户信息")
    public Result<List<SearchUsersVo>> queryAllUsers(){

        return success(service.queryAllUsers());
    }

    /**
     * @Author chauncy
     * @Date 2020-03-07 00:11
     * @Description //baseService使用，数据源切换，mybatis-plus结合lambda使用，查询逻辑删除
     *
     * @Update chauncy
     *
     * @param  username
     * @return com.chauncy.cloud.common.base.Result<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @GetMapping("/find-by-username")
    @ApiOperation(("测试基础业务模块，根据用户名查询信息"))
    public Result<Map<String,Object>> findByUserName(@RequestParam String username) {

        //测试baseService使用
        Map<String, Object> result = service.findByUserName(username);
        //测试业务模块结合lambda使用，查询逻辑删除 where del_flag = 0
        service.test(username);

        return success(result);
    }

    /**
     * @Author chauncy
     * @Date 2020-03-07 13:07
     * @Description //逻辑删除操作，以及抛出异常处理
     *
     * @Update chauncy
     *
     * @param  ids
     * @return com.chauncy.cloud.common.base.Result
     **/
    @DeleteMapping("/del-by-ids/{ids}")
    @ApiOperation("根据ID批量删除")
    public Result delByIds(@PathVariable @NonNull @ApiParam(required = true,name = "ids",value = "用户ID集合")
                                       List<Long> ids){

        service.delByIds(ids);
        return success();
    }

    /**
     * @Author chauncy
     * @Date 2020-03-07 14:12
     * @Description //测试分页插件、入参校验、
    *
     * @Update chauncy
     *
     * @param  searchUsersDto
     * @return com.chauncy.cloud.common.base.Result<com.github.pagehelper.PageInfo<com.chauncy.cloud.data.domain.vo.test.SearchUsersVo>>
     **/
    @PostMapping("/search-users")
    @ApiOperation("条件分页查询用户信息")
    public Result<PageInfo<SearchUsersVo>> searchUsers(@RequestBody @ApiParam(required = true,name = "searchUsersDto",value = "查询用户条件")
                                                       @Validated SearchUsersDto searchUsersDto){

        return success(service.searchUsers(searchUsersDto));
    }

    /**
     * @Author chauncy
     * @Date 2020-03-07 16:10
     * @Description //测试入参校验、事物回滚
     *
     * @Update chauncy
     *
     * @param  saveTestUsersDto
     * @return com.chauncy.cloud.common.base.Result
     **/
    @PostMapping("/save-user")
    @ApiOperation("保存用户信息")
    public Result saveUser(@RequestBody @ApiParam(required = true,name = "saveTestUsersDto",value = "保存用户信息")
                               @Validated(ISaveGroup.class) SaveTestUsersDto saveTestUsersDto){

        service.saveUser(saveTestUsersDto);

        return success();
    }

    @GetMapping("/query-other-user")
    @ApiOperation("查询其他数据源用户")
    public Result queryOtherUser(@RequestParam @ApiParam(required = true,name = "username",value = "名字") String username){

        //测试切换数据源
        return success(service.queryUser(username));
    }

}
