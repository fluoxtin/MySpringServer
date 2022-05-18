package com.example.springprojectdemo.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class AttendTask implements Serializable {

    private String attend_id;

    private String cour_id;

    private long deadline;

    private Location location;

    public AttendTask(String attend_id, String cour_id, long deadline, Location location) {
        this.attend_id = attend_id;
        this.cour_id = cour_id;
        this.deadline = deadline;
        this.location = location;
    }
}
