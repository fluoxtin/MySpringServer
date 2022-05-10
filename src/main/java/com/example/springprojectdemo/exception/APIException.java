package com.example.springprojectdemo.exception;

import com.example.springprojectdemo.model.ResultCode;

public class APIException extends RuntimeException{
    private int status;
    private String msg;

    public APIException() {
        this(ResultCode.FAILED);
    }

    public APIException(ResultCode errorCode) {
        this.status = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

}
