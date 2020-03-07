package com.chauncy.cloud.data.mapper.test;

import com.chauncy.cloud.common.annotation.TargetDataSource;
import com.chauncy.cloud.common.enums.system.TargetDataSourceEnum;
import com.chauncy.cloud.data.domain.po.test.TbUsersPo;
import com.chauncy.cloud.data.mapper.base.IBaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-03-06
 */
public interface TbUsersMapper extends IBaseMapper<TbUsersPo> {

    TbUsersPo queryUser(String username);

}
