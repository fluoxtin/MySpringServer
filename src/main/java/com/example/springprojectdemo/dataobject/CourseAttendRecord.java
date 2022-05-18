package com.example.springprojectdemo.dataobject;

import lombok.Data;

import java.util.Date;

@Data
public class CourseAttendRecord {

    private String attend_id;

    private String cour_name;

    private long time;

    private int total_student;

    private int actual_attendance;
}
