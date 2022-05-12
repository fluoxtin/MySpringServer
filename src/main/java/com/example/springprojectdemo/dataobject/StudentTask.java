package com.example.springprojectdemo.dataobject;

import lombok.Data;

import java.util.Date;

@Data
public class StudentTask {
    private String attend_id;

    private String stu_id;

    private Date dead_time;

    public StudentTask(String attend_id, String id, Date dead_time) {
        this.attend_id = attend_id;
        this.stu_id = id;
        this.dead_time = dead_time;
    }
}
