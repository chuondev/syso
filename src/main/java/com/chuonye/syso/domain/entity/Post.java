package com.chuonye.syso.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 文章
 * 
 * @author chuonye@foxmail.com
 */
@Entity
@Table(name = "so_post", indexes = { @Index(name = "idx_update_time", columnList = "update_time") })
public class Post implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String  title;

    @Column(name = "slug", unique = true)
    private String  slug;

    @Column(name = "raw_content", nullable = false)
    @Lob
    private String  rawContent;

    @Column(name = "rendered_content", nullable = false)
    @Lob
    private String  renderedContent;

    @Column(name = "summary")
    private String  summary;

    @Column(name = "status")
    private Integer status;

    @Column(name = "views")
    private Integer views = 0;

    @Column(name = "create_time", nullable = false)
    private Date    createTime = new Date();

    @Column(name = "update_time", nullable = false)
    private Date    updateTime = new Date();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public String getRenderedContent() {
        return renderedContent;
    }

    public void setRenderedContent(String renderedContent) {
        this.renderedContent = renderedContent;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", title=" + title + '}';
    }
}