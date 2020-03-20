package com.chauncy.cloud.gateway.admin.service;

import com.chauncy.cloud.data.domain.dto.gateway.SaveOrUpdateGatewayRouteDto;
import com.chauncy.cloud.data.domain.po.gateway.GatewayRoutePo;
import com.chauncy.cloud.core.config.base.Service;

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
}
