package com.chauncy.cloud.data.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author cheng
 * @create 2020-03-06 00:09
 * 自定义 MyBatis IBaseMapper
 */
public interface IBaseMapper<T> extends BaseMapper<T> {

    /**
     *判断id是否存在
     * @param value 值
     * @param tableName 表名称
     * @param fields 数据要过滤的字段,多个用逗号隔开  如 id=#{value}
     * @return
     */
    int countById(@Param("value") Object value,
                  @Param("tableName") String tableName,
                  @Param("field")String fields,
                  @Param("concatWhereSql") String concatWhereSql
    );



    Map<String, Object> findByUserName(String username);

}