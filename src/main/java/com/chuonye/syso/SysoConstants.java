package com.chuonye.syso;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 常量配置
 *  
 * @author chuonye@foxmail.com
 */
public final class SysoConstants {
    
    public static final String  USER_SESSION_KEY    = "_syso_user";
    public static final String  NOTICE_SESSION_KEY  = "_syso_notice";
    public static final String  KAPTCHA_SESSION_KEY = "_syso_captcha";
    
    public static final Path WORK_HOME = Paths.get(System.getProperty("syso.base"));
    
    public static final int PAGE_SIZE = 20;
    
    /** 已发布 */
    public static final int PUBLISHED = 0;
    
    /** 草稿 */
    public static final int DRAFT = 1;
}
