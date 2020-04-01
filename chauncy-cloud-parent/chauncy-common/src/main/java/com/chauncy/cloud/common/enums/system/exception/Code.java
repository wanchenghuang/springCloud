package com.chauncy.cloud.common.enums.system.exception;

import com.chauncy.cloud.common.base.BaseCode;
import lombok.AllArgsConstructor;

/**
 * @Author cheng
 * @create 2020-03-05 15:58
 */
@AllArgsConstructor
public enum Code implements BaseCode {

    SUCCESS(200, "成功"),

    BAD_REQUEST(400, "错误的请求"),
    UNAUTHORIZED(401, "无权限访问"),
    NOT_FOUND(404, "找不到对应请求"),
    METHOD_NOT_ALLOWED(405, "不支持的请求方法"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持当前媒体类型"),

    ERROR(500, "系统繁忙"),
    FEIGN_CALL_ERROR(517, "服务间调用出错"),

    GATEWAY_NOT_FOUND_SERVICE(010404, "服务未找到"),
    GATEWAY_ERROR(010500, "网关异常"),
    GATEWAY_CONNECT_TIME_OUT(010002, "网关超时"),

    ARGUMENT_NOT_VALID(020000, "请求参数校验不通过"),
    INVALID_TOKEN(020001, "无效token"),
    UPLOAD_FILE_SIZE_LIMIT(020010, "上传文件大小超过限制"),

    DUPLICATE_PRIMARY_KEY(030000,"唯一键冲突"),

    ;

    private final int code;

    private final String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
