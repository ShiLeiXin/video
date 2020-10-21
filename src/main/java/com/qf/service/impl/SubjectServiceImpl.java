package com.qf.service.impl;

import com.qf.dao.SubjectMapper;
import com.qf.pojo.Subject;
import com.qf.pojo.SubjectExample;
import com.qf.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;


    @Override
    public List<Subject> findAllSubjects() {
        return subjectMapper.selectByExample(null);
    }

    @Override
    public Subject findSubjectById(Integer subjectId) {

        return subjectMapper.selectSubjectById(subjectId);
    }

   /* @Override
    public Subject findByCourseId(Integer courseId) {
        SubjectExample subjectExample = new SubjectExample();
        SubjectExample.Criteria criteria = subjectExample.createCriteria();
        criteria.and
        return subjectMapper.sele
    }*/
}
