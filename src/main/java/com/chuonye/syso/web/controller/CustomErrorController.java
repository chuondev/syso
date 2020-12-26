package com.chuonye.syso.web.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 处理 404 500
 *  
 * @author chuonye@foxmail.com
 */
@Controller
public class CustomErrorController extends AbstractErrorController {

    private static final String ERROR_PATH = "/error";
    
    public CustomErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    /**
     * 渲染 404，500
     *
     * @param request request
     * @return String
     */
    @GetMapping(value = ERROR_PATH)
    public String handleError(HttpServletRequest request, Model model) {
        Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
        
        HttpStatus status = HttpStatus.valueOf(code);
        if (Objects.isNull(status)) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        
        model.addAttribute("title", status.value() + " " + status.getReasonPhrase());
        model.addAttribute("display", status.value());
        model.addAttribute("desc", getDesc(status));
        return "common/error";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
    
    private String getDesc(HttpStatus status) {
        String msg = "";
        if (status == HttpStatus.NOT_FOUND) {
            msg = "抱歉，您访问的页面不存在";
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            msg = "抱歉，服务器内部错误，我们的锅";
        }
        return msg;
    }
    
}
