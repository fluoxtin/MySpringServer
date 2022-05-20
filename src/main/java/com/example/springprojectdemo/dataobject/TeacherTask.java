package com.example.springprojectdemo.dataobject;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherTask {
    String attend_id;
    String tea_id;
    long deadline;
    String course_name;
}
