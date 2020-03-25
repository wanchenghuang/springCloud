package com.chauncy.cloud.sysadmin.organization.service.impl;

import com.chauncy.cloud.data.domain.po.organization.UserGroupRelationPo;
import com.chauncy.cloud.data.mapper.organization.UserGroupRelationMapper;
import com.chauncy.cloud.sysadmin.organization.service.IUserGroupRelationService;
import com.chauncy.cloud.core.config.base.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户和组关系表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserGroupRelationServiceImpl extends AbstractService<UserGroupRelationMapper,UserGroupRelationPo> implements IUserGroupRelationService {

 @Autowired
 private UserGroupRelationMapper mapper;

}
