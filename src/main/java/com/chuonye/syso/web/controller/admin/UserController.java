package com.chuonye.syso.web.controller.admin;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.chuonye.syso.SysoConstants;
import com.chuonye.syso.domain.dto.UserForm;
import com.chuonye.syso.domain.entity.User;
import com.chuonye.syso.service.UserService;
import com.chuonye.syso.utils.Tools;
import com.google.code.kaptcha.Producer;

/**
 * 用户登录、注销；用户信息和密码更新
 *  
 * @author chuonye@foxmail.com
 */
@Controller
public class UserController extends AbstractController {
    
    private Producer captchaProducer;
    private UserService userService;
    
    @Autowired
    public UserController(Producer captchaProducer, UserService userService) {
        this.captchaProducer = captchaProducer;
        this.userService = userService;
    }

    @GetMapping(value = "/admin/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute(SysoConstants.USER_SESSION_KEY);
        model.addAttribute("user", user);
        return "admin/profile";
    }
    
    @PostMapping("/admin/profile/modify")
    public String updateProfile(@Valid UserForm userForm, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            noticeFail(bindingResult.getAllErrors().toString());
            model.addAttribute("user", userForm);
        } else {
            User user = userService.getUser(userForm.getUserId());
            
            userService.updateUserByUserForm(user, userForm);
            
            userService.addUser(user);
            
            noticeOk("更新成功");
            session.setAttribute(SysoConstants.USER_SESSION_KEY, user);
            model.addAttribute("user", user);
        }
        
        return "redirect:/admin/profile";
    }
    
    @PostMapping("/admin/profile/passwd/{userId:[0-9]+}")
    public String updatePassword(@PathVariable Integer userId, String passwd, String confirm) {
        if (confirm.equals(passwd)) {
            User user = userService.getUser(userId);
            
            String hexPassword = Tools.md5Hex(passwd + user.getSalt());
            user.setPassword(hexPassword);
            
            userService.addUser(user);
            
            noticeOk("密码更新成功");
        } else {
            noticeFail("两次密码不一致");
        }
        return "redirect:/admin/profile";
    }
    
    
    /////////////////////
    /////////////////////
    
    /**
     * 返回登录页面
     *
     * @param session http session
     * @return 模板路径 admin/login
     */
    @GetMapping(value = "/admin/login")
    public String login(HttpSession session, Model model) {
        User user = (User) session.getAttribute(SysoConstants.USER_SESSION_KEY);
        // 已登录
        if (user != null) {
            return "redirect:/admin";
        }
        
        model.addAttribute("u", session.getAttribute("u"));
        model.addAttribute("p", session.getAttribute("p"));
        session.removeAttribute("u");
        session.removeAttribute("p");
        return "admin/login";
    }

    @PostMapping("/admin/doLogin")
    public String doLogin(String loginName, String password, String captcha, HttpSession session) {
        String error = null;
        
        String captchaId = (String) session.getAttribute(SysoConstants.KAPTCHA_SESSION_KEY);
        
        if (captcha.equalsIgnoreCase(captchaId)) {
            User user = userService.getOneUser();
            
            String hexPassword = Tools.md5Hex(password + user.getSalt());
            
            if ((loginName.equals(user.getEmail()) || loginName.equals(user.getName()))
                    && user.getPassword().equals(hexPassword)) {
                
                session.removeAttribute(SysoConstants.KAPTCHA_SESSION_KEY);
                session.setAttribute(SysoConstants.USER_SESSION_KEY, user);
                return "redirect:/admin";
            } else {
                error = "用户名或密码错误";
            }
        } else {
            error = "验证码错误";
        }
        
        if (Objects.nonNull(error)) {
            noticeFail(error);
            session.setAttribute("u", loginName);
            session.setAttribute("p", password);
        }
        
        return "redirect:/admin/login";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
    
    @GetMapping("/captcha")
    public void captchaImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set to expire far in the past.
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
          
        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = captchaProducer.createText();

        // store the text in the session
        request.getSession().setAttribute(SysoConstants.KAPTCHA_SESSION_KEY, capText);

        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);

        ServletOutputStream out = response.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
    
}
