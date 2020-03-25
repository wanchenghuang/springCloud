package com.chauncy.cloud.sysadmin.organization.service.impl;

import com.chauncy.cloud.data.domain.po.organization.MenuPo;
import com.chauncy.cloud.data.mapper.organization.MenuMapper;
import com.chauncy.cloud.sysadmin.organization.service.IMenuService;
import com.chauncy.cloud.core.config.base.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class MenuServiceImpl extends AbstractService<MenuMapper,MenuPo> implements IMenuService {

 @Autowired
 private MenuMapper mapper;

}
