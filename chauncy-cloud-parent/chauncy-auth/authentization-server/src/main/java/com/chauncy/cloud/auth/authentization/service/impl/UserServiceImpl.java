package com.chauncy.cloud.auth.authentization.service.impl;

import com.chauncy.cloud.auth.authentization.service.IUserService;
import com.chauncy.cloud.client.organization.OrganizationClient;
import com.chauncy.cloud.data.domain.po.organization.UsersPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author cheng
 * @create 2020-04-10 14:43
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private OrganizationClient provider;


    @Override
    public UsersPo getByUniqueId(String uniqueId) {
        return provider.getUserByUniqueId(uniqueId).getData();
    }
}
