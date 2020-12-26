package com.chuonye.syso.web.controller.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.chuonye.syso.domain.entity.Post;
import com.chuonye.syso.service.PostService;

/**
 * 前台首页
 *  
 * @author chuonye@foxmail.com
 */
@Controller
public class IndexController {
    
    @Autowired
    private PostService postService;
    
    @GetMapping(value = {"/", "index"})
    public String index(Model model) {
        // 分页获取 TODO
//        Pageable pageRequest = PageRequest.of(0, SysoConstants.PAGE_SIZE, Sort.Direction.DESC, "id");
//        Page<Post> posts = postService.getPublishedPosts(pageRequest);
        
        List<Post> posts = postService.getAllPublishedPosts();
        
        model.addAttribute("posts", posts);
        return "app/index";
    }
    
}
