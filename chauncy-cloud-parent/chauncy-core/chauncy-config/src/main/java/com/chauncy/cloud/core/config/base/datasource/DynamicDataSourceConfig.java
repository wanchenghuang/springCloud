package com.chauncy.cloud.core.config.base.datasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.chauncy.cloud.common.constant.Constants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author cheng
 * @create 2020-03-06 14:37
 */
@Configuration
public class DynamicDataSourceConfig {

    @Bean(name = "master")
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "slave1")
    @ConfigurationProperties("spring.datasource.druid.slave1")
    public DataSource slave1DataSource() {
        return DruidDataSourceBuilder.create().build();
    }


    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier(Constants.MASTER)DataSource mysqlDataSource, @Qualifier(Constants.SLAVE1)DataSource slave1DataSource) {
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(Constants.MASTER, mysqlDataSource);
        targetDataSource.put(Constants.SLAVE1, slave1DataSource);
        return new DynamicDataSource(mysqlDataSource, targetDataSource);
    }

    @Bean
    public ServletRegistrationBean startViewServlet(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        // IP白名单
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        // IP黑名单(共同存在时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny","127.0.0.1");
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername","admin");
        servletRegistrationBean.addInitParameter("loginPassword","123456");
        //是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean statFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //忽略过滤的格式
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
