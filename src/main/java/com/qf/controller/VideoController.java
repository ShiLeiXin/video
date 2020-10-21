package com.qf.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.pojo.*;
import com.qf.service.CourseService;
import com.qf.service.SpeakerService;
import com.qf.service.SubjectService;
import com.qf.service.VideoService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("video")
@Controller
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private SpeakerService speakerService;

    @Autowired
    private CourseService courseService;

    @Autowired
    SubjectService subjectService;

    @RequestMapping("addVideo")
    public String addVideo(Model model) {

        List<Speaker> speakerList = speakerService.findAllSpeakers();
        List<Course> courseList = courseService.findAllCourses();

        model.addAttribute("speakerList", speakerList);
        model.addAttribute("courseList", courseList);

        return "behind/addVideo.jsp";
    }

    @RequestMapping("list")
    public String findVideosByPage(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                   QueryVo queryVo, Model model, HttpSession httpSession) {

        if (pageNum < 1) {
            pageNum = 1;
        } else if (pageNum > getTotalPages()) {
            pageNum = getTotalPages();
        }

        List<Speaker> speakerList = speakerService.findAllSpeakers();
        List<Course> courseList = courseService.findAllCourses();

        PageHelper.startPage(pageNum, pageSize);
        List<Video> videos = videoService.findAllVideoByQueryVo(queryVo);
        PageInfo<Video> pageInfo = new PageInfo<>(videos);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("speakerList", speakerList);
        model.addAttribute("courseList", courseList);

        return "behind/videoList.jsp";
    }

    @RequestMapping("edit")
    public String edit(Model model, Integer id) {

        Video video = videoService.findVideoById(id);

        List<Video> allVideoByQueryVo = videoService.findAllVideoByQueryVo(null);
        List<Speaker> speakerList = speakerService.findAllSpeakers();
        List<Course> courseList = courseService.findAllCourses();

        model.addAttribute("speakerList", speakerList);
        model.addAttribute("courseList", courseList);
        model.addAttribute("video", video);

        return "behind/addVideo.jsp";
    }

    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(Model model, Video video) {

        Video video1 = videoService.findVideoById(video.getId());

        if (video1 == null) {
            videoService.addVideo(video);
        } else {
            videoService.updateVideo(video);
        }

        return "redirect:/video/list";

    }

    @RequestMapping("videoDel")
    @ResponseBody
    public String videoDel(Integer id) {

        Integer result = videoService.deleteVideo(id);

        if (result > 0) {
            return "success";
        } else {
            return "false";
        }
    }

    @RequestMapping("delBatchVideos")
    public String delBatchVideos(int[] ids, String pageNum) {
        for (int i = 0; i < ids.length; i++) {
            videoService.deleteVideo(ids[i]);
        }

        return "redirect:/video/list?pageNum=" + pageNum;
    }

    @RequestMapping("showVideo")
    public String showVideo(Integer videoId, Model model) {
        Video video = videoService.findVideoById(videoId);
        Course course = courseService.findCourseById(video.getCourseId());

        Integer subjectId = course.getSubjectId();
        Subject subject = subjectService.findSubjectById(subjectId);

        List<Subject> subjectList = subjectService.findAllSubjects();

        model.addAttribute("video", video);
        model.addAttribute("course", course);
        model.addAttribute("subject", subject);
        model.addAttribute("subjectList", subjectList);

        return "before/section.jsp";
    }

    public Integer getTotalPages() {

        Integer totalPages = videoService.getTotalCounts();

        Integer pageSize = 10;

        if (totalPages % 10 == 0) {
            return totalPages / 10;
        } else {
            return totalPages / 10 + 1;
        }
    }

}
