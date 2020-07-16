package com.chauncy.cloud.data.domain.po.thread;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.chauncy.cloud.data.domain.po.BasePo;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author admin
 * @since 2020-07-16
 */
@Data
@Accessors(chain = true)
@TableName("point")
@ApiModel(value = "PointPo对象", description = "")
public class PointPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pointId", type = IdType.AUTO)
    private Integer pointId;

    @TableField("availablePoints")
    private BigDecimal availablePoints;

    @TableField("frozenPoints")
    private BigDecimal frozenPoints;

    private Integer version;

    private Integer user;

    private LocalDateTime lastUpdateTime;

    @ApiModelProperty(value = "是否开启延迟更新模式")
    @TableField("delayUpdateMode")
    private Integer delayUpdateMode;

    @ApiModelProperty(value = "上次更新时最后一条log的id")
    private Integer latestPointLogId;


}
