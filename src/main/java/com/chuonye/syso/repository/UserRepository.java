package com.chuonye.syso.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chuonye.syso.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    
    @Modifying
    @Query(value = "update so_user set last_login = :now where id = :id", nativeQuery = true)
    void updateLastLogin(@Param("now") Date now, @Param("id") Integer id);
}
