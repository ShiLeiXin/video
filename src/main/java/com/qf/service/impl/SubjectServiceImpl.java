package com.qf.service.impl;

import com.qf.dao.SubjectMapper;
import com.qf.pojo.Course;
import com.qf.pojo.Subject;
import com.qf.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;


    public List<Subject> showSubject() {
        List<Subject> list = subjectMapper.selectByExample(null);
        return list;
    }


    @Override
    public Subject selectSubjectById(Integer id) {
        Subject subject = subjectMapper.selectSubjectById(id);

        return subject;
    }
}



