package com.chuonye.syso.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.chuonye.syso.SysoConstants;
import com.chuonye.syso.domain.entity.Media;
import com.chuonye.syso.repository.MediaRepository;
import com.chuonye.syso.utils.Tools;

/**
 * 媒体资源的基本和高级操作
 *  
 * @author chuonye@foxmail.com
 */
@Service
public class MediaService {
    
    @Autowired
    private MediaRepository mediaDao;

    public List<Media> getAllMedias() {
        return mediaDao.findAll();
    }

    public Page<Media> getAll(Pageable pageable) {
        return mediaDao.findAll(pageable);
    }
    
    @Transactional(rollbackFor = IOException.class)
    public void addMedia(List<MultipartFile> files, HttpServletRequest request) throws IOException {
        // 获取上传路径 /uploads/year/month
        LocalDateTime now = LocalDateTime.now();
        
        String nowYear = String.valueOf(now.getYear());
        String nowMonth = String.valueOf(now.getMonthValue());
        
        String basePath = "upload/" + nowYear + "/" + nowMonth + "/";
        
        Path uploadPath = SysoConstants.WORK_HOME.resolve(basePath);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // 保存文件
        for (MultipartFile rawFile : files) {
          String rawFileName = rawFile.getOriginalFilename();
          if ("".equals(rawFileName)) continue;
          
          Media media = new Media();
          media.setName(rawFileName);
          media.setMimeType(rawFile.getContentType());
          media.setSize(rawFile.getSize());
         
          // 重命名资源, 格式: 资源名称-毫秒数.后缀名
          String nameWithoutSuffix = rawFileName.substring(0, rawFileName.lastIndexOf('.'));
          String suffix = rawFileName.substring(rawFileName.lastIndexOf('.'));
          
          StringBuilder sb = new StringBuilder(nameWithoutSuffix.length() + suffix.length() + 1 + 13);
          sb.append(nameWithoutSuffix)
            .append('-')
            .append(System.currentTimeMillis())
            .append(suffix);
          String newName =  sb.toString();
          
          media.setPath(basePath + newName);
          
          // 保存到磁盘
          Path diskFile = uploadPath.resolve(newName);
          Files.copy(rawFile.getInputStream(), diskFile, StandardCopyOption.REPLACE_EXISTING);
          
          // 插入数据库
          mediaDao.save(media);
        }
    }
    
    @Transactional
    public void addBatch(List<Media> medias) {
        mediaDao.saveAll(medias);
    }
    
    public void removeById(Integer mediaId) {
        Media res = mediaDao.findById(mediaId).get();//.orElse(new Resource())
        Path targetFile = SysoConstants.WORK_HOME.resolve(res.getPath());
        try {
            Files.deleteIfExists(targetFile);
          } catch (IOException e) {
              throw new RuntimeException("删除资源[" + targetFile.toString() + "]失败", e);
          }
  
        mediaDao.deleteById(mediaId);
    }
    
    public void deleteAllMedias() throws IOException {
        Tools.deleteDirectory(SysoConstants.WORK_HOME.resolve("upload"));
        
        mediaDao.deleteAllInBatch();
    }
}
