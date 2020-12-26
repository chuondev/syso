package com.chuonye.syso.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chuonye.syso.manager.ServerMetricManager;
import com.chuonye.syso.service.PostService;

/**
 * 后台首页；开发者工具；系统性能参数
 *  
 * @author chuonye@foxmail.com
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends AbstractController {

    private PostService         postService;
    private ServerMetricManager serverMetric;
    
    @Autowired
    public AdminController(PostService postService, ServerMetricManager serverMetric) {
        this.postService = postService;
        this.serverMetric = serverMetric;
    }

    @GetMapping(value = { "", "/index" })
    public String adminPage(Model model) {
        model.addAttribute("totalPosts", postService.getTotal());
        
        model.addAttribute("totalViews", postService.getTotalViews());
        
        model.addAttribute("latestPosts", postService.getLatestPosts());
        
        model.addAttribute("osmetrics", serverMetric.loadOSMetrics());
        return "admin/index";
    }

    @GetMapping(value = "/server-metrics")
    public String getServerMetrics(Model model) {
        model.addAttribute("osmetrics", serverMetric.loadOSMetrics());
        return "admin/_osmetrics";
    }
    
    @GetMapping(value = "/devtools")
    public String devtoolsPage(Model model) {
        model.addAttribute("osmetrics", serverMetric.loadOSMetrics());
        model.addAttribute("jvmetrics", serverMetric.loadJVMetrics());
        model.addAttribute("env", serverMetric.loadEnvInfo());
        return "admin/devtools";
    }
}
