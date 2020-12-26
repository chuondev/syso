package com.chuonye.syso.web.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.chuonye.syso.service.OptionService;
import com.chuonye.syso.service.PostService;

import freemarker.template.Template;

/**
 * RSS Sitemap
 *  
 * @author chuonye@foxmail.com
 */
@Controller
public class FeedController {
    
    private PostService          postService;
    private FreeMarkerConfigurer freeMarker;

    @Autowired
    public FeedController(PostService postService, OptionService optSrevice, FreeMarkerConfigurer freeMarker) {
        this.postService = postService;
        this.freeMarker = freeMarker;
    }

    @ResponseBody
    @GetMapping(value = {"/feed", "/feed.xml", "/rss", "/rss.xml"}, produces = MediaType.APPLICATION_XML_VALUE)
    public String feed(Model model) throws Exception {
        model.addAttribute("posts", postService.getAllPublishedPosts());
        
        Template template = freeMarker.getConfiguration().getTemplate("app/rss.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }
    
    @ResponseBody
    @GetMapping(value = {"/sitemap", "/sitemap.xml"}, produces = MediaType.APPLICATION_XML_VALUE)
    public String sitemap(Model model) throws Exception {
        model.addAttribute("posts", postService.getAllPublishedPosts());
        
        Template template = freeMarker.getConfiguration().getTemplate("app/sitemap.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }
    
}
