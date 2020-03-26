package com.chauncy.cloud.sysadmin.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.cloud.data.domain.po.organization.UserRoleRelationPo;
import com.chauncy.cloud.data.mapper.organization.UserRoleRelationMapper;
import com.chauncy.cloud.sysadmin.organization.service.IUserRoleRelationService;
import com.chauncy.cloud.core.config.base.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户和角色关系表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserRoleRelationServiceImpl extends AbstractService<UserRoleRelationMapper,UserRoleRelationPo> implements IUserRoleRelationService {

 @Autowired
 private UserRoleRelationMapper mapper;

    @Override
    public Set<String> queryByUserId(String id) {

        QueryWrapper<UserRoleRelationPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        List<UserRoleRelationPo> userRoleList = list(queryWrapper);
        return userRoleList.stream().map(UserRoleRelationPo::getRoleId).collect(Collectors.toSet());
    }
}
