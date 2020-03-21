package com.chauncy.cloud.data.domain.dto.base;

import com.chauncy.cloud.data.domain.po.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * @Author cheng
 * @create 2020-03-19 22:16
 */
@ApiModel(description = "基础Dto")
@Data
@Slf4j
public abstract class BaseDto<T extends BasePo> {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * dto转化为Po，进行后续业务处理
     *
     * @param clazz
     * @return
     */
    public T toPo(Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, t);
        return t;
    }

    /**
     * dto转化为Po，进行后续业务处理
     *
     * @param id
     * @param clazz
     * @return
     */
    public T toPo(Long id, Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        t.setId(id);
        BeanUtils.copyProperties(this, t);
        return t;
    }
}
