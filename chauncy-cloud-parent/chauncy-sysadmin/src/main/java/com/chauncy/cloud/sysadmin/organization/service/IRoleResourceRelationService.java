package com.chauncy.cloud.sysadmin.organization.service;

import com.chauncy.cloud.data.domain.po.organization.RoleResourceRelationPo;
import com.chauncy.cloud.core.config.base.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色和资源关系表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
public interface IRoleResourceRelationService extends Service<RoleResourceRelationPo> {

    List<RoleResourceRelationPo> queryByRoleIds(Set<String> roleIds);
}
