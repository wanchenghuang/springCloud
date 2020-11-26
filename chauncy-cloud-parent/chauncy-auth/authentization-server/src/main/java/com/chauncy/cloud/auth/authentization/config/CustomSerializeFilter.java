// package com.chauncy.cloud.auth.authentization.config;
//
// import com.alibaba.fastjson.serializer.BeforeFilter;
// import com.alibaba.fastjson.serializer.JSONSerializer;
// import com.alibaba.fastjson.serializer.PropertyPreFilter;
// import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
// import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
//
// /**
//  * @Author: wanchenghuang
//  * @DateTime: 2020/11/2 11:30 上午
//  * @Version: 2.0.0
//  * @description: 单独使用，未解决token返回格式问题
//  **/
// public class CustomSerializeFilter extends BeforeFilter implements PropertyPreFilter {
//
// 	@Override
// 	public void writeBefore(Object object) {
// 		/**
// 		 * DefaultOAuth2AccessToken 默认格式
// 		 * {
// 		 *     "access_token": "763d17ee-7847-4d8b-b3e8-207110d094c9",
// 		 *     "token_type": "bearer",
// 		 *     "refresh_token": "68d85c17-e817-4582-95dd-169dce4a3264",
// 		 *     "expires_in": 7199,
// 		 *     "scope": "read write"
// 		 * }
// 		 */
// 		if (object instanceof DefaultOAuth2AccessToken) {
// 			DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken) object;
// 			writeKeyValue("access_token", accessToken.getValue());
// 			writeKeyValue("token_type", accessToken.getTokenType());
// 			writeKeyValue("refresh_token", accessToken.getRefreshToken().getValue());
// 			writeKeyValue("expires_in", accessToken.getExpiresIn());
// 			writeKeyValue("scope", String.join(" ", accessToken.getScope()));
// 		}
// 	}
//
//
// 	@Override
// 	public boolean apply(JSONSerializer serializer, Object object, String name) {
// 		// DefaultOAuth2AccessToken、DefaultExpiringOAuth2RefreshToken
// 		// 原先的所有属性都不进行序列化
// 		return !(object instanceof DefaultOAuth2AccessToken
// 				|| object instanceof DefaultExpiringOAuth2RefreshToken);
// 	}
// }
