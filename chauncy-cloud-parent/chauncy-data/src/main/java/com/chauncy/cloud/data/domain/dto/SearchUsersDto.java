package com.chauncy.cloud.data.domain.dto;

import com.chauncy.cloud.common.annotation.EnumConstraint;
import com.chauncy.cloud.common.enums.test.TestUserTypeEnum;
import com.chauncy.cloud.data.valid.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2020-03-06 23:40
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("测试条件分页查询用户信息")
public class SearchUsersDto {

    @ApiModelProperty("用户ID")
    @NeedExistConstraint(tableName = "tb_user")
    private Long userId;

    @ApiModelProperty("用户类型 1-app 2-商家 3-后台")
    @EnumConstraint(target = TestUserTypeEnum.class)
    private Integer type;

    @ApiModelProperty
    private String name;

    @ApiModelProperty("最早创建时间")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startTime;

    @ApiModelProperty("最晚创建时间")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endTime;

    @Min(1)
    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @Min(1)
    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

}
