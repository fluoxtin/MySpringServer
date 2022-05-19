package com.example.springprojectdemo.dataobject;

import lombok.Data;


@Data
public class Course {

    private String cour_id;

    private String cour_name;

    private String tea_name;

    private String start;

    private String end;

    private int week_day;

}
