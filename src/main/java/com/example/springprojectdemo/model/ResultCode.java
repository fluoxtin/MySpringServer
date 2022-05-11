package com.example.springprojectdemo.model;

import lombok.Getter;

@Getter
public enum ResultCode {
    FAILED(1000, "Failed"),
    ERROR(1001, "Unknown Error"),
    INVALIDATE_TOKEN(1002, "Invalidate token"),
    LOGIN_FAILED(1003, "Wrong username or password"),
    FAILED_USER_NOT_EXIST(1004, "User dose not exist"),

    SUCCESS(200, "Successfully"),
    NOT_FOUND(404, "The requested resource does not exist"),
    SERVER_ERROR(500,"Internal server error"),
    VALIDATE_FAILED(1001, "Parameter validation failed");


    private int code;
    private String msg;

    ResultCode(int status, String msg) {
        this.code = status;
        this.msg = msg;
    }

}
