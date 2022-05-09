package com.example.springprojectdemo.userlogin.dataobject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class Student implements Serializable {

    private String stu_id;

    private String name;

    private String sex;

    private String phone;

    private String email;

    private String unit;

    private String stu_class;

    private String major;

    @Override
    public String toString() {
        return "Student{" +
                "stu_id='" + stu_id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", unit='" + unit + '\'' +
                ", stu_class='" + stu_class + '\'' +
                ", major='" + major + '\'' +
                '}';
    }
}
