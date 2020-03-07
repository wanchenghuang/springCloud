package com.chauncy.cloud.demos.web.service;

import com.chauncy.cloud.data.domain.dto.SaveTestUsersDto;
import com.chauncy.cloud.data.domain.dto.SearchUsersDto;
import com.chauncy.cloud.data.domain.po.test.TbUserPo;
import com.chauncy.cloud.core.config.base.Service;
import com.chauncy.cloud.data.domain.po.test.TbUsersPo;
import com.chauncy.cloud.data.domain.vo.test.SearchUsersVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-03-06
 */
public interface ITbUserService extends Service<TbUserPo> {

    List<SearchUsersVo> queryAllUsers();

    void test(String username);

    TbUsersPo queryUser(String username);

    void delByIds(List<Long> ids);

    PageInfo<SearchUsersVo> searchUsers(SearchUsersDto searchUsersDto);

    void saveUser(SaveTestUsersDto saveTestUsersDto);
}
