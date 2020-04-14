package com.chauncy.cloud.auth.authentication.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.chauncy.cloud.auth.authentication.service.IResourceService;
import com.chauncy.cloud.auth.authentication.service.NewMvcRequestMatcher;
import com.chauncy.cloud.client.organization.OrganizationClient;
import com.chauncy.cloud.data.domain.po.organization.ResourcePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author cheng
 * @create 2020-04-14 09:53
 */
@Service
@Slf4j
public class ResourceServiceImpl implements IResourceService {
    @Autowired
    private HandlerMappingIntrospector mvcHandlerMappingIntrospector;

    @Autowired
    private OrganizationClient resourceProvider;

    /**
     * 系统中所有权限集合
     */
    private Map<RequestMatcher, ConfigAttribute> resourceConfigAttributes;

    @Override
    public void saveResource(ResourcePo resource) {
        resourceConfigAttributes.put(
                this.newMvcRequestMatcher(resource.getUrl(), resource.getMethod()),
                new SecurityConfig(resource.getCode())
        );
        log.info("resourceConfigAttributes size:{}", this.resourceConfigAttributes.size());
    }

    @Override
    public void removeResource(ResourcePo resource) {
        resourceConfigAttributes.remove(this.newMvcRequestMatcher(resource.getUrl(), resource.getMethod()));
        log.info("resourceConfigAttributes size:{}", this.resourceConfigAttributes.size());
    }

    @Override
    public Map<RequestMatcher, ConfigAttribute> loadResource() {
        Set<ResourcePo> resources = resourceProvider.resources().getData();
        this.resourceConfigAttributes = resources.stream()
                .collect(Collectors.toMap(
                        resource -> this.newMvcRequestMatcher(resource.getUrl(), resource.getMethod()),
                        resource -> new SecurityConfig(resource.getCode())
                ));
        log.debug("init resourceConfigAttributes:{}", this.resourceConfigAttributes);
        return this.resourceConfigAttributes;
    }

    @Override
    public ConfigAttribute findConfigAttributesByUrl(HttpServletRequest authRequest) {
        return this.resourceConfigAttributes.keySet().stream()
                .filter(requestMatcher -> requestMatcher.matches(authRequest))
                .map(requestMatcher -> this.resourceConfigAttributes.get(requestMatcher))
                .peek(urlConfigAttribute -> log.debug("url在资源池中配置：{}", urlConfigAttribute.getAttribute()))
                .findFirst()
                .orElse(new SecurityConfig("NONEXISTENT_URL"));
    }

    @Override
    @Cached(name = "resource4user::", key = "#username", cacheType = CacheType.LOCAL)
    public Set<ResourcePo> queryByUsername(String username) {
        return resourceProvider.resources(username).getData();
    }

    /**
     * 创建RequestMatcher
     *
     * @param url
     * @param method
     * @return
     */
    private MvcRequestMatcher newMvcRequestMatcher(String url, String method) {
        return new NewMvcRequestMatcher(mvcHandlerMappingIntrospector, url, method);
    }
}