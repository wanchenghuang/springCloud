package com.chauncy.cloud.sysadmin.organization.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.cloud.common.exception.BusinessException;
import com.chauncy.cloud.data.domain.po.organization.UsersPo;
import com.chauncy.cloud.data.mapper.organization.UsersMapper;
import com.chauncy.cloud.sysadmin.organization.service.IUserRoleRelationService;
import com.chauncy.cloud.sysadmin.organization.service.IUsersService;
import com.chauncy.cloud.core.config.base.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UsersServiceImpl extends AbstractService<UsersMapper, UsersPo> implements IUsersService {

    @Autowired
    private UsersMapper mapper;

    @Autowired
    private IUserRoleRelationService userRoleService;

    @Override
    @Cached(name = "user::", key = "#uniqueId", cacheType = CacheType.REMOTE)
    public UsersPo getByUniqueId(String uniqueId) {
        UsersPo user = this.getOne(new QueryWrapper<UsersPo>()
                .eq("username", uniqueId)
                .or()
                .eq("mobile", uniqueId));
        if (Objects.isNull(user)) {
            throw new BusinessException("user not found with uniqueId:" + uniqueId);
        }
        user.setRoleIds(userRoleService.queryByUserId(user.getId()));
        return user;
    }
}
