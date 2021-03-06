package com.chauncy.cloud.auth.authentization.config;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;

import java.io.IOException;

/**
 * @Author: wanchenghuang
 * @DateTime: 2020/11/2 12:46 下午
 * @Version: 2.0.0
 * @description:
 *
 * 在Spring框架中，自定义配置了 FastJsonHttpMessageConverter ，覆盖掉 MappingJackson2HttpMessageConverter，导致返回时 序列化出现问题
 *
 * 自定义一个消息转换器，并让MappingJackson 消息转换器来处理Oauth2AccessToken的类返回信息
 *
 * 解决token返回格式问题
 **/
public class Oauth2HttpMessageConverter extends AbstractHttpMessageConverter<DefaultOAuth2AccessToken> {

	private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

	public Oauth2HttpMessageConverter() {
		super(MediaType.APPLICATION_JSON_UTF8);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return clazz.equals(DefaultOAuth2AccessToken.class);
	}

	@Override
	protected DefaultOAuth2AccessToken readInternal(Class<? extends DefaultOAuth2AccessToken> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		throw new UnsupportedOperationException(
				"This converter is only used for converting DefaultOAuth2AccessToken to json.");
	}

	@Override
	protected void writeInternal(DefaultOAuth2AccessToken accessToken, HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		mappingJackson2HttpMessageConverter.write(accessToken, MediaType.APPLICATION_JSON_UTF8, outputMessage);
	}
}