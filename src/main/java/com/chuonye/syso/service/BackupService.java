package com.chuonye.syso.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import com.chuonye.syso.SysoConstants;
import com.chuonye.syso.domain.entity.Media;
import com.chuonye.syso.domain.entity.Post;
import com.chuonye.syso.domain.vo.BackupFileView;
import com.chuonye.syso.utils.JsonUtils;
import com.chuonye.syso.utils.Tools;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 系统备份与恢复服务
 *  
 * @author chuonye@foxmail.com
 */
@Service
public class BackupService {
    final static Logger logger = LoggerFactory.getLogger(BackupService.class);
    
    public static volatile boolean backuping = false;
    
    @Value("${app.version}")
    private String appVersion;
    
    private PostService postService;
    private MediaService mediaService;
    
    @Autowired
    public BackupService(PostService postService, MediaService mediaService) {
        this.postService = postService;
        this.mediaService = mediaService;
    }

    @Async
    public void asyncExport() throws IOException {
        backuping = true;
        try {
            exportData();
        } finally {
            backuping = false;
        }
    }
    
    public void exportData() throws IOException {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("version", appVersion);
        data.put("posts", postService.getAllPosts());
        
        List<Media> medias = mediaService.getAllMedias();
        HashMap<String, String> mediasContent = new HashMap<>(medias.size());
        for (Media media:medias) {
            if (media.getSize() < 10 * 1024 * 1024) { // < 10MB
                String base64Content = Base64Utils.encodeToString(Files.readAllBytes(SysoConstants.WORK_HOME.resolve(media.getPath())));
                mediasContent.put(media.getPath(), base64Content);
            } // else > 10MB skip
        }
        data.put("medias", medias);
        data.put("medias_content", mediasContent);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
        StringBuilder sb = new StringBuilder(50);
        sb.append("syso-export-data-")
          .append(sdf.format(new Date()))
          .append(Tools.randomString(8))
          .append(".json");
        
        Path file = Paths.get(System.getProperty("java.io.tmpdir"), sb.toString());
        Files.deleteIfExists(file);
        
        String jsonData = JsonUtils.objectToJson(data);
        Files.write(file, jsonData.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
    }
    
    public List<BackupFileView> getHistoryFiles() {
        List<BackupFileView> backFiles = null;
        
        File dir = Paths.get(System.getProperty("java.io.tmpdir")).toFile();
        
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files.length > 0) {
                backFiles = new LinkedList<>();
                
                for (File file : files) {
                    if (file.isFile() && file.getName().startsWith("syso-export-data-")) {
                        backFiles.add(createView(file));
                    }
                }
            }
        }
        
        if (Objects.isNull(backFiles)) {
            return Collections.emptyList();
        } else {
            Collections.sort(backFiles);
            return backFiles;
        }
    }
    
    public BackupFileView createView(File file) {
        BackupFileView backup = new BackupFileView();
        backup.setName(file.getName());
        backup.setSize(file.length());
        backup.setCreateTime(file.lastModified());
        return backup;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void importData(MultipartFile file, boolean force) throws Exception {
        if (force) {
            postService.deleteAllPosts();
            mediaService.deleteAllMedias();
        }
        
        String json = new String(file.getBytes(), StandardCharsets.UTF_8);
        JsonNode data = JsonUtils.jsonToJsonNode(json);
        
        postService.addBatch(JsonUtils.jsonToList(data.get("posts").toString(), Post.class));
        mediaService.addBatch(JsonUtils.jsonToList(data.get("medias").toString(), Media.class));
        
        Map<String, String> mediasContent = JsonUtils.jsonToMap(data.get("medias_content").toString(), String.class, String.class);
        for (Entry<String, String> entry : mediasContent.entrySet()) {
            Path path = SysoConstants.WORK_HOME.resolve(entry.getKey());
            byte[] content = Base64Utils.decodeFromString(entry.getValue());
            
            Path parentDir = path.getParent();
            if (!Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }
            
            Files.deleteIfExists(path);
            Files.write(path, content, StandardOpenOption.CREATE);
        }
    }

    public Resource loadFileAsResource(String name) {
        Path file = Paths.get(System.getProperty("java.io.tmpdir"), name).normalize();
        try {
            Resource res = new UrlResource(file.toUri());
            if (res.exists()) {
                return res;
            } else {
                throw new RuntimeException("File not found " + name);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found " + name, e);
        }
    }

    public void removeBackfile(String name) throws IOException {
        Path file = Paths.get(System.getProperty("java.io.tmpdir"), name).normalize();
        Files.deleteIfExists(file);
    }
}
