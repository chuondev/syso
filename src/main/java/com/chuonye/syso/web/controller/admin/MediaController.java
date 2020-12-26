package com.chuonye.syso.web.controller.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.chuonye.syso.SysoConstants;
import com.chuonye.syso.domain.entity.Media;
import com.chuonye.syso.service.MediaService;

/**
 * 后台资源增删改
 *  
 * @author chuonye@foxmail.com
 */
@Controller
@RequestMapping("/admin/medias")
public class MediaController extends AbstractController{
    
    @Autowired
    private MediaService mediaService;

    @GetMapping("")
    public String adminPage(@RequestParam(defaultValue = "1") int page, Model model) {
        Pageable pageable = PageRequest.of(page-1, SysoConstants.PAGE_SIZE, Sort.Direction.DESC, "id");
        Page<Media> medias = mediaService.getAll(pageable);
        
        model.addAttribute("medias", medias);
        return "admin/admin-medias";
    }

    @PostMapping("/upload")
    public String uploadMedia(List<MultipartFile> files, HttpServletRequest request) throws IOException {
      
      if (files != null && files.size() > 0) {
          mediaService.addMedia(files, request);
      }
      
      noticeOk("上传成功");
      return "redirect:/admin/medias";
    }
    
    @GetMapping("/remove")
    public String removeMedia(Integer mediaId) {
        mediaService.removeById(mediaId);
        
        noticeOk("删除成功");
        return "redirect:/admin/medias";
    }
    
    @PostMapping("/batchRemove")
    public String batchRemove(@RequestParam(value = "mediaId[]") Integer[] ids) {
        for (Integer id : ids) {
            mediaService.removeById(id);
        }
        
        noticeOk("批量删除成功");
        return "redirect:/admin/medias";
    }
}
