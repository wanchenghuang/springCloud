package com.chauncy.cloud.auth.authentization.domain.vo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author huangwancheng
 * @date 2021/7/1 上午9:22
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Oauth2TokenVo {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 访问令牌头前缀
     */
    private String tokenHead;
    /**
     * 有效时间（秒）
     */
    private int expiresIn;

    /**
     * token类型
     */
    private String tokenType;
    /**
     * 范围
     */
    private String scope;
    /**
     * 额外字段
     */
    Map<String, Object> additionalInformation;
}
