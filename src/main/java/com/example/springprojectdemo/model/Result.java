package com.example.springprojectdemo.model;

import lombok.Data;


import java.io.Serializable;

/**
 * @author fluoxtin created on 2022/4/8
 *
 * 请求结果类
 * @param <T>  数据体类型
 *
 */

@Data
public class Result<T> implements Serializable {

    /**
     * message
     */
    private String msg;

    /**
     *  status : 状态码
     */
    private int code;

    /**
     *
     */
    private  T data;

    public Result() {}

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS, data);
    }

    public static<T> Result<T> failed() {
        return new Result<>(ResultCode.FAILED);
    }

}
