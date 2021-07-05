package com.chauncy.cloud.auth.authentization.service.impl;

import com.chauncy.cloud.auth.authentization.domain.dto.LoginDto;
import com.chauncy.cloud.auth.authentization.domain.vo.Oauth2TokenVo;
import com.chauncy.cloud.auth.authentization.exception.AuthErrorType;
import com.chauncy.cloud.auth.authentization.exception.CustomOauthException;
import com.chauncy.cloud.auth.authentization.service.IOauthService;
import com.chauncy.cloud.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
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

        /**
         * 请求进来之前经过的过滤器
         */
        /*FilterChainProxy
        WebAsyncManagerIntegrationFilter;
        SecurityContextPersistenceFilter; --SecurityContext配置，将securityContext封装在HttpServletResponse
        HeaderWriterFilter;
        LogoutFilter;
        ClientCredentialsTokenEndpointFilter;--验证请求参数clientId
        BasicAuthenticationFilter;--解析Authorization参数获取client_id和client_secret
        RequestCacheAwareFilter;
        SecurityContextHolderAwareRequestFilter;
        AnonymousAuthenticationFilter;
        SessionManagementFilter;
        ExceptionTranslationFilter;
        FilterSecurityInterceptor;*/

        //OAuth2AuthenticationProcessingFilter


        /**
         * TokenGranter 子类
         *
         * 抽象类 AbstractTokenGranter
         *
         * 授权码模式授权 AuthorizationCodeTokenGranter
         *
         * 密码模式授权 ResourceOwnerPasswordTokenGranter
         *
         * 客户端模式授权 ClientCredentialsTokenGranter
         *
         * 简化模式授权 ImplicitTokenGranter
         *
         * 刷新token授权 RefreshTokenGranter
         */

        /**
         * 扩展字段 WebAuthenticationDetails -> WebAuthenticationDetailsSource
         */
        //AbstractTokenGranter
        //CompositeTokenGranter
        //UsernamePasswordAuthenticationFilter


        //AuthorizationEndpoint--第三方认证接口
        //DefaultRedirectStrategy
        //HttpSessionSecurityContextRepository
        //ClientDetailsUserDetailsService
        //UsernamePasswordAuthenticationToken

        /**
         *  校验client账号密码是否正确, 认证管理，可以通过实现AuthenticationProvider类自定义认证方式（token认证，Authentication）
         * {@link ProviderManager#authenticate }
         * ProviderManager.authenticate(Authentication) -> AbstractUserDetailsAuthenticationProvider.authenticate(Authentication) -> DaoAuthenticationProvider.retrieveUser(String, UsernamePasswordAuthenticationToken) -> UserDetailsService.loadUserByUsername(String)，其中UserDetailsService是接口，我们需要实现该接口，从数据库查询用户信息并匹配密码是否正确。验证通过，返回认证通过的对象UsernamePasswordAuthenticationToken[true]。
         *
         *
         *
         * 对于ClientDetailsService 、 UserDetailsService、AuthorizationServerTokenServices (默认实现类DefaultTokenServices token生成器）我们可以直接从Spring 容器中获取（启动阶段就赋值）。
         *
         * 根据client_id获取ClientDetails，有了 ClientDetails 就有了 TokenRequest，有了 TokenRequest 和 Authentication(认证后肯定有的) 就有了 OAuth2Authentication ，有了OAuth2Authentication 就能够生成 OAuth2AccessToken。
         *
         *
         */


        Map<String, String> parameters = createClientParameters(loginDto.getUsername(),loginDto.getPassword(),loginDto.getGrantType(), loginDto.getCode(), loginDto.getRedirectUri());
        try {
            OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(createClientToken(parameters), parameters).getBody();
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
            if (e instanceof OAuth2Exception){
                throw new CustomOauthException((OAuth2Exception) e);
            }
            throw new BusinessException(AuthErrorType.ACCESS_DENIED,e.getMessage());
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
    private UsernamePasswordAuthenticationToken createClientToken(Map<String, String> parameters) {
        //客户端信息,client客户端信息使用配置文件方式配置
        User u = new User("test_client", "test_secret", new ArrayList<>());
        //生成已经认证的client
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(u, null, new ArrayList<>());
        //parameters.put("password","protected");
       /* token.setDetails(parameters);
        SecurityContextHolder.clearContext();
        //如何将password设置为protect
        SecurityContextHolder.getContext().setAuthentication(token);*/
        return token;
    }
}
