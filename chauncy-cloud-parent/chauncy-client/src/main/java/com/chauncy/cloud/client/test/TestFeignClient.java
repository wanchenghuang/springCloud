package com.chauncy.cloud.client.test;

import com.chauncy.cloud.client.config.MyFeignClientConfig;
import com.chauncy.cloud.common.base.Result;
import com.chauncy.cloud.data.domain.dto.SearchUsersDto;
import com.chauncy.cloud.data.domain.vo.test.SearchUsersVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author cheng
 * @create 2020-03-15 17:17
 *
 * 测试feign调用
 */
@FeignClient(name = "web-test",path = "/test",configuration = MyFeignClientConfig.class)
public interface TestFeignClient {

    @GetMapping("/tb-user-po/query-all-user")
    Result<List<SearchUsersVo>> queryAllUsers();
//    String queryAllUsers();


    @PostMapping("/tb-user-po/search-users")
//    String searchUsers(@RequestBody SearchUsersDto searchUsersDto);
    Result<PageInfo<SearchUsersVo>> searchUsers(@RequestBody SearchUsersDto searchUsersDto);
}
