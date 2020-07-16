package com.chauncy.cloud.data.domain.po.thread;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.chauncy.cloud.data.domain.po.BasePo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据抽取任务表
 * </p>
 *
 * @author admin
 * @since 2020-07-16
 */
@Data
@Accessors(chain = true)
@TableName("import_data_task")
@ApiModel(value = "ImportDataTaskPo对象", description = "数据抽取任务表")
public class ImportDataTaskPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "抽取数据日期")
    private String day;

    @ApiModelProperty(value = "1:point 2:point_log 3:user_loan_record 4:finance_plan_user_stat 5:user_stat")
    private String type;

    @ApiModelProperty(value = "0 未处理,1已处理")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "版本号")
    private Integer version;


}
