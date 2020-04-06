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
@FeignClient(name = "web-test",/*path = "/test",*/configuration = MyFeignClientConfig.class)
public interface TestFeignClient {

    @GetMapping("/tb-user-po/query-all-user")
    Result<List<SearchUsersVo>> queryAllUsers();
//    String queryAllUsers();


    @PostMapping("/tb-user-po/search-users")
//    String searchUsers(@RequestBody SearchUsersDto searchUsersDto);
    Result<PageInfo<SearchUsersVo>> searchUsers(@RequestBody SearchUsersDto searchUsersDto);

    /**
     * @Author chauncy
     * @Date 2020-04-06 20:30
     * @param
     * @return
     *   默认feign客户端只等待一秒钟，但是服务端处理需要超过1秒钟导致feign客户端直接返回错误
     *
     *   需要设置feign客户端超时，由ribbon控制
     *
     **/
    @GetMapping("/tb-user-po/web/feign/timeout")
    String webFeignTimeout();
}
