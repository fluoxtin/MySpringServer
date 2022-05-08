package com.example.springprojectdemo.userlogin.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 *  @author fluoxtin created on 2022/4/8
 *
 * 用户类
 */
@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)
public class User implements Serializable {


    @NotEmpty(message = "用户名不能为空！")
    private String username;

    @NotEmpty(message = "密码不能为空！")
    @Size(min = 8, message = "密码长度不能小于8！")
    private String password;

    /**
     *  role : 0 teacher
     *         1 student
     */
    private Integer role;
}
