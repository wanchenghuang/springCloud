package com.chauncy.cloud.auth.authentization.service.impl;

import com.chauncy.cloud.auth.authentization.domain.dto.LoginDto;
import com.chauncy.cloud.auth.authentization.domain.vo.Oauth2TokenVo;
import com.chauncy.cloud.auth.authentization.service.IOauthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author huangwancheng
 * @date 2021/7/1 下午2:59
 * @description
 */
@Service
@Slf4j
public class IOauthServiceImpl implements IOauthService {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Override
    public Oauth2TokenVo postAccessToken(LoginDto loginDto) {

        Oauth2TokenVo oauth2TokenVo = Oauth2TokenVo.builder().build();
        //AuthorizationEndpoint
        //DefaultRedirectStrategy

        Map<String, String> parameters = createClientParameters(loginDto.getUsername(),loginDto.getPassword(),loginDto.getGrantType(), loginDto.getCode(), loginDto.getRedirectUri());

        try {
            OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(createClientToken(), parameters).getBody();
            oauth2TokenVo = Oauth2TokenVo.builder()
                    .token(oAuth2AccessToken.getValue())
                    .tokenType(oAuth2AccessToken.getTokenType())
                    .tokenHead(oAuth2AccessToken.BEARER_TYPE)
                    .refreshToken(Objects.nonNull(oAuth2AccessToken.getRefreshToken()) ? oAuth2AccessToken.getRefreshToken().getValue() : null)
                    .expiresIn(oAuth2AccessToken.getExpiresIn())
                    .scope(String.join(" ", oAuth2AccessToken.getScope()))
                    .additionalInformation(oAuth2AccessToken.getAdditionalInformation()).build();


        } catch (Exception e) {
            log.error("登录失败！！" + e.getMessage());
        }

        return oauth2TokenVo;
    }

    //构造客户端请求
    private Map<String, String> createClientParameters(String username, String password,String grantType,String code, String redirectUri) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("username", username);
        parameters.put("password", password);
        parameters.put("grant_type", grantType);
        parameters.put("redirect_uri", redirectUri);
        parameters.put("code", code);
        return parameters;
    }

    //构造客户端Token
    private UsernamePasswordAuthenticationToken createClientToken() {
        //客户端信息
        User u = new User("test_client", "test_secret", new ArrayList<>());
        //生成已经认证的client
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(u, null, new ArrayList<>());
        return token;
    }
}
