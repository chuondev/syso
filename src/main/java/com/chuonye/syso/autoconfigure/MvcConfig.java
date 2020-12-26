package com.chuonye.syso.autoconfigure;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.chuonye.syso.SysoConstants;
import com.chuonye.syso.web.interceptor.InitInterceptor;
import com.chuonye.syso.web.interceptor.LoginInterceptor;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import freemarker.core.TemplateClassResolver;
import freemarker.template.TemplateException;

/**
 * @author chuonye@foxmail.com
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    
    private LoginInterceptor   loginInterceptor;
    private InitInterceptor    initInterceptor;
    
    @Autowired
    public MvcConfig(LoginInterceptor loginInterceptor, InitInterceptor initInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.initInterceptor = initInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
            .addPathPatterns("/admin/**")
            .excludePathPatterns("/admin/login")
            .excludePathPatterns("/admin/doLogin")
            .excludePathPatterns("/static/**");
        
        registry.addInterceptor(initInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/init")
            .excludePathPatterns("/static/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
         registry.addResourceHandler("/static/**")
             .addResourceLocations("classpath:/static/")
             .setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS).cachePublic());
         registry.addResourceHandler("/upload/**")
             .addResourceLocations("file:///" + SysoConstants.WORK_HOME + "/upload/")
             .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS).cachePublic());
         registry.addResourceHandler("/robots.txt")
             .addResourceLocations("classpath:robots.txt");
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setCache(false);
        resolver.setAllowRequestOverride(false);
        resolver.setExposeRequestAttributes(false);
        resolver.setExposeSessionAttributes(false);
        resolver.setExposeSpringMacroHelpers(true);
        registry.viewResolver(resolver);
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPaths("classpath:/templates/"/* , other */);
        configurer.setDefaultEncoding("UTF-8");

        Properties properties = new Properties();
        properties.setProperty("auto_import", "/common/macros.ftl as common");

        configurer.setFreemarkerSettings(properties);

        freemarker.template.Configuration configuration = configurer.createConfiguration();

        configuration.setNewBuiltinClassResolver(TemplateClassResolver.SAFER_RESOLVER);
//        configuration.setClassicCompatible(true);// 设置为 true 的话，访问模板需要绝对路径
        configuration.setNumberFormat("0.####");
        configuration.setDateTimeFormat("yyyy-MM-dd HH:mm");
        configurer.setConfiguration(configuration);

        return configurer;
    }
    
    
    @Bean  
    public Producer kaptchaProducer(){  
        DefaultKaptcha kaptcha = new DefaultKaptcha();  
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "125");
        properties.setProperty("kaptcha.image.height", "33");
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.textproducer.font.size", "26");
        properties.setProperty("kaptcha.textproducer.char.length", "5");
        properties.setProperty("kaptcha.textproducer.char.space","4");
        Config config = new Config(properties);
        kaptcha.setConfig(config);

        return kaptcha;
    } 
}
