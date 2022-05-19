package com.example.springprojectdemo.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CourseAttendRecord {

    private String tea_id;

    private String attend_id;

    private String cour_name;

    private long time;

    private int total;

    private int actual;

//    public CourseAttendRecord() {}

    public CourseAttendRecord(
            String tea_id,
            String attend_id,
            String cour_name,
            long time,
            int total,
            int actual
    ) {
        this.tea_id = tea_id;
        this.attend_id = attend_id;
        this.cour_name = cour_name;
        this.time = time;
        this.total = total;
        this.actual = actual;
    }
}
