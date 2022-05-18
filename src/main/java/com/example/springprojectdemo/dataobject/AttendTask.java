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



}
