package com.chauncy.cloud.thread.imports.service.impl;

import com.chauncy.cloud.data.domain.po.thread.ImportDataTaskPo;
import com.chauncy.cloud.data.mapper.thread.ImportDataTaskMapper;
import com.chauncy.cloud.thread.imports.service.IImportDataTaskService;
import com.chauncy.cloud.core.config.base.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 数据抽取任务表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-07-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ImportDataTaskServiceImpl extends AbstractService<ImportDataTaskMapper,ImportDataTaskPo> implements IImportDataTaskService {

 @Autowired
 private ImportDataTaskMapper mapper;

}
