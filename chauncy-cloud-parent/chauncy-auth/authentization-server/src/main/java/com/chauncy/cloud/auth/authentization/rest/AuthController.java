package com.chauncy.cloud.auth.authentization.rest;

import com.chauncy.cloud.auth.authentization.domain.dto.LoginDto;
import com.chauncy.cloud.auth.authentization.domain.vo.Oauth2TokenVo;
import com.chauncy.cloud.auth.authentization.service.IOauthService;
import com.chauncy.cloud.common.base.BaseController;
import com.chauncy.cloud.common.base.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangwancheng
 * @date 2021/7/1 上午9:22
 * @description
 */
@RestController
@RequestMapping("/oauth")
public class AuthController extends BaseController {

    @Autowired
    private IOauthService oauthService;

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    @ApiResponses(@ApiResponse(code = 200, message = "处理成功", response = Result.class))
    public Result<Oauth2TokenVo> postAccessToken(@RequestBody @ApiParam(required = true,name = "loginDto",value = "登录信息") LoginDto loginDto) throws HttpRequestMethodNotSupportedException {

        return success(oauthService.postAccessToken(loginDto));
    }
}
