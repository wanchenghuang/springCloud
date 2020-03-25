package com.chauncy.cloud.sysadmin.organization.service.impl;

import com.chauncy.cloud.data.domain.po.organization.ResourcePo;
import com.chauncy.cloud.data.mapper.organization.ResourceMapper;
import com.chauncy.cloud.sysadmin.organization.service.IResourceService;
import com.chauncy.cloud.core.config.base.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ResourceServiceImpl extends AbstractService<ResourceMapper,ResourcePo> implements IResourceService {

 @Autowired
 private ResourceMapper mapper;

}
