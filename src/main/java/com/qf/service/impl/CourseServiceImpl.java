package com.qf.service.impl;

import com.qf.dao.CourseMapper;
import com.qf.pojo.Course;
import com.qf.pojo.CourseExample;
import com.qf.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findAllCourses() {
        return courseMapper.selectByExampleWithBLOBs(null);
    }

    @Override
    public List<Course> findCousesBySubjectId(Integer subjectId) {

        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria criteria = courseExample.createCriteria();
        criteria.andSubjectIdEqualTo(subjectId);

        return courseMapper.selectByExampleWithBLOBs(courseExample);
    }

    @Override
    public Course findCourseById(Integer id) {
        return courseMapper.findCourseById(id);
    }
}
