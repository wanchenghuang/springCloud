package com.chauncy.cloud.sysadmin.organization.service;

import com.chauncy.cloud.data.domain.po.organization.UserRoleRelationPo;
import com.chauncy.cloud.core.config.base.Service;

import java.util.Set;

/**
 * <p>
 * 用户和角色关系表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
public interface IUserRoleRelationService extends Service<UserRoleRelationPo> {

    Set<String> queryByUserId(String id);
}
