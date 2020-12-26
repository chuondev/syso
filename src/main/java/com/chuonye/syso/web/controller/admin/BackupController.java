package com.chuonye.syso.web.controller.admin;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.chuonye.syso.service.BackupService;

/**
 * 后台备份与恢复
 * 
 * @author chuonye@foxmail.com
 */
@Controller
@RequestMapping("/admin/backup")
public class BackupController extends AbstractController {

    @Autowired
    private BackupService backupService;

    @GetMapping("")
    public String backupPage(Model model) {
        model.addAttribute("files", backupService.getHistoryFiles());
        return "/admin/backup";
    }

    @PostMapping("/export")
    public String exoprtData(String async) throws IOException {
        if (Objects.isNull(async)) {
            backupService.exportData();
        } else {
            if (!BackupService.backuping) {
                backupService.asyncExport();
            }
        }
        
        noticeOk("备份成功");
        return "redirect:/admin/backup";
    }

    @GetMapping("/download/{name:.+}")
    public ResponseEntity<Resource> download(@PathVariable("name") String name) {
        Resource resource = backupService.loadFileAsResource(name);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/remove/{name:.+}")
    public String remove(@PathVariable("name") String name) throws IOException {
        backupService.removeBackfile(name);
        return "redirect:/admin/backup";
    }

    @PostMapping("/import")
    public String importData(MultipartFile file, String force, Model model) throws Exception {
        backupService.importData(file, Objects.nonNull(force));
        
        noticeOk("恢复成功");
        return "redirect:/admin/backup";
    }

}
