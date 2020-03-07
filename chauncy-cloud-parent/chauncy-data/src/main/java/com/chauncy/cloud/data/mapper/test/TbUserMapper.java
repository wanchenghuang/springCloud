package com.chauncy.cloud.data.mapper.test;

import com.chauncy.cloud.data.domain.dto.SearchUsersDto;
import com.chauncy.cloud.data.domain.po.test.TbUserPo;
import com.chauncy.cloud.data.domain.vo.test.SearchUsersVo;
import com.chauncy.cloud.data.mapper.base.IBaseMapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-03-06
 */
public interface TbUserMapper extends IBaseMapper<TbUserPo> {

    List<SearchUsersVo> queryAllUsers();

    /**
     * 测试分页查询
     *
     * @param SearchUsersDto
     * @return
     */
    List<SearchUsersVo> searchUsers(SearchUsersDto SearchUsersDto);
}

