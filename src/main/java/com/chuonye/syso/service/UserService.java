package com.chuonye.syso.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.chuonye.syso.domain.dto.UserForm;
import com.chuonye.syso.domain.entity.User;
import com.chuonye.syso.repository.UserRepository;

/**
 * 用户基本操作
 * 
 * @author chuonye@foxmail.com
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userDao;
    
    public User getUser(Integer userId) {
        return userDao.findById(userId).orElse(new User());
    }
    
    public User getOneUser() {
        List<User> users = userDao.findAll();
        if (users != null && users.size() > 0) {
            return users.get(0);
        } else {
            return new User();
        }
    }
    
    @Transactional
    public User addUser(User user) {
        return userDao.save(user);
    }
    
    @Transactional
    public void updateLastLoginTime(User user) {
        userDao.updateLastLogin(new Date(), user.getId());
    }
    
    public void updateUserByUserForm(User user, UserForm userForm) {
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        
        if (StringUtils.hasLength(userForm.getNickname())) {
            user.setNickname(userForm.getNickname());
        } else {
            user.setNickname(userForm.getName());
        }
    }
}
