package com.chauncy.cloud.auth.authentization.service.impl;

import com.chauncy.cloud.auth.authentization.service.IRoleService;
import com.chauncy.cloud.client.organization.OrganizationClient;
import com.chauncy.cloud.data.domain.po.organization.RolesPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author cheng
 * @create 2020-04-10 14:43
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private OrganizationClient provider;

    @Override
    public Set<RolesPo> queryUserRolesByUserId(String userId) {
        return provider.queryRolesByUserId(userId).getData();
    }
}
