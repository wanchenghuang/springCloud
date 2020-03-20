package com.chauncy.cloud.data.domain.dto.gateway;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author cheng
 * @create 2020-03-19 22:01
 */
@EqualsAndHashCode
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "网关过滤器信息")
public class FilterDefinitionDto {

    @ApiModelProperty(value = "网关过滤器名称")
    @NotBlank(message = "网关过滤器名称不能为空")
    private String name;

    @ApiModelProperty(value = "网关过滤器参数")
    @NotBlank(message = "网关过滤器参数不能为空")
    private Map<String, String> args = new LinkedHashMap<>();

}
