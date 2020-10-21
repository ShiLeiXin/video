package com.qf.controller;

import com.qf.pojo.Course;
import com.qf.pojo.Subject;
import com.qf.service.CourseService;
import com.qf.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
public class SubjectController {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private CourseService courseService;

    @RequestMapping("course/course")
    public String showSubject(Model model) {
        List<Subject> list = subjectService.showSubject();
        model.addAttribute("subjectList",list);
        return "/before/index.jsp";
    }

    @RequestMapping(value = "course/course/{id}")
    public String selectSubjectById(@PathVariable(name="id") Integer id , Model model) {
        Subject subject = subjectService.selectSubjectById(id);
        model.addAttribute("subject",subject);
        return "/before/course.jsp";
    }


}
