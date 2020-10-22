package com.qf.controller;

import com.qf.pojo.Admin;
import com.qf.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("tologin")
    public String tologin() {
        return "behind/login.jsp";
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(Admin data, HttpSession httpSession, HttpServletResponse res) {
        List<Admin> admins = adminService.findByAdmin(data);

        if (admins.size() == 0) {
            return "false";
        } else {

            httpSession.setAttribute("admin", admins.get(0));
            httpSession.setAttribute("userAccount2", admins.get(0).getUsername());
            httpSession.setMaxInactiveInterval(60*60);

            Cookie jsessionid = new Cookie("JSESSIONID", httpSession.getId());
            jsessionid.setMaxAge(60*60);
            jsessionid.setPath("/");
            res.addCookie(jsessionid);

            return "success";
        }
    }

    @RequestMapping("exit")
    public String exit(HttpSession httpSession, HttpServletResponse res) {
        httpSession.invalidate();

        Cookie jsessionid = new Cookie("JSESSIONID", "");
        jsessionid.setMaxAge(0);
        res.addCookie(jsessionid);

        return "behind/login.jsp";
    }
}
