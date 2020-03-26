package com.chauncy.cloud.sysadmin.organization.service;

import com.chauncy.cloud.data.domain.po.organization.ResourcePo;
import com.chauncy.cloud.core.config.base.Service;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-03-25
 */
public interface IResourceService extends Service<ResourcePo> {

    List<ResourcePo> getAll();

    List<ResourcePo> queryByName(String username);
}
