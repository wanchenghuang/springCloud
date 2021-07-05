package com.chauncy.cloud.auth.authentization.config.details;

import com.alibaba.fastjson.JSONObject;
import com.chauncy.cloud.common.enums.system.exception.Code;
import com.chauncy.cloud.common.exception.BusinessException;
import com.chauncy.cloud.common.utils.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 登录验证时，附带增加额外的数据，如验证码、用户类型、手机号码
 *
 * @Author cheng
 * @Date 2021/07/05 14:38
 **/
@Slf4j
@Getter
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    //额外数据实体
    private ExtraBo extraBo;

    public MyWebAuthenticationDetails(HttpServletRequest request) {

        super(request);

        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream jsonData = request.getInputStream()) {

            Map<String, String> jsonAuthenticationBean = objectMapper.readValue(jsonData, Map.class);
            log.info(new Gson().toJson(jsonAuthenticationBean));

            extraBo = JSONUtils.toBean(JSONObject.toJSONString(jsonAuthenticationBean), ExtraBo.class);

        } catch (Exception e) {
            //异常处理
            log.error(e.getMessage());
            if (e instanceof IOException) {
                throw new BusinessException(Code.BODY_MISS, "根据输入流获取json来获取额外信息异常!");
            }
        }
    }
}
