package com.chauncy.cloud.data.domain.po.thread;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.chauncy.cloud.data.domain.po.BasePo;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("import_point")
@ApiModel(value = "ImportPointPo对象", description = "")
public class ImportPointPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "day", type = IdType.ID_WORKER)
    private String day;

    @TableField("userId")
    private Integer userId;

    @TableField("availablePoints")
    private BigDecimal availablePoints;

    @TableField("frozenPoints")
    private BigDecimal frozenPoints;

    @ApiModelProperty(value = "数据库创建时间")
    private LocalDateTime createTime;


}
