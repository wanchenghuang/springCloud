package com.chauncy.cloud.gateway.admin.service;

import com.chauncy.cloud.data.domain.dto.gateway.get.GetRouteDto;
import com.chauncy.cloud.data.domain.dto.gateway.save.SaveOrUpdateGatewayRouteDto;
import com.chauncy.cloud.data.domain.dto.gateway.search.SearchRoutesDto;
import com.chauncy.cloud.data.domain.po.gateway.GatewayRoutePo;
import com.chauncy.cloud.core.config.base.Service;
import com.chauncy.cloud.data.domain.vo.gateway.SearchRoutesVo;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 * 网关路由表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-03-18
 */
public interface IGatewayRouteService extends Service<GatewayRoutePo> {


    boolean saveRoute(SaveOrUpdateGatewayRouteDto saveOrUpdateGatewayRoute);

    /**
     * 重新加载网关路由配置到redis
     *
     * @return 成功返回true
     */
    boolean overload();

    /**
     * @Author chauncy
     * @Date 2020-03-21 13:37
     * @param  searchRoutesDto
     * @return
     *       条件分页查询路由信息
     **/
    PageInfo<SearchRoutesVo> searchRoutes(SearchRoutesDto searchRoutesDto);

    /**
     * @Author chauncy
     * @Date 2020-03-21 22:53
     * @param  ids
     * @return
     *  批量删除网关路由
     **/
    boolean batchDel(String ids);

    /**
     * @Author chauncy
     * @Date 2020-03-22 00:02
     * @param  getRouteDto
     * @return
     *       getRouteDto
     **/
    SearchRoutesVo getRouteByConditions(GetRouteDto getRouteDto);
}
