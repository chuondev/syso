package com.chuonye.syso.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.chuonye.syso.SiteProperties;
import com.chuonye.syso.domain.dto.PostForm;
import com.chuonye.syso.domain.entity.User;
import com.chuonye.syso.service.OptionService;
import com.chuonye.syso.service.PostService;
import com.chuonye.syso.service.UserService;
import com.chuonye.syso.utils.ServletTools;
import com.chuonye.syso.utils.Tools;

/**
 * 系统初始安装
 *  
 * @author chuonye@foxmail.com
 */
@Controller
public class InitController {
    
    private PostService   postService;
    private UserService   userService;
    private OptionService optService;
    
    @Autowired
    public InitController(PostService postService, UserService userService, OptionService optService) {
        this.postService = postService;
        this.userService = userService;
        this.optService = optService;
    }
    
    @GetMapping("/init")
    public String initSite(HttpServletRequest request, Model model) {
        if ("1".equals(optService.getOption(SiteProperties.INITIALIZED.key()))) {
            return "redirect:/admin/login";
        }
        // 初始化一篇文章
        PostForm postForm = new PostForm();
        postForm.setTitle("欢迎使用 Syso");
        postForm.setContent("如果您看到这篇文章,表示您的 blog 已经安装成功.");
        postForm.setSlug("start");
        postForm.setAction("published");
        postService.addPost(postForm, null);
        
        // 初始化默认用户
        String salt = Tools.randomString(5);
        String passwd = Tools.randomString(8);
        String hexPassword = Tools.md5Hex(passwd + salt);
        
        User user = new User();
        user.setName("syso");
        user.setNickname("Syso");
        user.setPassword(hexPassword);
        user.setSalt(salt);
        user.setLastLogin(user.getCreateTime());
        
        userService.addUser(user);
        
        // 初始化配置
        Map<String, String> options = new HashMap<String, String>();
        options.put(SiteProperties.INITIALIZED.key(), "1");
        options.put(SiteProperties.SITE_TITLE.key(), "小创编程");
        options.put(SiteProperties.SITE_URL.key(), ServletTools.getSiteUrl(request));
        options.put(SiteProperties.SITE_LOGO.key(), "/static/app/app-logo.svg");
        options.put(SiteProperties.SITE_FAVICON.key(), "/static/app/app-favicon.ico");
        options.put(SiteProperties.SITE_DESC.key(), "小创编程是一个源码分析，技术研究的个人网站");
        optService.addOptions(options);
        
        model.addAttribute("user", "syso");
        model.addAttribute("passwd", passwd);
        return "admin/welcome";
    }
}
