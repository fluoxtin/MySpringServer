package com.example.springprojectdemo.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentRecord {
    private String atten_id;
    private String stu_id;
    private String stu_name;
    private int attendance;
    private long sign_in_time;
}
