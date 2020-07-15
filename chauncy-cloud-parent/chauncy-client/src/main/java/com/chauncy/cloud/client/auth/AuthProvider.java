package com.chauncy.cloud.client.auth;

import com.chauncy.cloud.client.config.MyFeignClientConfig;
import com.chauncy.cloud.common.base.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * @Author cheng
 * @create 2020-04-14 11:14
 */
@FeignClient(name = "authentication-server", configuration = MyFeignClientConfig.class)
public interface AuthProvider {
    /**
     * 调用签权服务，判断用户是否有权限
     *
     * @param authentication
     * @param url
     * @param method
     * @return <pre>
     * Result:
     * {
     *   code:"000000"
     *   mesg:"请求成功"
     *   data: true/false
     * }
     * </pre>
     */
    @PostMapping(value = "/auth/permission")
    Result auth(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestParam("url") String url, @RequestParam("method") String method);

    /**
     * 调用签权服务，判断用户是否有权限
     *
     * @param authentication
     * @param url
     * @param method
     * @return Result
     */
    @PostMapping(value = "/auth/authenticate")
    public Result authenticate(@RequestParam String authentication,@RequestParam String url,@RequestParam String method);

    /**
     * 判断url是否在忽略的范围内
     * 只要是配置中的开头，即返回true
     *
     * @param url
     * @return
     */
    @GetMapping(value = "/auth/ignoreAuthentication")
    boolean ignoreAuthentication(@RequestParam("url") String url);

    /**
     * 查看签权服务器返回结果，有权限返回true
     *
     * @param authResult
     * @return
     */
    @PostMapping(value = "/auth/hasPermission")
    boolean hasPermission(@RequestBody Result authResult);

    /**
     * 调用签权服务，判断用户是否有权限
     *
     * @param authentication
     * @param url
     * @param method
     * @return true/false
     */
    @GetMapping(value = "/auth/hasPermission")
    boolean hasPermission(@RequestParam String authentication,@RequestParam String url,@RequestParam String method);

    /**
     * 是否无效authentication
     *
     * @param authentication
     * @return
     */
    @GetMapping(value = "/auth/invalidJwtAccessToken")
    boolean invalidJwtAccessToken(@RequestParam String authentication);

    /**
     * 从认证信息中提取jwt token 对象
     *
     * @param jwtToken toke信息 header.payload.signature
     * @return Jws对象
     */
    @GetMapping(value = "/auth/getJwt")
    Jws<Claims> getJwt(@RequestParam String jwtToken);

    @PostMapping(value = "/auth/test")
    String test(@RequestParam String name);
}
