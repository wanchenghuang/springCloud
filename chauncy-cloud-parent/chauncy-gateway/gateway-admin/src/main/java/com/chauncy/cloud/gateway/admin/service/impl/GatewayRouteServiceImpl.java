package com.chauncy.cloud.gateway.admin.service.impl;

import cn.hutool.core.convert.Convert;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.cloud.common.constant.Constants;
import com.chauncy.cloud.common.enums.system.exception.Code;
import com.chauncy.cloud.common.exception.BusinessException;
import com.chauncy.cloud.common.utils.RedisUtil;
import com.chauncy.cloud.core.config.base.AbstractService;
import com.chauncy.cloud.data.domain.dto.gateway.get.GetRouteDto;
import com.chauncy.cloud.data.domain.dto.gateway.save.FilterDefinitionDto;
import com.chauncy.cloud.data.domain.dto.gateway.save.PredicateDefinitionDto;
import com.chauncy.cloud.data.domain.dto.gateway.save.SaveOrUpdateGatewayRouteDto;
import com.chauncy.cloud.data.domain.dto.gateway.search.SearchRoutesDto;
import com.chauncy.cloud.data.domain.po.gateway.GatewayRoutePo;
import com.chauncy.cloud.data.domain.vo.gateway.SearchRoutesVo;
import com.chauncy.cloud.data.mapper.gateway.GatewayRouteMapper;
import com.chauncy.cloud.gateway.admin.event.EventSender;
import com.chauncy.cloud.gateway.admin.service.IGatewayRouteService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.chauncy.cloud.common.constant.Constants.GATEWAY_ROUTES;

/**
 * <p>
 * 网关路由表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-03-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class GatewayRouteServiceImpl extends AbstractService<GatewayRouteMapper, GatewayRoutePo> implements IGatewayRouteService {

    @CreateCache(name = GATEWAY_ROUTES, cacheType = CacheType.REMOTE)
    private Cache<String, String> gatewayRouteCache;

    @Autowired
    private GatewayRouteMapper mapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EventSender eventSender;

    /**
     * @Author chauncy
     * @Date 2020-03-21 10:57
     * @param  saveOrUpdateGatewayRoute
     * @return
     *
     * 保存路由信息到数据库和redis中
     **/
    @Override
    public boolean saveRoute(SaveOrUpdateGatewayRouteDto saveOrUpdateGatewayRoute) {

        boolean isSuccess = false;
        GatewayRoutePo gatewayRoute = new GatewayRoutePo();
        List<String> routIds = Lists.newArrayList();
        List<String> uris = Lists.newArrayList();
        List<GatewayRoutePo> routePos = mapper.selectList(null);
//        GatewayRoutePo gatewayRoutePo = new GatewayRoutePo();

        if (saveOrUpdateGatewayRoute.getId() != 0){
            gatewayRoute = mapper.selectById(saveOrUpdateGatewayRoute.getId());
            if (gatewayRoute ==null ){
                throw new BusinessException(Code.ERROR,String.format("数据库不存在ID为[%s]的路由！",saveOrUpdateGatewayRoute.getId()));
            }else {
                GatewayRoutePo finalGatewayRoutePo = gatewayRoute;
                routIds = routePos.stream().map(a->a.getRouteId()).filter(b->!b.equals(finalGatewayRoutePo.getRouteId())).collect(Collectors.toList());
                uris = routePos.stream().map(a->a.getUri()).filter(b->!b.equals(finalGatewayRoutePo.getUri())).collect(Collectors.toList());
                if (routIds.contains(saveOrUpdateGatewayRoute.getRouteId())){
                    throw new BusinessException(Code.ERROR,String.format("网关路由id:【%s】不能重复！",saveOrUpdateGatewayRoute.getRouteId()));
                }
                if (uris.contains(saveOrUpdateGatewayRoute.getUri())){
                    throw new BusinessException(Code.ERROR,String.format("网关uri:【%s】不能重复！",saveOrUpdateGatewayRoute.getUri()));
                }
                gatewayRoute = saveOrUpdateGatewayRoute.toPo(saveOrUpdateGatewayRoute.getId(), GatewayRoutePo.class);
                isSuccess = this.updateById(gatewayRoute);
            }
        }else {
            routIds = routePos.stream().map(a->a.getRouteId()).collect(Collectors.toList());
            uris = routePos.stream().map(a->a.getUri()).collect(Collectors.toList());
            if (routIds.contains(saveOrUpdateGatewayRoute.getRouteId())){
                throw new BusinessException(Code.ERROR,String.format("网关路由id:【%s】不能重复！",saveOrUpdateGatewayRoute.getRouteId()));
            }
            if (uris.contains(saveOrUpdateGatewayRoute.getUri())){
                throw new BusinessException(Code.ERROR,String.format("网关uri:【%s】不能重复！",saveOrUpdateGatewayRoute.getUri()));
            }
            gatewayRoute = saveOrUpdateGatewayRoute.toPo(GatewayRoutePo.class);
            gatewayRoute.setId(null);
            isSuccess = this.save(gatewayRoute);
        }
        if (isSuccess) {
            if (saveOrUpdateGatewayRoute.getId() != 0){
                redisUtil.hDelete(GATEWAY_ROUTES,gatewayRoute.getRouteId());
            }
            RouteDefinition routeDefinition = gatewayRouteToRouteDefinition(gatewayRoute);
            // 转化为gateway需要的类型，缓存到redis, 并事件通知各网关应用
            redisUtil.hPut(GATEWAY_ROUTES,gatewayRoute.getRouteId(), new Gson().toJson(routeDefinition));
            //gatewayRouteCache.put(gatewayRoute.getRouteId(),routeDefinition);
            eventSender.send(Constants.ROUTING_KEY, routeDefinition);
        }

        return isSuccess;
    }


    /**
     * 重新加载网关路由配置到redis
     *
     * @return 成功返回true
     */
    @Override
    @PostConstruct
    public boolean overload() {
        List<GatewayRoutePo> gatewayRoutes = this.list(new QueryWrapper<>());
//        gatewayRoutes.forEach(gatewayRoute ->
//                redisUtil.set(GATEWAY_ROUTES+gatewayRoute.getRouteId(), gatewayRouteToRouteDefinition(gatewayRoute))
//                gatewayRouteCache.put(gatewayRoute.getRouteId(), new Gson().toJson(gatewayRouteToRouteDefinition(gatewayRoute)))
//        );
        gatewayRoutes.forEach(gatewayRoute ->
                redisUtil.hset(GATEWAY_ROUTES,gatewayRoute.getRouteId(), new Gson().toJson(gatewayRouteToRouteDefinition(gatewayRoute)))
        );
        log.info("全局初使化网关路由成功!");
        return true;
    }

    /**
     * @Author chauncy
     * @Date 2020-03-21 13:38
     * @param  searchRoutesDto
     * @return
     *      条件分页查询路由信息
     **/
    @Override
    public PageInfo<SearchRoutesVo> searchRoutes(SearchRoutesDto searchRoutesDto) {

        Integer pageNo = searchRoutesDto.getPageNo() == null ? defaultPageNo : searchRoutesDto.getPageNo();
        Integer pageSize = searchRoutesDto.getPageSize() == null ? defaultPageSize : searchRoutesDto.getPageSize();

        /*mapper.selectList(searchRoutesDto.build().lambda()
                .eq(StringUtils.isNotBlank(searchRoutesDto.getUri()), GatewayRoutePo::getUri, searchRoutesDto.getUri()))
                .stream().map(SearchRoutesVo::new).collect(Collectors.toList());*/

        PageInfo<SearchRoutesVo> routesVoPageInfo = PageHelper.startPage(pageNo,pageSize)
                .doSelectPageInfo(() -> mapper.searchRoutes(searchRoutesDto));

        routesVoPageInfo.getList().forEach(route->{
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                route.setFilterList(objectMapper.readValue(route.getFilters(), new TypeReference<List<FilterDefinitionDto>>() {
                }));
                route.setPredicateList(objectMapper.readValue(route.getPredicates(), new TypeReference<List<PredicateDefinitionDto>>() {
                }));
            }catch (IOException e){
                log.error("网关路由对象转换失败", e);
            }
        });



        return routesVoPageInfo;
    }

    /**
     * @Author chauncy
     * @Date 2020-03-21 22:53
     * @param  ids
     * @return
     *       批量删除网关路由
     **/
    @Override
    public boolean batchDel(String ids) {
        Long[] routIds = null;
        try {
            routIds = Convert.toLongArray(ids);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new BusinessException(Code.ERROR,"id长度错误！");
        }
        List<Long> idList = Arrays.asList(routIds);
        idList.forEach(a->{
            GatewayRoutePo gatewayRoutePo = mapper.selectByIds(a);
            if (gatewayRoutePo == null){
                throw new BusinessException(Code.ERROR,String.format("数据库不存在id:[%s]的路由",a));
            }
        });
        idList.forEach(a->{
            GatewayRoutePo gatewayRoutePo = mapper.selectById(a);
            redisUtil.hDelete(GATEWAY_ROUTES,gatewayRoutePo.getRouteId());
        });

        int result = mapper.deleteBatchIds(idList);

        return result > 0 ? true : false;
    }

    /**
     * @Author chauncy
     * @Date 2020-03-22 00:02
     * @param  getRouteDto
     * @return
     *       getRouteDto
     **/
    @Override
    public SearchRoutesVo getRouteByConditions(GetRouteDto getRouteDto) {

        SearchRoutesVo searchRoutesVo = mapper.getRouteByConditions(getRouteDto);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            searchRoutesVo.setPredicateList(objectMapper.readValue(searchRoutesVo.getPredicates(), new TypeReference<List<PredicateDefinitionDto>>() {
            }));
            searchRoutesVo.setFilterList(objectMapper.readValue(searchRoutesVo.getFilters(), new TypeReference<List<FilterDefinitionDto>>() {
            }));
        }catch (Exception e){
            log.error("网关路由对象转换失败", e);
        }

        return searchRoutesVo;
    }

    /**
     * 将数据库中json对象转换为网关需要的RouteDefinition对象
     *
     * @param gatewayRoute
     * @return RouteDefinition
     */
    private RouteDefinition gatewayRouteToRouteDefinition(GatewayRoutePo gatewayRoute) {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gatewayRoute.getRouteId());
        routeDefinition.setOrder(gatewayRoute.getOrders());
        routeDefinition.setUri(URI.create(gatewayRoute.getUri()));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            routeDefinition.setFilters(objectMapper.readValue(gatewayRoute.getFilters(), new TypeReference<List<FilterDefinition>>() {
            }));
            routeDefinition.setPredicates(objectMapper.readValue(gatewayRoute.getPredicates(), new TypeReference<List<PredicateDefinition>>() {
            }));
        } catch (IOException e) {
            log.error("网关路由对象转换失败", e);
        }
        return routeDefinition;
    }
}
