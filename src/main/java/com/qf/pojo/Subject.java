package com.qf.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Subject {
    private Integer id;

    private String subjectName;
    private List<Course> courseList;


}