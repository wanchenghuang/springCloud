package com.chauncy.cloud.common.exception;

import com.chauncy.cloud.common.base.BaseCode;
import com.chauncy.cloud.common.enums.system.exception.Code;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author cheng
 * @create 2020-03-05 16:23
 *
 * 业务层抛出异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    private BaseCode code = Code.ERROR;

    private String message;

    public BusinessException(BaseCode code) {
        super(code.getMsg());
        this.code = code;
        this.message = code.getMsg();
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(BaseCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(BaseCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
}
