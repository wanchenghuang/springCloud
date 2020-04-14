package com.chauncy.cloud.auth.authentication.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author cheng
 * @create 2020-04-14 09:39
 */
public interface IAuthenticationService {
    /**
     * 校验权限
     *
     * @param authRequest
     * @return 是否有权限
     */
    boolean decide(HttpServletRequest authRequest);

}
