package com.chuonye.syso.web.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chuonye.syso.service.OptionService;

/**
 * 站点信息设置
 *  
 * @author chuonye@foxmail.com
 */
@Controller
public class OptionController extends AbstractController {
    
    @Autowired
    private OptionService optService;

    @GetMapping(value = "/admin/options")
    public String profile(Model model, HttpSession session) {
        return "admin/admin-options";
    }
    
    @PostMapping("/admin/options/modify")
    public String updateOptions(@RequestParam(required = false) Map<String, String> options, HttpSession session) {
        optService.addOptions(options);
        
        noticeOk("保存成功");
        return "redirect:/admin/options";
    }
}
