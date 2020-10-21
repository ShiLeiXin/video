package com.qf.service;

import com.qf.pojo.Subject;

import java.util.List;

public interface SubjectService {
    public List<Subject> findAllSubjects();

    public Subject findSubjectById(Integer subjectId);

    /*public Subject findByCourseId(Integer courseId);*/
}
