package com.example.springprojectdemo.dataobject;

import lombok.Data;

@Data
public class StudentLeave {

    private String stu_id;

    private long start;

    private long end;

    private String msg;

}
