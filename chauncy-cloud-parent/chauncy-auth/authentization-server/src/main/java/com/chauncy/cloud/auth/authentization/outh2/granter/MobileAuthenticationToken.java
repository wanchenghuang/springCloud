package com.chauncy.cloud.auth.authentization.outh2.granter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * @Author cheng
 * @create 2020-04-10 14:49
 *
 * 手机验证码登陆Token认证类
 */
public class MobileAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public MobileAuthenticationToken(Authentication authenticationToken) {
        super(authenticationToken.getPrincipal(), authenticationToken.getCredentials());
    }
}

