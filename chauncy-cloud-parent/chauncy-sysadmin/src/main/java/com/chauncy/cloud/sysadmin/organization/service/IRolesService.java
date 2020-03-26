package com.chauncy.cloud.sysadmin.organization.service;

import com.chauncy.cloud.data.domain.po.organization.RolesPo;
import com.chauncy.cloud.core.config.base.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
public interface IRolesService extends Service<RolesPo> {

    List<RolesPo> queryById(String userId);
}

