package com.chauncy.cloud.auth.authentization.outh2.granter;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author cheng
 * @create 2020-04-10 14:48
 *
 * 手机验证码登陆provider
 */
public class MobileAuthenticationProvider extends DaoAuthenticationProvider {

    public MobileAuthenticationProvider(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

