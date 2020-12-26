package com.chuonye.syso.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 系统用户
 * 
 * @author chuonye@foxmail.com
 */
@Entity
@Table(name = "so_user", uniqueConstraints = {@UniqueConstraint(columnNames="name")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name", nullable = false)
    private String  name;
    
    @Column(name = "nickname", nullable = false)
    private String  nickname;
                                                                                                          
    @Column(name = "password", nullable = false)
    private String  password;
    
    @Column(name = "email")
    private String  email;
   
    @Column(name = "salt")
    private String  salt;

    @Column(name = "create_time")
    private Date    createTime = new Date();
    
    @Column(name = "last_login")
    private Date    lastLogin; // 上次登录时间
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getLastLogin() {
        return lastLogin;
    }
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
    @Override
    public String toString() {
        return "User {name=" + name + ", lastLogin=" + lastLogin + "}";
    }
    
}
