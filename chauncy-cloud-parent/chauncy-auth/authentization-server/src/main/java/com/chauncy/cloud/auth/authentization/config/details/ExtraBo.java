package com.chauncy.cloud.auth.authentization.config.details;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author huangwancheng
 * @date 2021/7/5 下午4:57
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ExtraBo {

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
