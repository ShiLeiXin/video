package com.qf.service;

import com.qf.pojo.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> showSubject();


    Subject selectSubjectById(Integer id);


}
