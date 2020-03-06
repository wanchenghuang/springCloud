package com.chauncy.cloud.common.exception;

import com.chauncy.cloud.common.base.Result;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author cheng
 * @create 2020-03-05 16:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FeignException extends RuntimeException {

    private Result result;

    public FeignException(Result result) {
        super(result.getMsg());
        this.result = result;
    }


}