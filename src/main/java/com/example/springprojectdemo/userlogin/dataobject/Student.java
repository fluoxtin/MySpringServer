package com.example.springprojectdemo.userlogin.dataobject;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Student implements Serializable {

    private String stu_id;

    private String name;

    private String sex;

    private String phone;

    private String email;

    private String unit;

    private String stu_class;

    private String major;
}
