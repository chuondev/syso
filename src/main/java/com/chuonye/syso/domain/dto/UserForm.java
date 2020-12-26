package com.chuonye.syso.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 接收和校验更新用户信息页面参数
 * 
 * @author chuonye@foxmail.com
 */
public class UserForm {
    
    @NotNull(message = "用户 ID 不能为空")
    private Integer  userId;

    @NotEmpty(message = "登录名不能为空")
    private String   name;
    
    private String   nickname;
    
    @Email(message = "邮箱格式不正确")
    private String   email;

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "UserForm {userId=" + userId + ", name=" + name + ", email=" + email + "}";
    }
}
