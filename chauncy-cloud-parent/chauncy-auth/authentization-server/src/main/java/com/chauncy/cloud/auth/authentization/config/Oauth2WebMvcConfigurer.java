package com.chauncy.cloud.auth.authentization.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author: wanchenghuang
 * @DateTime: 2020/11/2 11:20 上午
 * @Version: 2.0.0
 * @description: TODO
 **/
@Configuration
public class Oauth2WebMvcConfigurer extends WebMvcConfigurationSupport {

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
	    Oauth2HttpMessageConverter oauth2HttpMessageConverter = new Oauth2HttpMessageConverter();
	    converters.add(0, oauth2HttpMessageConverter);
	    // converters.add(0, new OAuth2AccessTokenMessageConverter());
	    super.configureMessageConverters(converters);
	}
}
