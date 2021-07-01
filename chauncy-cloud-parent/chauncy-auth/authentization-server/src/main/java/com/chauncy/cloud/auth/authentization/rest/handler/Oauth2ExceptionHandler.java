package com.chauncy.cloud.auth.authentization.rest.handler;

import com.chauncy.cloud.common.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author huangwancheng
 * @date 2021/7/1 上午9:45
 * @description
 */
@RestControllerAdvice
@Slf4j
public class Oauth2ExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public Result handleOauth2(OAuth2Exception e) {
        return Result.error(e.getMessage());
    }
}
