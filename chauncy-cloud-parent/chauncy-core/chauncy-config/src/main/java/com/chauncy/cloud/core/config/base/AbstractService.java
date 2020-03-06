package com.chauncy.cloud.core.config.base;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chauncy.cloud.data.mapper.base.IBaseMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @Author cheng
 * @create 2020-03-06 00:07
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<M extends BaseMapper<T>,T> extends ServiceImpl<M, T> implements Service<T> {

    @Autowired
    private IBaseMapper<T> iBaseMapper;

}