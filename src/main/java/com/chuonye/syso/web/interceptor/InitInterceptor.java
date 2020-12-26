package com.chuonye.syso.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chuonye.syso.SiteProperties;
import com.chuonye.syso.service.OptionService;

/**
 * 是否需要安装
 *  
 * @author chuonye@foxmail.com
 */
@Component
public class InitInterceptor implements HandlerInterceptor {

    @Autowired
    private OptionService optService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if ("1".equals(optService.getOption(SiteProperties.INITIALIZED.key()))) {
            return true;
        }

        response.sendRedirect("/init");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
