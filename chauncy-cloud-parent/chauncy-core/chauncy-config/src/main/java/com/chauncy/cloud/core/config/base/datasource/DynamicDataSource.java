package com.chauncy.cloud.core.config.base.datasource;

import com.chauncy.cloud.common.holder.DataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Author cheng
 * @create 2020-03-06 14:43
 *
 * 数据源切换
 * 1、若业务层没有使用事物，则可以直接在mapper层使用注解@TargetDataSource(name = "slave1")
 * 2、若业务层使用事物管理，若不指定@Transactional(value = TransactionConfig.SECOND_TX,rollbackFor = Exception.class)
 * 事物管理，只使用@TargetDataSource(name = "slave1")，则数据源切换失败，因为事物控制会采用缺省的数据源，该注解AOP只是切换了数据源
 * 字符串，但是数据源没有被真正修改
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }

    public static void setDataSource(String dataSource){
        DataSourceContextHolder.setDataSource(dataSource);
    }

    public static String getDataSource(){
        return  DataSourceContextHolder.getDataSource();
    }

    public static void clearDataSource(){
        DataSourceContextHolder.clear();
    }
}
