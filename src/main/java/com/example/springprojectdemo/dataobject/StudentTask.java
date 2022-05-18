package com.example.springprojectdemo.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class StudentTask {
    private String attend_id;

    private String stu_id;

    private long deadline;

    private double latitude;

    private double longitude;

    public StudentTask(String attend_id, String id, long dead_time, double latitude, double longitude) {
        this.attend_id = attend_id;
        this.stu_id = id;
        this.deadline = dead_time;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
