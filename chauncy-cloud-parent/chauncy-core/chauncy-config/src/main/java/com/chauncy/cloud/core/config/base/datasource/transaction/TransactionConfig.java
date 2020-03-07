//package com.chauncy.cloud.core.config.base.datasource.transaction;
//
//import com.chauncy.cloud.common.constant.Constants;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
///**
// * @Author cheng
// * @create 2020-03-07 19:21
// *
// * 第二数据源事务控制处理
// *
// * 多事物管理器配置
// * 定义事务管理器 并指定其对应管理的数据源和声明name
// *
// * 不配置事物管理器，则默认数据源
// */
//@Configuration
//@EnableTransactionManagement(order=2)
//public class TransactionConfig {
//
//    public final static String DEFAULT_TX = "defaultTx";
//
//    public final static String SECOND_TX = "slave1TX";
//
//
//    @Bean(name = TransactionConfig.DEFAULT_TX)
//    @Primary
//    public DataSourceTransactionManager transaction(@Qualifier(Constants.MASTER) DataSource firstDataSource) {
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(firstDataSource);
//        return dataSourceTransactionManager;
//    }
//
//    @Bean(name = TransactionConfig.SECOND_TX)
//    public DataSourceTransactionManager rongyuanTransaction(@Qualifier(Constants.SLAVE1) DataSource rongyuanDataScoure) {
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(rongyuanDataScoure);
//        return dataSourceTransactionManager;
//    }
//}