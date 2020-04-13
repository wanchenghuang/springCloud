package com.chauncy.cloud.auth.authentization.outh2;

import com.chauncy.cloud.auth.authentization.service.IRoleService;
import com.chauncy.cloud.auth.authentization.service.IUserService;
import com.chauncy.cloud.data.domain.po.organization.RolesPo;
import com.chauncy.cloud.data.domain.po.organization.UsersPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author cheng
 * @create 2020-04-10 14:28
 *
 * 自定义用户详情
 */
@Slf4j
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String uniqueId) {
        UsersPo user = userService.getByUniqueId(uniqueId);
        log.info("load user by username :{}", user.toString());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNonLocked(),
                this.obtainGrantedAuthorities(user));
    }

    /**
     * 获得登录者所有角色的权限集合.
     *
     * @param user
     * @return
     */
    protected Set<GrantedAuthority> obtainGrantedAuthorities(UsersPo user) {
        Set<RolesPo
                > roles = roleService.queryUserRolesByUserId(user.getId());
        log.info("user:{},roles:{}", user.getUsername(), roles);
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet());
    }
}

