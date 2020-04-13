package com.chauncy.cloud.auth.authentization.service;

import com.chauncy.cloud.data.domain.po.organization.RolesPo;

import java.util.Set;

/**
 * @Author cheng
 * @create 2020-04-10 14:29
 */
public interface IRoleService {

    Set<RolesPo> queryUserRolesByUserId(String userId);

}
