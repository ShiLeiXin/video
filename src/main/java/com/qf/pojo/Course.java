package com.qf.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Course {
    private Integer id;

    private String courseTitle;

    private Integer subjectId;

    private String courseDesc;

    private List<Video> videoList;

}