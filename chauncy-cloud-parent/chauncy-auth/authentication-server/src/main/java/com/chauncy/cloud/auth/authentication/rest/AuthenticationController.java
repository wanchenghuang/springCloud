package com.chauncy.cloud.auth.authentication.rest;

import com.chauncy.cloud.auth.authentication.service.IAuthService;
import com.chauncy.cloud.auth.authentication.service.IAuthenticationService;
import com.chauncy.cloud.common.base.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author cheng
 * @create 2020-04-14 09:37
 */
@RestController
@Api("auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    IAuthenticationService authenticationService;

    @Autowired
    IAuthService authService;

    @ApiOperation(value = "权限验证", notes = "根据用户token，访问的url和method判断用户是否有权限访问")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "url", value = "访问的url", required = true, dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "method", value = "访问的method", required = true, dataType = "string")
    })
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功", response = Result.class))
    @PostMapping(value = "/auth/permission")
    public Result decide(@RequestParam String url, @RequestParam String method, HttpServletRequest request) {
        boolean decide = authenticationService.decide(new HttpServletRequestAuthWrapper(request, url, method));
        return Result.success(decide);
    }

    @ApiOperation(value = "测试")
    @PostMapping(value = "/auth/test")
    public String test(@RequestParam String name){

        return authService.test(name);
    }

    /**
     * 调用签权服务，判断用户是否有权限
     *
     * @param authentication
     * @param url
     * @param method
     * @return Result
     */
    @ApiOperation(value = "判断权限", notes = "调用签权服务，判断用户是否有权限")
    @PostMapping(value = "/auth/authenticate")
    public Result authenticate(@RequestParam String authentication,@RequestParam String url,@RequestParam String method){

        return authService.authenticate(authentication,url,method);
    }

    /**
     * 判断url是否在忽略的范围内
     * 只要是配置中的开头，即返回true
     *
     * @param url
     * @return
     */
    @ApiOperation(value = "判断url是否在忽略的范围内", notes = "只要是配置中的开头，即返回true")
    @GetMapping(value = "/auth/ignoreAuthentication")
    boolean ignoreAuthentication(@RequestParam("url") String url){
        return authService.ignoreAuthentication(url);
    }

    /**
     * 查看签权服务器返回结果，有权限返回true
     *
     * @param authResult
     * @return
     */
    @ApiOperation(value = "查看签权服务器返回结果，有权限返回true")
    @PostMapping(value = "/auth/hasPermission")
    boolean hasPermission(@RequestBody Result authResult){
        return authService.hasPermission(authResult);
    }

    /**
     * 调用签权服务，判断用户是否有权限
     *
     * @param authentication
     * @param url
     * @param method
     * @return true/false
     */
    @ApiOperation(value = "调用签权服务，判断用户是否有权限")
    @GetMapping(value = "/auth/hasPermission")
    boolean hasPermission(@RequestParam String authentication,@RequestParam String url,@RequestParam String method){
        return authService.hasPermission(authentication,url,method);
    }

    /**
     * 是否无效authentication
     *
     * @param authentication
     * @return
     */
    @ApiOperation(value = "是否无效authentication")
    @GetMapping(value = "/auth/invalidJwtAccessToken")
    boolean invalidJwtAccessToken(@RequestParam String authentication){
        return authService.invalidJwtAccessToken(authentication);
    }

    /**
     * 从认证信息中提取jwt token 对象
     *
     * @param jwtToken toke信息 header.payload.signature
     * @return Jws对象
     */
    @ApiOperation(value = "从认证信息中提取jwt token 对象")
    @GetMapping(value = "/auth/getJwt")
    Jws<Claims> getJwt(@RequestParam String jwtToken){
        return authService.getJwt(jwtToken);
    }

}