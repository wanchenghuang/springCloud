package com.chauncy.cloud.auth.authentization.service;

import com.chauncy.cloud.auth.authentization.domain.dto.LoginDto;
import com.chauncy.cloud.auth.authentization.domain.vo.Oauth2TokenVo;

/**
 * @author huangwancheng
 * @date 2021/7/1 下午2:59
 * @description  封装登录相关信息
 */
public interface IOauthService {

    Oauth2TokenVo postAccessToken(LoginDto loginDto);
}
