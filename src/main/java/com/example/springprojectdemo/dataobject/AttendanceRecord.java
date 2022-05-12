package com.example.springprojectdemo.dataobject;

import lombok.Data;

@Data
public class AttendanceRecord {
    private String attend_id;

    private String cour_name;

    private String attendance;

    private String sign_in_time;

}
