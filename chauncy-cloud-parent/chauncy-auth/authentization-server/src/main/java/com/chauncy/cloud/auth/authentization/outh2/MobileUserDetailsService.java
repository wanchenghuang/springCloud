package com.chauncy.cloud.auth.authentization.outh2;

import com.chauncy.cloud.auth.authentization.service.IUserService;
import com.chauncy.cloud.client.sms.SmsClient;
import com.chauncy.cloud.data.domain.po.organization.UsersPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @Author cheng
 * @create 2020-04-10 14:52
 */
@Slf4j
@Service("mobileUserDetailsService")
public class MobileUserDetailsService extends CustomUserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private SmsClient smsCodeProvider;

    @Override
    public UserDetails loadUserByUsername(String uniqueId) {

        UsersPo user = userService.getByUniqueId(uniqueId);
        log.info("load user by mobile:{}", user.toString());

        // 如果为mobile模式，从短信服务中获取验证码（动态密码）
        String credentials = smsCodeProvider.getSmsCode(uniqueId, "LOGIN");

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                credentials,
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNonLocked(),
                super.obtainGrantedAuthorities(user));
    }
}