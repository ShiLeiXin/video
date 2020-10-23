package com.qf.controller;

import com.mysql.cj.Session;
import com.qf.pojo.Subject;
import com.qf.pojo.User;
import com.qf.service.SubjectService;
import com.qf.service.UserService;
import com.qf.utils.ImageCut;
import com.qf.utils.MailUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.plugin2.message.Message;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static com.qf.utils.MailUtils.getValidateCode;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @RequestMapping("show")
    public String showIndex(Model model, HttpSession httpSession) {

        String userAccount = (String) httpSession.getAttribute("userAccount");
        List<Subject> subjectList = subjectService.findAllSubjects();

        model.addAttribute("subjectList", subjectList);
        model.addAttribute("userAccount", userAccount);

        return "before/index.jsp";
    }

    @RequestMapping("loginUser")
    @ResponseBody
    public String LoginUser(User user, HttpSession httpSession) {

        List<User> users = null;
        users = userService.findByEmailAndPsw(user);
        httpSession.setAttribute("userAccount", user.getEmail());
        httpSession.setMaxInactiveInterval(60 * 60 * 24 * 7);

        if (users.size() == 0) {
            return "error";
        } else {
            return "success";
        }
    }

    @RequestMapping("loginOut")
    @ResponseBody
    public String loginOut(HttpSession httpSession) {
        httpSession.invalidate();
        return "";
    }

    @RequestMapping("loginOut2")
    public String loginOut2(HttpSession httpSession) {
        httpSession.invalidate();

        return "before/index.jsp";
    }

    @RequestMapping("changeProfile")
    public String changeProfile(HttpSession httpSession, Model model) {
        User user = findUserByHttpSession(httpSession);

        model.addAttribute("user", user);

        return "before/change_profile.jsp";
    }

    @RequestMapping("updateProfile")
    public String updateProfile(User user) {
        userService.updateUser(user);

        return "redirect:/user/showMyProfile";
    }

    @RequestMapping("showMyProfile")
    public String showMyProfile(HttpSession httpSession, Model model) {

        User user = findUserByHttpSession(httpSession);

        model.addAttribute(user);

        return "before/my_profile.jsp";
    }

    @RequestMapping("changeAvatar")
    public String changeAvatar(HttpSession httpSession, Model model) {

        User user = findUserByHttpSession(httpSession);

        model.addAttribute("user", user);

        return "before/change_avatar.jsp";

    }

    @RequestMapping("upLoadImage")
    public String upLoadImage(MultipartFile image_file, HttpSession httpSession, String x1,
                              String y1, String w,
                              String h) {

        Integer x11 = (int) Double.parseDouble(x1);
        Integer y11 = (int) Double.parseDouble(y1);
        Integer w1 = (int) Double.parseDouble(w);
        Integer h1 = (int) Double.parseDouble(h);


        String path = "D:\\Program Files\\server\\apache-tomcat-8.5.41\\webapps\\video";
        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }
        String filename = image_file.getOriginalFilename();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + filename;

        File file1 = new File(path, filename);


        try {
            image_file.transferTo(file1);

            String path2 = path + "\\" + filename;
            System.out.println(path2);
            ImageCut imageCut = new ImageCut();
            imageCut.cutImage(path2, x11, y11, w1, h1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        String userAccount = (String) httpSession.getAttribute("userAccount");
        List<User> users = userService.findByUserAccount(userAccount);

        User user = users.get(0);
        user.setImgurl(filename);

        userService.updateUser(user);

        return "redirect:/user/showMyProfile";

    }

    @RequestMapping("passwordSafe")
    public String passwordSafe(HttpSession httpSession, Model model) {
        User user = findUserByHttpSession(httpSession);

        model.addAttribute("user", user);

        return "before/password_safe.jsp";
    }

    @RequestMapping("updatePassword")
    public String updatePassword(HttpSession httpSession, String newPassword) {
        User user = findUserByHttpSession(httpSession);

        user.setPassword(newPassword);

        userService.updateUser(user);

        return "redirect:/user/showMyProfile";
    }

    @RequestMapping("validatePassword")
    @ResponseBody
    public String validatePassword(String password, HttpSession httpSession) {
        User user = findUserByHttpSession(httpSession);

        if (password.equals(user.getPassword())) {
            return "success";
        } else {
            return "false";
        }
    }

    @RequestMapping("insertUser")
    @ResponseBody
    public String insertUser(User user) {
        Integer result = userService.insertUser(user);

        if (result > 0) {
            return "success";
        } else {
            return "false";
        }
    }

    @RequestMapping("validateEmail")
    @ResponseBody
    public String validateEmail(String email) {
        User user = new User();
        user.setEmail(email);
        List<User> users = userService.findByEmail(user);
        if (users.size() == 0) {
            return "success";
        } else {
            return "false";
        }

    }

    @RequestMapping("forgetPassword")
    public String forgetPassword() {
        return "before/forget_password.jsp";
    }

    @RequestMapping("sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpSession httpSession) {
        User user = new User();
        user.setEmail(email);
        List<User> users = userService.findByEmail(user);
        if (users.size() == 0) {
            return "hasNoUser";
        } else if (users.size() > 0) {

            String validateCode = MailUtils.getValidateCode(6);
            httpSession.setAttribute("validateCode", validateCode);
            System.out.println(email);

            MailUtils.sendMail(email, "你好，这是一封测试邮件，无需回复。",
                    "测试邮件随机生成的验证码是：" + validateCode);

            return "success";
        } else {
            return "false";
        }
    }

    @RequestMapping("validateEmailCode")
    public String validateEmailCode(String code, String email, HttpSession httpSession, Model model) {
        String validateCode = (String) httpSession.getAttribute("validateCode");

        if (code.equals(validateCode)) {
            model.addAttribute("email", email);
            return "before/reset_password.jsp";
        } else {
            return "redirect:/user/forgetPassword";
        }

    }

    @RequestMapping("resetPassword")
    public String resetPassword(String email, String password) {
        List<User> users = userService.findByUserAccount(email);

        User user = users.get(0);

        user.setPassword(password);
        //根据id修改用户
        userService.updateUser(user);

        return "redirect:/user/show";

    }


    /**
     * 通过httpSession获取到对应用户的User类对象
     *
     * @param httpSession
     * @return
     */
    public User findUserByHttpSession(HttpSession httpSession) {
        String userAccount = (String) httpSession.getAttribute("userAccount");
        List<User> users = userService.findByUserAccount(userAccount);

        return users.get(0);
    }


}
