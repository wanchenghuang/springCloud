package com.chauncy.cloud.demos.feign.service.impl;

import com.chauncy.cloud.client.test.TestFeignClient;
import com.chauncy.cloud.common.base.Result;
import com.chauncy.cloud.common.utils.BeanTransferUtils;
import com.chauncy.cloud.common.utils.JSONUtils;
import com.chauncy.cloud.data.domain.dto.SearchUsersDto;
import com.chauncy.cloud.data.domain.vo.test.SearchUsersVo;
import com.chauncy.cloud.demos.feign.service.ClassService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author cheng
 * @create 2020-03-15 16:58
 */
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private TestFeignClient testFeignClient;

    @Override
    public List<SearchUsersVo> queryAllUsers() {

//        String result = testFeignClient.queryAllUsers();
//        List<SearchUsersVo> users = JSONUtils.parseObject(JSONUtils.parseObject(result,Result.class).getData().toString(),List.class);
//
//        return users;
//
        Result<List<SearchUsersVo>> a = testFeignClient.queryAllUsers();
        return a.getData();
    }

    @Override
    public PageInfo<SearchUsersVo> searchUsers(SearchUsersDto searchUsersDto) {

//        String result = testFeignClient.searchUsers(searchUsersDto);
        Result<PageInfo<SearchUsersVo>> result = testFeignClient.searchUsers(searchUsersDto);
        return result.getData();
    }

    @Override
    public String webFeignTimeout() {
        return testFeignClient.webFeignTimeout();
    }
}
