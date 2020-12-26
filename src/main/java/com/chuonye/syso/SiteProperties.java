package com.chuonye.syso;

/**
 * 配置项
 *  
 * @author chuonye@foxmail.com
 */
public enum SiteProperties {
    
    /** 0-未安装 1-已安装 */
    INITIALIZED("initialized", "0"),
    
    SITE_TITLE("siteTitle", ""),
    SITE_URL("siteUrl",  ""),
    SITE_LOGO("siteLogo",  ""),
    SITE_FAVICON("siteFavicon",  ""),
    SITE_DESC("siteDesc",  ""),
    SITE_KEYWORDS("siteKeywords",  ""),
    SITE_ANALYTICS("siteAnalytics",  "");
    
    private String key;
    private String defaultValue;
    
    private SiteProperties(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String key() {
        return key;
    }

    public String defaultValue() {
        return defaultValue;
    }
}
