package com.chauncy.cloud.common.base;

import com.chauncy.cloud.common.enums.system.exception.Code;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * @Author cheng
 * @create 2020-03-05 16:04
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)//为空不序列化
@ApiModel(value = "response返回对象")
public class Result<T> implements Serializable {

    @ApiModelProperty(value = "返回码")
    private int code;

    @ApiModelProperty(value = "返回信息")
    private String msg;


    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "出错的请求路径")
    private String path;

    @ApiModelProperty(value = "时间戳")
    private LocalDateTime timestamp = LocalDateTime.now();

    public boolean isSuccess() {
        return code == Code.SUCCESS.getCode();
    }

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Result(int code, String msg, T data, String path) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.path = path;
    }

    @SuppressWarnings("unchecked")
    public static <T> Result<T> success(T data) {
        if (data == null) {
            return (Result<T>) Result.success();
        }
        return new Result<>(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(), data);
    }

    public static Result<Object> success() {
        return success(new Object());
    }

    public static <T> Result<T> error(String message) {
        return error(Code.ERROR, message, null);
    }

    public static <T> Result<T> error(BaseCode code) {
        return error(code, null);
    }

    public static <T> Result<T> error(BaseCode code, String path) {
        return new Result<>(code.getCode(), code.getMsg(), null, path);
    }

    public static <T> Result<T> error(BaseCode code, String message, String path) {
        return new Result<>(code.getCode(), message, null, path);
    }

    public static <T> Result<T> error(int code, String message, String path) {
        return new Result<>(code, message, null, path);
    }

}
