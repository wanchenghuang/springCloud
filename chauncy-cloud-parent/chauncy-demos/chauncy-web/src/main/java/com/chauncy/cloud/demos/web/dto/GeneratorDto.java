package com.chauncy.cloud.demos.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author cheng
 * @create 2020-03-24 22:03
 */
@ApiModel(description = "自动生成代码")
@Data
public class GeneratorDto {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "模块名称")
    @NotBlank(message = "模块名称不能为空")
    private String modelName;

    @ApiModelProperty(value = "数据库")
    @NotBlank(message = "数据库不能为空")
    private String database;

    @ApiModelProperty(value = "表名")
    @NotBlank(message = "表名不能为空")
    private String tableName;

    @ApiModelProperty(value = "ip")
    @NotBlank(message = "ip不能为空")
    private String host;

    @ApiModelProperty(value = "端口")
    @NotNull(message = "端口不能为空")
    private Long port;
}
