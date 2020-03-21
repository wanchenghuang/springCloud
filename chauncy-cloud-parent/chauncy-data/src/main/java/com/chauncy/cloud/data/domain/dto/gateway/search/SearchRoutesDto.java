package com.chauncy.cloud.data.domain.dto.gateway.search;

import com.chauncy.cloud.data.domain.dto.base.BaseSearchDto;
import com.chauncy.cloud.data.domain.po.gateway.GatewayRoutePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

/**
 * @Author cheng
 * @create 2020-03-21 12:34
 */
@ApiModel(description = "条件查询分页routes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SearchRoutesDto extends BaseSearchDto<GatewayRoutePo> {

    @ApiModelProperty(value = "路由uri")
    private String uri;

}
