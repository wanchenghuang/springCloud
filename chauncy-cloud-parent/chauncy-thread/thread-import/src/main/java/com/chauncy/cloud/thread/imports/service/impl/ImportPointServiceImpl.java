package com.chauncy.cloud.thread.imports.service.impl;

import com.chauncy.cloud.data.domain.po.thread.ImportPointPo;
import com.chauncy.cloud.data.mapper.thread.ImportPointMapper;
import com.chauncy.cloud.thread.imports.service.IImportPointService;
import com.chauncy.cloud.core.config.base.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-07-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ImportPointServiceImpl extends AbstractService<ImportPointMapper,ImportPointPo> implements IImportPointService {

 @Autowired
 private ImportPointMapper mapper;

}
