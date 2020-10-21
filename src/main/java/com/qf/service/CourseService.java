package com.qf.service;

import com.qf.pojo.Course;

import java.util.List;

public interface CourseService {
    public List<Course> findAllCourses();

    public List<Course> findCousesBySubjectId(Integer subjectId);

    public Course findCourseById(Integer id);
}
