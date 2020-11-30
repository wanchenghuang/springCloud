package com.chauncy.cloud.util.pdf.engine.title;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: wanchenghuang
 * @DateTime: 2020/9/8 12:25 下午
 * @Version: 2.0.0
 * @description: 用户信息打印
 **/
@Data
@ConfigurationProperties(prefix = "user", ignoreUnknownFields = false)
@PropertySource("classpath:title/user.properties")
@Component
public class TbUsersPrint {

	private String id;
	private String name;
	private String age;
	private String salary;
}
