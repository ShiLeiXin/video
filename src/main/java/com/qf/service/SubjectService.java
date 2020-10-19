package com.qf.service;

import com.qf.pojo.Course;
import com.qf.pojo.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> showSubject();


    Subject selectAll(Integer id);

}
