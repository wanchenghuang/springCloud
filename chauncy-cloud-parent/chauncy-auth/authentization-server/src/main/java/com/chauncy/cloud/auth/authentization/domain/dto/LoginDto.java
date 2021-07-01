package com.chauncy.cloud.auth.authentization.domain.dto;

import lombok.Data;

/**
 * @author huangwancheng
 * @date 2021/7/1 下午3:04
 * @description
 */
@Data
public class LoginDto {

    /**
     * 用户
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 验证码
     */
    private String verifyCode;
    /**
     *
     */
    private String randomid;
    /**
     * 授权码
     */
    private String code;

    /**
     * 授权类型
     */
    private String grantType;
    /**
     * 范围
     */
    private String cope;
    /**
     * 跳转路径
     */
    private String redirectUri;
}
