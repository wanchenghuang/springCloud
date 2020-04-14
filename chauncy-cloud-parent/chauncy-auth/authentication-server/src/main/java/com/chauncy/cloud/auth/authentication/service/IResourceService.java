package com.chauncy.cloud.auth.authentication.service;

import com.chauncy.cloud.data.domain.po.organization.ResourcePo;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * @Author cheng
 * @create 2020-04-14 09:42
 */
public interface IResourceService {

    /**
     * 动态新增更新权限
     *
     * @param resource
     */
    void saveResource(ResourcePo resource);

    /**
     * 动态删除权限
     *
     * @param resource
     */
    void removeResource(ResourcePo resource);

    /**
     * 加载权限资源数据
     */
    Map<RequestMatcher, ConfigAttribute> loadResource();

    /**
     * 根据url和method查询到对应的权限信息
     *
     * @param authRequest
     * @return
     */
    ConfigAttribute findConfigAttributesByUrl(HttpServletRequest authRequest);

    /**
     * 根据用户名查询 该用户所拥有的角色对应的资源信息
     *
     * @param username
     * @return
     */
    Set<ResourcePo> queryByUsername(String username);

}
