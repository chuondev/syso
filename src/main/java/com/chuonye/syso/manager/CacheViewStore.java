package com.chuonye.syso.manager;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chuonye.syso.service.PostService;

/**
 * 定时将阅读数持久化到数据库
 *  
 * @author chuonye@foxmail.com
 */
@Component
public class CacheViewStore {

    final static Logger logger = LoggerFactory.getLogger(CacheViewStore.class);
  
    private PostService  postService;
    private CacheManager cacheManager;
    
    @Autowired
    public CacheViewStore(PostService  postService, CacheManager cacheManager) {
        this.postService = postService;
        this.cacheManager = cacheManager;
    }
    
    @Scheduled(cron = "${app.view.cron}")
    public void cacheViewStore() {
        Cache viewCache = cacheManager.getCache(PostService.CACHE_POST_VIEWS);
        Cache userCache = cacheManager.getCache(PostService.CACHE_USERS);
        
        // 由于 Cache 不支持遍历，这里获取所有文章的 slug 进行更新
        try {
            List<String> slugs = postService.getAllPostsSlug();
            for (String slug : slugs) {
                AtomicInteger addend = viewCache.get(slug, AtomicInteger.class);
                if (Objects.nonNull(addend)) {
                    postService.updatePostViews(slug, addend.get());
                    logger.debug("{}+{}", slug, addend.get());
                }
            }
        } finally {
            viewCache.clear();
            userCache.clear();
        }
    }
}
