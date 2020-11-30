package com.chauncy.cloud.gateway.admin.controller;


import com.chauncy.cloud.common.base.BaseController;
import com.chauncy.cloud.common.base.Result;
import com.chauncy.cloud.data.domain.dto.gateway.get.GetRouteDto;
import com.chauncy.cloud.data.domain.dto.gateway.save.SaveOrUpdateGatewayRouteDto;
import com.chauncy.cloud.data.domain.dto.gateway.search.SearchRoutesDto;
import com.chauncy.cloud.data.domain.vo.gateway.SearchRoutesVo;
import com.chauncy.cloud.gateway.admin.service.IGatewayRouteService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网关路由表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-03-18
 */
@RestController
@RequestMapping("gateway-route-po")
@Api(tags = "网关路由表")
@Slf4j
public class GatewayRouteController extends BaseController {

    @Autowired
    private IGatewayRouteService service;

    /**
     * @param
     * @return 将所有网关的路由全部重载到redis中
     * @Author chauncy
     * @Date 2020-03-21 10:57
     **/
    @ApiOperation(value = "重载网关路由", notes = "将所有网关的路由全部重载到redis中")
    @PostMapping(value = "/overload")
    public Result overload() {
        return Result.success(service.overload());
    }

    /**
     * @param saveOrUpdateGatewayRoute
     * @return 保存路由信息到数据库和redis中
     * @Author chauncy
     * @Date 2020-03-21 10:57
     **/
    @ApiOperation(value = "保存网关路由信息", notes = "保存路由信息到数据库和redis中")
    @PostMapping("/saveRoute")
    public Result saveRoute(@RequestBody @ApiParam(required = true, name = "saveOrUpdateGatewayRoute", value = "保存网关路由信息")
                            @Validated SaveOrUpdateGatewayRouteDto saveOrUpdateGatewayRoute) {

        log.info("routes:", saveOrUpdateGatewayRoute);

        return success(service.saveRoute(saveOrUpdateGatewayRoute));
    }

    /**
     * @param searchRoutesDto
     * @return 条件分页查询路由信息
     * @Author chauncy
     * @Date 2020-03-21 13:38
     **/
    @ApiOperation(value = "条件分页查询路由信息")
    @PostMapping("/search-routes")
    public Result<PageInfo<SearchRoutesVo>> searchRoutes(@RequestBody @ApiParam(required = true, name = "searchRoutesDto", value = "条件分页查询网关列表")
                                                         @Validated SearchRoutesDto searchRoutesDto) {

        log.info("conditions:", searchRoutesDto);
        return success(service.searchRoutes(searchRoutesDto));

    }

    /**
     * @param getRouteDto
     * @return 条件查询路由信息
     * @Author chauncy
     * @Date 2020-03-22 00:01
     **/
    @ApiOperation(value = "条件查询路由信息")
    @PostMapping("/get")
    public Result<SearchRoutesVo> getRouteByConditions(@RequestBody @ApiParam(required = true, name = "getRouteDto", value = "条件查询网关")
                                                       @Validated GetRouteDto getRouteDto) {
        log.info("conditions:", getRouteDto);

        return success(service.getRouteByConditions(getRouteDto));
    }

    /**
     * @param ids
     * @return 批量删除网关路由
     * @Author chauncy
     * @Date 2020-03-21 22:52
     **/
    @ApiOperation(value = "批量删除网关路由")
    @GetMapping("/batch-del/{ids}")
    public Result BatchDel(@PathVariable String ids) {

        log.info("ids:", ids);
        return success(service.batchDel(ids));
    }


    @ApiOperation(value = "测试配置中心动态更新")
    @GetMapping("/config/test")
    public Result getConfig(){
        return  success(service.getConfig());
    }
}
