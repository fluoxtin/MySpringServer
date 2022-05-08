package com.example.springprojectdemo.userlogin.dataobject;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Teacher implements Serializable {
    private String tea_id;

    private String name;

    private String sex;

    private String phone;

    private String email;

    private String unit;
}
