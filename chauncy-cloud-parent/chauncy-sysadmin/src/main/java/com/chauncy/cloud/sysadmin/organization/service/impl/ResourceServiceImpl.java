package com.chauncy.cloud.sysadmin.organization.service.impl;

import com.chauncy.cloud.data.domain.po.organization.ResourcePo;
import com.chauncy.cloud.data.domain.po.organization.RoleResourceRelationPo;
import com.chauncy.cloud.data.domain.po.organization.RolesPo;
import com.chauncy.cloud.data.domain.po.organization.UsersPo;
import com.chauncy.cloud.data.mapper.organization.ResourceMapper;
import com.chauncy.cloud.sysadmin.organization.service.IResourceService;
import com.chauncy.cloud.core.config.base.AbstractService;
import com.chauncy.cloud.sysadmin.organization.service.IRoleResourceRelationService;
import com.chauncy.cloud.sysadmin.organization.service.IRolesService;
import com.chauncy.cloud.sysadmin.organization.service.IUsersService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ResourceServiceImpl extends AbstractService<ResourceMapper, ResourcePo> implements IResourceService {

    @Autowired
    private ResourceMapper mapper;

    @Autowired
    private IUsersService userService;

    @Autowired
    private IRolesService roleService;

    @Autowired
    private IRoleResourceRelationService roleResourceService;

    @Override
    public List<ResourcePo> getAll() {

        return this.list();
    }

    @Override
    public List<ResourcePo> queryByName(String username) {
        //根据用户名查询到用户所拥有的角色
        UsersPo user = userService.getByUniqueId(username);
        List<RolesPo> roles = roleService.queryById(user.getId());
        //提取用户所拥有角色id列表
        Set<String> roleIds = roles.stream().map(role -> role.getId()).collect(Collectors.toSet());
        //根据角色列表查询到角色的资源的关联关系
        List<RoleResourceRelationPo> roleResources = roleResourceService.queryByRoleIds(roleIds);
        //根据资源列表查询出所有资源对象
        Set<String> resourceIds = roleResources.stream().map(roleResource -> roleResource.getResourceId()).collect(Collectors.toSet());
        //根据resourceId列表查询出resource对象
        return (List<ResourcePo>) this.listByIds(resourceIds);
    }
}
