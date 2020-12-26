package com.chuonye.syso.service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.NullValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.chuonye.syso.SysoConstants;
import com.chuonye.syso.domain.dto.PostForm;
import com.chuonye.syso.domain.entity.Post;
import com.chuonye.syso.repository.PostRepository;
import com.chuonye.syso.utils.MarkdownUtil;
import com.chuonye.syso.utils.Tools;

/**
 * 文章的基本和高级操作，以及控制选择性访问缓存数据
 * 
 * @author chuonye@foxmail.com
 */
@Service
public class PostService {
    final static Logger logger = LoggerFactory.getLogger(PostService.class);

    public static final String      CACHE_POSTS = "cache.posts";
    public static final String      CACHE_USERS = "cache.users";
    public static final String CACHE_POST_VIEWS = "cache.post.views";

    private PostRepository postDao;
    private CacheManager   cacheManager;

    @Autowired
    public PostService(PostRepository postDao, CacheManager cacheManager) {
        this.postDao = postDao;
        this.cacheManager = cacheManager;
    }

    //////// 前台 ////////
    
    @Cacheable(value = CACHE_POSTS, key = "'all-posts'")
    public List<Post> getAllPublishedPosts() {
        return postDao.findByStatusOrderByCreateTimeDesc(SysoConstants.PUBLISHED);
    }
    
    /**
     * 分页获取文章列表，缓存前 2 页数据
     * 
     * @param page 分页数据
     * @return Page&lt;Post&gt;
     */
    @Cacheable(value = CACHE_POSTS, key = "'page-' + #page.getPageNumber()", condition = "#page.getPageNumber() < 2")
    public Page<Post> getPublishedPosts(Pageable page) {
        return postDao.findByStatus(SysoConstants.PUBLISHED, page);
    }
    
    /** 根据缩略名 slug 获取文章 */
    @Cacheable(value = CACHE_POSTS, key = "#slug")
    public Post getPublishedPost(String slug) {
        Post post = postDao.findBySlugAndStatus(slug, SysoConstants.PUBLISHED);
        if (Objects.isNull(post)) {
            throw new RuntimeException("查询不到该文章的信息");
        }
        return post;
    }

    public Post getPrevPost(Date date) {
        return postDao.findTopByStatusAndCreateTimeBeforeOrderByCreateTimeDesc(SysoConstants.PUBLISHED, date);
    }

    public Post getNextPost(Date date) {
        return postDao.findTopByStatusAndCreateTimeAfterOrderByCreateTimeAsc(SysoConstants.PUBLISHED, date);
    }

    /**
     * 文章阅读数，只更新缓存，在缓存'过期'或'被驱逐'时持久化到数据库
     * 
     * @param user ip+slug，此 IP 4 小时内阅读数只加一次
     * @param slug 文章唯一缩略名
     */
    public void cacheViews(String user, String slug) {
        Cache cachedUser = cacheManager.getCache(CACHE_USERS);
        // 此 IP 是否已经阅读此文章
        if (cachedUser.get(user) == null) {
            cachedUser.put(user, NullValue.INSTANCE);

            Cache cache = cacheManager.getCache(CACHE_POST_VIEWS);
            
            AtomicInteger cachedViews = cache.get(slug, AtomicInteger.class);
            if (Objects.isNull(cachedViews)) {
                cache.putIfAbsent(slug, new AtomicInteger());
                cachedViews = cache.get(slug, AtomicInteger.class);
            }
            
            // +1
            cachedViews.incrementAndGet();
        }
    }
    
    @Transactional
    @CachePut(value = CACHE_POSTS, key = "#slug")
    public Post updatePostViews(String slug, Integer addend) {
        // 从缓存或数据库中获取文章
        Post post = getPublishedPost(slug);
        post.setViews(post.getViews() + addend);
        // 更新数据库
        return postDao.save(post);
    }
    


    /** 生成 RSS */

    /** 生成 Sitemap */

    //////// 后台 ////////

    public Long getTotal() {
        return postDao.count();
    }

    public Long getTotalViews() {
        return postDao.countViews();
    }

    public List<Post> getLatestPosts() {
        return postDao.findTop5ByOrderByCreateTimeDesc();
    }

    public List<Post> getAllPosts() {
        return postDao.findAll();
    }
    
    public Page<Post> getAllPosts(Pageable pageRequest) {
        return postDao.findAll(pageRequest);
    }

    public Page<Post> getDraftPosts(Pageable pageRequest) {
        return postDao.findByStatus(SysoConstants.DRAFT, pageRequest);
    }
    
    public List<String> getAllPostsSlug() {
        return postDao.findAllSlug();
    }
    
    
    @Transactional
    @CacheEvict(value = CACHE_POSTS, allEntries = true, beforeInvocation = true)
    public void addBatch(List<Post> posts) {
        postDao.saveAll(posts);
    }
    
    @Transactional
    @CacheEvict(value = CACHE_POSTS, allEntries = true, beforeInvocation = true)
    public Post addPost(PostForm postForm, Date fixedCreateTime) {
        Post post = null;
        // 缩略名
        String slug = postForm.getSlug();
        if (StringUtils.hasLength(slug)) {
            post = postDao.findBySlug(slug);
            if (Objects.isNull(post)) {
                post = new Post();
                post.setSlug(slug);
            }
        } else {
            post = new Post();
        }
        
        if (Objects.nonNull(fixedCreateTime)) {
            post.setCreateTime(fixedCreateTime);
        }

        setPostProperties(post, postForm);

        if (!StringUtils.hasLength(post.getSlug())) {
            // 如果缩略名为空，默认使用编号
            post = postDao.save(post);
            post.setSlug(String.valueOf(post.getId()));
        }
        
        post = postDao.save(post);

        return post;
    }
    
    @Transactional
    @CacheEvict(value = CACHE_POSTS, allEntries = true, beforeInvocation = true)
    public Post updatePost(Post post, PostForm postForm) {
        setPostProperties(post, postForm);

        String slug = postForm.getSlug();
        if (StringUtils.hasLength(slug)) {
            post.setSlug(slug);
        }

        post = postDao.save(post);

        return post;
    }

    private void setPostProperties(Post post, PostForm postForm) {
        post.setUpdateTime(new Date());
        post.setTitle(postForm.getTitle());
        post.setRawContent(postForm.getContent());

        // markdown -> html
        String html = MarkdownUtil.renderToHtml(postForm.getContent());
        post.setRenderedContent(html);

        // 提取摘要
        int summary = 50;
        String cleanedHtml = Tools.stripTags(html);
        if (html.length() > summary) {
            post.setSummary(cleanedHtml.substring(0, summary) + " ...");
        } else {
            post.setSummary(cleanedHtml);
        }

        // 草稿?已发布?
        int stat = "published".equals(postForm.getAction()) ? SysoConstants.PUBLISHED : SysoConstants.DRAFT;
        post.setStatus(stat);
    }

    /** 根据编号删除文章 */
    @CacheEvict(value = CACHE_POSTS, allEntries = true, beforeInvocation = true)
    public void deletePost(Integer postId) {
        postDao.deleteById(postId);
    }

    /** 根据 id 获取文章 */
    public Post getPost(Integer postId) {
        return postDao.findById(postId).orElse(new Post());
    }

    /** 发布文章 */
    @Transactional
    @CacheEvict(value = CACHE_POSTS, allEntries = true, beforeInvocation = true)
    public void publishInBatch(Integer[] ids) {
        for (Integer id : ids) {
            postDao.updatePostStatus(id, SysoConstants.PUBLISHED);
        }
    }

    @CacheEvict(value = CACHE_POSTS, allEntries = true, beforeInvocation = true)
    public void removeInBatch(Integer[] ids) {
        for (Integer id : ids) {
            postDao.deleteById(id);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CACHE_POSTS, allEntries = true, beforeInvocation = true)
    public Post parseMarkdownFile(MultipartFile file) throws Exception {
        String markdown = new String(file.getBytes(), StandardCharsets.UTF_8);
        Map<String, List<String>> frontMatter = MarkdownUtil.getYamlFrontMatter(markdown);
        
        PostForm postForm = new PostForm();
        Date fixedCreateTime = null;
        
        for (Entry<String, List<String>> entry : frontMatter.entrySet()) {
            String key = entry.getKey();
            for (String val : entry.getValue()) {
                val = val.trim();
                if ("".equals(val)) continue;
                
                switch (key) {
                case "title":
                    postForm.setTitle(val);
                    break;
                case "permalink":
                    postForm.setSlug(val);
                    break;
                case "status":
                    postForm.setAction(val);
                case "date": //yyyy-MM-dd HH:mm:ss
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    fixedCreateTime = sdf.parse(val);
                    break;
                default:
                    break;
                }
            }
        }
        
        postForm.setContent(markdown);
        
        if (StringUtils.isEmpty(postForm.getTitle())) {
            postForm.setTitle(file.getOriginalFilename());
        }
        if (Objects.isNull(postForm.getAction())) {
            postForm.setAction("published");
        }
        
        return addPost(postForm, fixedCreateTime);
    }
    
    @CacheEvict(value = {CACHE_POSTS, CACHE_USERS, CACHE_POST_VIEWS}, allEntries = true, beforeInvocation = true)
    public void deleteAllPosts() {
        postDao.deleteAllInBatch();
    }
}
