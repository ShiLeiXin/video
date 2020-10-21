package com.qf.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.pojo.Admin;
import com.qf.pojo.QueryVo;
import com.qf.pojo.Speaker;
import com.qf.service.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("speaker")
public class SpeakerController {

    @Autowired
    private SpeakerService speakerService;

    @RequestMapping("showSpeakerList")
    public String findAllSpeakers(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                  QueryVo queryVo, Model model, HttpSession httpSession) {

        Admin admin = (Admin) httpSession.getAttribute("admin");
        System.out.println(admin);

        if (pageNum < 1) {
            pageNum = 1;
        } else if (pageNum > getTotalPages()) {
            pageNum = getTotalPages();
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Speaker> speakers = speakerService.findAllSpeakers();
        PageInfo<Speaker> pageInfo = new PageInfo<>(speakers);

        model.addAttribute("pageInfo", pageInfo);
        try {
            model.addAttribute("admin", admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "behind/speakerList.jsp";
    }

    @RequestMapping("addSpeaker")
    public String addSpeaker() {
        return "behind/addSpeaker.jsp";
    }

    @RequestMapping("edit")
    public String edit(Model model, Integer id) {

        Speaker speaker = speakerService.findSpeakerById(id);
        model.addAttribute("speaker", speaker);
        return "behind/addSpeaker.jsp";
    }

    @RequestMapping("speakerDel")
    @ResponseBody
    public String speakerDel(Integer id) {
        Integer result = speakerService.deleteSpeakerById(id);

        if (result > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(Speaker speaker) {
        Speaker speaker1 = speakerService.findSpeakerById(speaker.getId());

        if (speaker1 == null) {
            speakerService.addSpeaker(speaker);
        } else {
            speakerService.updateSpeaker(speaker);
        }

        return "redirect:/speaker/showSpeakerList";
    }


    public Integer getTotalPages() {

        Integer totalPages = speakerService.getTotalCounts();

        Integer pageSize = 10;

        if (totalPages % 10 == 0) {
            return totalPages / 10;
        } else {
            return totalPages / 10 + 1;
        }
    }
}
