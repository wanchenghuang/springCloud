package com.chauncy.cloud.auth.authentization.service;

import com.chauncy.cloud.data.domain.po.organization.UsersPo;
import org.springframework.cache.annotation.Cacheable;

/**
 * @Author cheng
 * @create 2020-04-10 14:29
 */
public interface IUserService {

    /**
     * 根据用户唯一标识获取用户信息
     *
     * @param uniqueId
     * @return
     */
//    @Cacheable(value = "#id")
    UsersPo getByUniqueId(String uniqueId);
}
