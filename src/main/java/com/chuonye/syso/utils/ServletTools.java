package com.chuonye.syso.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public final class ServletTools {

    final static Logger logger = LoggerFactory.getLogger(ServletTools.class);

    /***
     * 尝试获取客户端真实 IP，可能有代理，通过以下 Header:
     * 
     * <pre>
     * 1. X-Forwarded-For
     * 2. X-Real-IP
     * </pre>
     * 
     * @param request {@link HttpServletRequest}
     * @return IP 地址
     */
    public static String getClientIp(HttpServletRequest request) {
        String[] headers = { "X-Forwarded-For", "X-Real-IP" };
        String ip = null;
        for (String name : headers) {
            ip = request.getHeader(name);
            if (StringUtils.hasLength(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip; // 这里没有考虑多级代理的情况
            }
        }
        ip = request.getRemoteAddr();
        return ip;
    }

    /**
     * 设置客户端 Cookie
     * 
     * @param response HttpServletResponse
     * @param name  Cookie 名称
     * @param value Cookie 值，为了存储中文内部会使用 URLEncoder 编码
     * @param expires 过期时间，-1：浏览器关闭时清除 Cookie；0：立即清除 Cookie；&gt;0：Cookie 存在的秒数
     */
    public static void setCookie(HttpServletResponse response, String name, String value, Integer expires) {
        if (!StringUtils.hasText(value))
            return;

        try {
            // Cookie 不能直接存中文，编码一下
            value = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ue) {
            // 应该都支持吧？
        }

        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(expires);
        // cookie.setHttpOnly(true); // true js 不能访问
        response.addCookie(cookie);
    }

    /**
     * 删除 Cookie
     */
    public static void unsetCookie(HttpServletRequest request) {
    }
    
    
    public static String getSiteUrl(HttpServletRequest request) {
        String fullUrl = request.getRequestURL().toString();
        return fullUrl.substring(0, Tools.ordinalIndexOf(fullUrl, "/", 3));
    }
}
