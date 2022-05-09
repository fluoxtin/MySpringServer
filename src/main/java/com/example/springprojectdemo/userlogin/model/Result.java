package com.example.springprojectdemo.userlogin.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author fluoxtin created on 2022/4/8
 *
 * 请求结果类
 * @param <T>  数据体类型
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class Result<T> implements Serializable {

    /**
     * message
     */
    private String message;

    private boolean success;

    private String token;

    private  T date;

    public void setResultSuccess(String msg, T date) {
        this.message = msg;
        this.success = true;
        this.date = date;
    }

    public void setResultFailed(String msg) {
        this.message = msg;
        this.success = false;
        this.date = null;
    }

    @Override
    public String toString() {
        return "Result{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", token='" + token + '\'' +
                ", date=" + date +
                '}';
    }
}
