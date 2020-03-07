package com.chauncy.cloud.demos.web.service.impl;

import com.chauncy.cloud.data.domain.po.test.TbUsersPo;
import com.chauncy.cloud.data.mapper.test.TbUsersMapper;
import com.chauncy.cloud.core.config.base.AbstractService;
import com.chauncy.cloud.demos.web.service.ITbUsersService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-03-06
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class TbUsersServiceImpl extends AbstractService<TbUsersMapper, TbUsersPo> implements ITbUsersService {

    @Autowired
    private TbUsersMapper mapper;

}
