package com.chauncy.cloud.data.mapper.gateway;

import com.chauncy.cloud.data.domain.dto.gateway.get.GetRouteDto;
import com.chauncy.cloud.data.domain.dto.gateway.search.SearchRoutesDto;
import com.chauncy.cloud.data.domain.po.gateway.GatewayRoutePo;
import com.chauncy.cloud.data.domain.vo.gateway.SearchRoutesVo;
import com.chauncy.cloud.data.mapper.base.IBaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 网关路由表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-03-18
 */
public interface GatewayRouteMapper extends IBaseMapper<GatewayRoutePo> {

    List<SearchRoutesVo> searchRoutes(SearchRoutesDto searchRoutesDto);

    SearchRoutesVo getRouteByConditions(GetRouteDto getRouteDto);

}
