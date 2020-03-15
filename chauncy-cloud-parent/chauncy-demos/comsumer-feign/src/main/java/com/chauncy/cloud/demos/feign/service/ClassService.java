package com.chauncy.cloud.demos.feign.service;

import com.chauncy.cloud.data.domain.dto.SearchUsersDto;
import com.chauncy.cloud.data.domain.vo.test.SearchUsersVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author cheng
 * @create 2020-03-15 16:57
 */
public interface ClassService {


    List<SearchUsersVo> queryAllUsers();

    PageInfo<SearchUsersVo> searchUsers(SearchUsersDto searchUsersDto);
}
