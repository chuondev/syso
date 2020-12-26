package com.chuonye.syso.web.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.chuonye.syso.SysoConstants;
import com.chuonye.syso.domain.JsonResponse;

public class AbstractController {
    
    @Autowired
    protected HttpSession session;
    
    protected void noticeOk(String msg) {
        session.setAttribute(SysoConstants.NOTICE_SESSION_KEY, JsonResponse.ok(msg));
    }
    
    protected void noticeFail(String msg) {
        session.setAttribute(SysoConstants.NOTICE_SESSION_KEY, JsonResponse.fail(msg));
    }
    
}
