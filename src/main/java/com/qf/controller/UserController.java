package com.qf.controller;

import com.qf.pojo.User;
import com.qf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("user/loginUser")
    @ResponseBody
    //用户登录
    public String loginUser(String email, String password, HttpSession session,Model model) {
        session.setAttribute("userAccount", email);
        model.addAttribute("email",email);
        int i = userService.loginUser(email,password);
        if (i > 0) {
            return "success";
        }else {
            return "登录失败";
        }
    }
    @RequestMapping("user/insertUser")
    @ResponseBody
    //用户注册
    public String insertUser(User user,String psw_again) {
        int i = userService.insertUser(user,psw_again);
        if (i > 0) {
            return "success";
        }else {
            return null;
        }
    }

    @RequestMapping("user/changeProfile")
    //更改资料
    public String changeProfile(HttpSession session,Model model) {
        User user = getUser(session);
        model.addAttribute("user", user);
        return "/before/change_profile.jsp";

    }
    @RequestMapping("user/updateUser")
    //更改后保存
    public String updateUser(User user) {
        int i = userService.updateUser(user);
        if (i > 0) {
            return "redirect:/user/changeProfile";
        }
        return "forward:/user/updateUser";

    }

    @RequestMapping("user/showMyProfile")
    //个人中心
    public String showMyProfile(HttpSession session, Model model) {
        String userAccount = (String) session.getAttribute("userAccount");
        User user = userService.findByEmail(userAccount);
        model.addAttribute("user", user);
        return "/before/my_profile.jsp";
    }

    @RequestMapping("user/loginOut2")
    //退出
    public String loginOut2(HttpSession session) {
        session.removeAttribute("userAccount");
        return "/before/index.jsp";
    }
    @RequestMapping("user/loginout")
    public String loginout(HttpSession session) {
        session.invalidate();
        return "/before/index.jsp";
    }

    //点击密码安全
    @RequestMapping("user/passwordSafe")
    public String passwordSafe() {
        return "/before/password_safe.jsp";
    }
    //点击密码安全后的保存
    @RequestMapping("user/updatePassword")
    public String updatePassword(HttpSession session, String newPassword) {
        User user = getUser(session);
        user.setPassword(newPassword);
        userService.updateUser(user);
        return "before/password_safe.jsp";
    }

    //获取当向登录用户的类对象
    public User getUser(HttpSession session) {
        String email = (String) session.getAttribute("userAccount");
        User user = userService.findByEmail(email);
        return user;
    }
}
