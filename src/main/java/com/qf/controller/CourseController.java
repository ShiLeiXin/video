package com.qf.controller;

import com.qf.pojo.Course;
import com.qf.pojo.Subject;
import com.qf.service.CourseService;
import com.qf.service.SubjectService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private SubjectService subjectService;


    @RequestMapping(value = "/course/{id}")
    public String showCourse(@PathVariable(name = "id") Integer subjectId, Model model) {


        List<Subject> subjectList = subjectService.findAllSubjects();

        Subject subject = subjectService.findSubjectById(subjectId);

        model.addAttribute("subjectList", subjectList);
        model.addAttribute("subject", subject);

        return "before/course.jsp";

    }

}
