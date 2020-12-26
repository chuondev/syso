package com.chuonye.syso.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chuonye.syso.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
    
    Page<Post> findByStatus(Integer status, Pageable pageable);
    
    Post findBySlug(String slug);
    
    Post findBySlugAndStatus(String slug, Integer status);
    
    List<Post> findByStatusOrderByCreateTimeDesc(Integer status);

    List<Post> findTop5ByOrderByViewsDesc();
    
    List<Post> findTop5ByOrderByCreateTimeDesc();

    Post findTopByStatusAndCreateTimeAfterOrderByCreateTimeAsc(int published, Date date);

    Post findTopByStatusAndCreateTimeBeforeOrderByCreateTimeDesc(int published, Date date);

    @Query(value = "select slug from so_post", nativeQuery=true)
    List<String> findAllSlug();
    
    @Query(value = "select sum(views) from so_post", nativeQuery=true)
    Long countViews();
    
    @Modifying
    @Query(value = "update so_post set status = :status where id = :id", nativeQuery = true)
    void updatePostStatus(@Param("id") Integer id, @Param("status") Integer status);
    
}
