package com.chauncy.cloud.sysadmin.organization.service;

import com.chauncy.cloud.data.domain.po.organization.UsersPo;
import com.chauncy.cloud.core.config.base.Service;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
public interface IUsersService extends Service<UsersPo> {

    UsersPo getByUniqueId(String uniqueId);
}
