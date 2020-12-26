package com.chuonye.syso.web.listener;

import java.util.Enumeration;
import java.util.Objects;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chuonye.syso.SysoConstants;
import com.chuonye.syso.domain.entity.User;
import com.chuonye.syso.service.UserService;

/**
 * session timeout or remove 记录用户最后登录时间
 *  
 * @author chuonye@foxmail.com
 */
@Component
public class CustomSessionListener implements HttpSessionListener {
    final static Logger logger = LoggerFactory.getLogger(CustomSessionListener.class);
    
    @Autowired
    private UserService userService;
    
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log(se, "Session Created");    
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Object obj = se.getSession().getAttribute(SysoConstants.USER_SESSION_KEY);
        if (Objects.nonNull(obj)) {
            User user = (User) obj;
            userService.updateLastLoginTime(user);
        }
        
        log(se, "Session Destroyed");
    }
    
    private void log(HttpSessionEvent se, String key) {
        HttpSession session = se.getSession();
        Enumeration<String> names = session.getAttributeNames();
        logger.debug("=========={}-{}===========", key, session.getId());
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = String.valueOf(session.getAttribute(name));
            logger.debug("name=" + name +", value=" + value);
        }
        logger.debug("====================================");
    
    }
    
}
