package com.chauncy.cloud.data.domain.dto;

import com.chauncy.cloud.common.annotation.EnumConstraint;
import com.chauncy.cloud.common.enums.test.TestUserTypeEnum;
import com.chauncy.cloud.data.valid.ISaveGroup;
import com.chauncy.cloud.data.valid.IUpdateGroup;
import com.chauncy.cloud.data.valid.NeedExistConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @Author cheng
 * @create 2020-03-07 15:57
 */
@Data
@ApiModel(description = "保存用户--测试事务和异常处理")
@Accessors(chain = true)
public class SaveTestUsersDto {

    @ApiModelProperty(value = "姓名")
    @NeedExistConstraint(groups = ISaveGroup.class,tableName = "tb_user",isNeedExists = false,field = "name",message = "该名称已存在")
    @NeedExistConstraint(groups = IUpdateGroup.class,tableName = "tb_user",field = "name",message = "该名称不存在")
    @NotNull
    private String name;

    @ApiModelProperty(value = "年龄")
    @Min(1)
    @Max(150)
    @NotNull(message = "年龄不能为空")
    private Integer age;

    @ApiModelProperty(value = "薪资")
    @Min(1)
    @NotNull(message = "薪资不能为空")
    private Double salary;

    @ApiModelProperty(value = "用户类型 1-app 2-商家 3-后台")
    @EnumConstraint(target = TestUserTypeEnum.class)
    @NotNull(message = "用户类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "入职时间")
    @Future(message = "入职时间需要在当前时间之后")
    @NotNull(message = "入职时间不能为空")
    private LocalDateTime entryTime;

}

