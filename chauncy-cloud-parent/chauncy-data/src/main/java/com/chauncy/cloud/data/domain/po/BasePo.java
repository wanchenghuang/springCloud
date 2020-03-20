package com.chauncy.cloud.data.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author cheng
 * @create 2020-03-19 22:20
 */
@Data
public class BasePo implements Serializable {
    public final static String DEFAULT_USERNAME = "system";

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;
}
