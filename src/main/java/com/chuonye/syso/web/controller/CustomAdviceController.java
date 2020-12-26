package com.chuonye.syso.web.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.chuonye.syso.SysoConstants;
import com.chuonye.syso.service.OptionService;

/**
 * 处理（service or manager）抛出的 Exception
 * 
 * @author chuonye@foxmail.com
 */
@ControllerAdvice
public class CustomAdviceController {
    final static Logger logger = LoggerFactory.getLogger(CustomAdviceController.class);

    @Autowired
    private OptionService        optSrevice;
    
    @ModelAttribute
    public void addAttributes(HttpSession session, Model model) {
        model.addAttribute("options", optSrevice.getAllOptions());
        
        Object obj = session.getAttribute(SysoConstants.NOTICE_SESSION_KEY);
        if(Objects.nonNull(obj)) {
            model.addAttribute("notice", obj);
            session.removeAttribute(SysoConstants.NOTICE_SESSION_KEY);
        }
    }

    @ExceptionHandler(Exception.class)
    public String defaultErrorHandle(HttpServletRequest request, Exception ex, Model model) {
        logger.error("Request: " + request.getRequestURL(), ex);

        model.addAttribute("title", "Error Page");
        model.addAttribute("display", "Error Page");
        model.addAttribute("desc", "Request: " + request.getRequestURL());
        model.addAttribute("message", ex.getMessage());
        return "common/error";
    }

}
