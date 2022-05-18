package com.example.springprojectdemo.dataobject;

import lombok.Data;

import java.util.Date;

@Data
public class CourseAttendRecord {

    private String tea_id;

    private String attend_id;

    private String cour_name;

    private long time;

    private int total_student;

    private int actual_attendance;

    public CourseAttendRecord(
            String tea_id,
            String attend_id,
            String cour_name,
            long time,
            int total_student,
            int actual_attendance
    ) {
        this.tea_id = tea_id;
        this.attend_id = attend_id;
        this.cour_name = cour_name;
        this.time = time;
        this.total_student = total_student;
        this.actual_attendance = actual_attendance;
    }
}
