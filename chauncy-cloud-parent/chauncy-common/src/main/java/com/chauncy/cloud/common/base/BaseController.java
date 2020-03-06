package com.chauncy.cloud.common.base;

/**
 * @Author cheng
 * @create 2020-03-05 22:21
 */
public abstract class BaseController {

    protected Result success(){
        return Result.success();
    }

    protected Result success(Object data){
        return Result.success(data);
    }
    protected  Result error(BaseCode code, String path){
        return Result.error(code, path);
    }

    protected  Result error(BaseCode code, String message, String path, Object... args){
        return Result.error(code, String.format(message,args),path);
    }

    protected  Result error(int code, String message,String path,Object... args){
        return Result.error(code, String.format(message,args),path);
    }


}
