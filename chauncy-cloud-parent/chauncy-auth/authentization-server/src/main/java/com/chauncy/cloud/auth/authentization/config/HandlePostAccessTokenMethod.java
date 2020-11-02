package com.chauncy.cloud.auth.authentization.config;

import com.baomidou.mybatisplus.extension.api.R;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wanchenghuang
 * @DateTime: 2020/11/2 12:30 下午
 * @Version: 2.0.0
 * @description:
 * 方法二：解决由于fastjson导致返回格式问题，本项目使用方法一：使用webMvc配置
 *
 * 使用aop 拦截增强 /oauth/token 接口
 *
 * DefaultOAuth2AccessToken 默认格式
 *          {
 * 		      "access_token": "763d17ee-7847-4d8b-b3e8-207110d094c9",
 * 		      "token_type": "bearer",
 * 		      "refresh_token": "68d85c17-e817-4582-95dd-169dce4a3264",
 * 		      "expires_in": 7199,
 * 		      "scope": "read write"
 *          }
 *
 * 错误格式:原因   使用fastjson而不是默认的Jackson
 *
 * {
 *     "additionalInformation": {},
 *     "expiration": "2019-03-06 15:49:06",
 *     "expired": false,
 *     "expiresIn": 2141,
 *     "refreshToken": {
 *         "expiration": "2019-03-06 16:49:06",
 *         "value": "35cca126-928a-4429-8bd7-86afb0250559"
 *     },
 *     "scope": [
 *         "mgtapi_admininfo"
 *     ],
 *     "tokenType": "bearer",
 *     "value": "f7d77b3f-61f1-4c1e-975e-c6b3bb5f244d"
 * }
 *
 *
 **/
// @Aspect
// @Component
public class HandlePostAccessTokenMethod {

	// @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
	public Object handlePostAccessTokenMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		// 获取原有值，进行包装返回
		Object proceed = joinPoint.proceed();

		ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
		OAuth2AccessToken body = responseEntity.getBody();

		Map<String, Object> data = new HashMap<>(8);
		//access_token
		data.put(OAuth2AccessToken.ACCESS_TOKEN, body.getValue());
		//token_type
		data.put(OAuth2AccessToken.TOKEN_TYPE, body.getTokenType());
		//refresh_token
		data.put(OAuth2AccessToken.REFRESH_TOKEN, String.join(" ", body.getRefreshToken().getValue()));
		//expires_in
		data.put(OAuth2AccessToken.EXPIRES_IN, body.getExpiresIn());
		//scope
		data.put(OAuth2AccessToken.SCOPE, String.join(" ", body.getScope()));
		//自定义额外信息
		body.getAdditionalInformation().forEach((k,v) ->{
			data.put(k, v);
		});

		// return data;
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(data);
				// .body(R.ok(body));
	}
}
