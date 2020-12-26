package com.chuonye.syso.web.controller.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.chuonye.syso.domain.entity.Post;
import com.chuonye.syso.service.PostService;
import com.chuonye.syso.utils.ServletTools;

/**
 * 前台文章详情
 *  
 * @author chuonye@foxmail.com
 */
@Controller
public class PostController {

    @Autowired
    private PostService    postService;

    @GetMapping("/archives/{slug}.html")
    public String getPost(Model model, @PathVariable String slug, HttpServletRequest request) {
        Post post = postService.getPublishedPost(slug);

        String user = ServletTools.getClientIp(request) + "." + slug;
        postService.cacheViews(user, post.getSlug());

        Post prevPost = postService.getPrevPost(post.getCreateTime());
        Post nextPost = postService.getNextPost(post.getCreateTime());

        model.addAttribute("post", post);
        model.addAttribute("prevPost", prevPost);
        model.addAttribute("nextPost", nextPost);
        return "app/post-show";
    }
}
