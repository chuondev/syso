package com.chuonye.syso.web.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.chuonye.syso.SysoConstants;
import com.chuonye.syso.domain.dto.PostForm;
import com.chuonye.syso.domain.entity.Post;
import com.chuonye.syso.service.PostService;

/**
 * 后台文章增删改
 *  
 * @author chuonye@foxmail.com
 */
@Controller
@RequestMapping("/admin/posts")
public class AdminPostController extends AbstractController {

    @Autowired
    private PostService postService;

    @GetMapping("")
    public String adminPage(@RequestParam(defaultValue = "1") int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, SysoConstants.PAGE_SIZE, Sort.Direction.DESC, "id");
        Page<Post> posts = postService.getAllPosts(pageable);

        model.addAttribute("posts", posts);
        return "admin/admin-posts";
    }

    @GetMapping("write")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        return "admin/post-write";
    }

    @GetMapping("/modify/{postId:[0-9]+}")
    public String editPost(@PathVariable Integer postId, Model model) {
      Post post = postService.getPost(postId);
      
      model.addAttribute("post", post);
      return "admin/post-write";
    }
    
    @PostMapping("new")
    public String createPost(@Valid PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            noticeFail(bindingResult.getAllErrors().toString());
            return "admin/post-write";
        } else {
            Post post = postService.addPost(postForm, null);
            
            if (post.getStatus() == 0) {
                noticeOk("发布成功");
                return "redirect:/admin/posts";
            } else {
                noticeOk("保存成功");
                return "redirect:/admin/posts/modify/" + post.getId();
            }
        }
    }

    @PostMapping("/modify/{postId:[0-9]+}")
    public String updatePost(@PathVariable Integer postId, @Valid PostForm postForm, BindingResult bindingResult,
            Model model) {
        Post post = postService.getPost(postId);
        if (!bindingResult.hasErrors()) {
            post =postService.updatePost(post, postForm);
        }
        
        if (post.getStatus() == 0) {
            noticeOk("发布成功");
            return "redirect:/admin/posts";
        } else {
            noticeOk("保存成功");
            return "redirect:/admin/posts/modify/" + post.getId();
        }
    }

    @GetMapping("/remove/{postId:[0-9]+}")
    public String removePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        
        noticeOk("删除成功");
        return "redirect:/admin/posts";
    }

    @GetMapping("/publish/{postId:[0-9]+}")
    public String publishPost(@PathVariable Integer postId) {
        postService.publishInBatch(new Integer[]{postId});
        
        noticeOk("发布成功");
        return "redirect:/admin/posts";
    }
    
    @PostMapping("/batchPublish")
    public String batchPublish(@RequestParam(value = "postId[]") Integer[] ids) {
        postService.publishInBatch(ids);
        
        noticeOk("批量发布成功");
        return "redirect:/admin/posts";
    }
    
    @PostMapping("/batchRemove")
    public String batchRemove(@RequestParam(value = "postId[]") Integer[] ids) {
        postService.removeInBatch(ids);
        
        noticeOk("批量删除成功");
        return "redirect:/admin/posts";
    }
    
    @PostMapping("/markdown")
    public String importMarkdown(MultipartFile file) throws Exception {
        Post post = postService.parseMarkdownFile(file);
        
        noticeOk("文章[" + post.getTitle() + "]导入成功");
        return "redirect:/admin/posts";
    }
    
    @GetMapping("/preview/{postId:[0-9]+}")
    public String getPost(Model model, @PathVariable Integer postId) {
        Post post = postService.getPost(postId);
        model.addAttribute("post", post);
        return "app/post-show";
    }
}
