package com.chauncy.cloud.client.organization;

import com.chauncy.cloud.client.config.MyFeignClientConfig;
import com.chauncy.cloud.common.base.Result;
import com.chauncy.cloud.data.domain.po.organization.RolesPo;
import com.chauncy.cloud.data.domain.po.organization.UsersPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @Author cheng
 * @create 2020-04-10 14:35
 */
@FeignClient(name = "organization",configuration = MyFeignClientConfig.class)
public interface OrganizationClient {

    @GetMapping(value = "/user")
    Result<UsersPo> getUserByUniqueId(@RequestParam("uniqueId") String uniqueId);

    @GetMapping(value = "/role/user/{userId}")
    Result<Set<RolesPo>> queryRolesByUserId(@PathVariable("userId") String userId);

}
