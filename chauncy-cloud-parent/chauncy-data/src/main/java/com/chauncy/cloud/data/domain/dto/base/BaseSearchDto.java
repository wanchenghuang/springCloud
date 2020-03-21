package com.chauncy.cloud.data.domain.dto.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.cloud.data.domain.po.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2020-03-21 11:16
 */
@Data
@ApiModel(description = "分页查询")
public class BaseSearchDto<T extends BasePo> {

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime createdTimeStart;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime createdTimeEnd;

    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    public QueryWrapper<T> build(){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge(null != this.createdTimeStart, "created_time", this.createdTimeStart)
                .le(null != this.createdTimeEnd, "created_time", this.createdTimeEnd);
        return  queryWrapper;
    }

}
