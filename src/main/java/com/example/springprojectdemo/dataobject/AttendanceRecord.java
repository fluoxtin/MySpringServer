package com.example.springprojectdemo.dataobject;

import lombok.Data;

@Data
public class AttendanceRecord {
    private String attend_id;

    private String cour_name;

    private int isAttendance;

    private long sign_in_time;

}
