package com.chauncy.cloud.gateway.web.service;

/**
 * @Author cheng
 * @create 2020-04-14 11:12
 */
public interface IPermissionService {
    /**
     * @param authentication
     * @param method
     * @param url
     * @return
     */
    boolean permission(String authentication, String url, String method);
}