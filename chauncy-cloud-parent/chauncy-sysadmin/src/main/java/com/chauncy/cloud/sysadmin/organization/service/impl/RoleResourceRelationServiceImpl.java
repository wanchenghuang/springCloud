package com.chauncy.cloud.sysadmin.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.cloud.data.domain.po.organization.RoleResourceRelationPo;
import com.chauncy.cloud.data.mapper.organization.RoleResourceRelationMapper;
import com.chauncy.cloud.sysadmin.organization.service.IRoleResourceRelationService;
import com.chauncy.cloud.core.config.base.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色和资源关系表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RoleResourceRelationServiceImpl extends AbstractService<RoleResourceRelationMapper,RoleResourceRelationPo> implements IRoleResourceRelationService {

 @Autowired
 private RoleResourceRelationMapper mapper;

    @Override
    public List<RoleResourceRelationPo> queryByRoleIds(Set<String> roleIds) {

        QueryWrapper<RoleResourceRelationPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", roleIds);
        return this.list(queryWrapper);
    }
}
