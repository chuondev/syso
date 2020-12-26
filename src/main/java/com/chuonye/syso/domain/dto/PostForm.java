package com.chuonye.syso.domain.dto;

import javax.validation.constraints.NotEmpty;

/**
 * 接收和校验编辑文章页面参数
 * 
 * @author chuonye@foxmail.com
 */
public class PostForm {
    private Integer  postId;

    @NotEmpty(message = "文章标题不能为空")
    private String   title;

    @NotEmpty(message = "文章内容不能为空")
    private String   content;

    private String   slug;

    private Integer  catgId;

    /** publish-发布, draft-存草稿, preview-预览 */
    private String   action;

    public Integer getPostId() {
        return postId;
    }
    public void setPostId(Integer postId) {
        this.postId = postId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSlug() {
        return slug;
    }
    public void setSlug(String slug) {
        this.slug = slug;
    }
    public Integer getCatgId() {
        return catgId;
    }
    public void setCatgId(Integer catgId) {
        this.catgId = catgId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "PostFormDto {postId=" + postId + ", title=" + title + ", content=" + content + ", slug=" + slug
                + ", category=" + catgId + ", action=" + action + "}";
    }
}
