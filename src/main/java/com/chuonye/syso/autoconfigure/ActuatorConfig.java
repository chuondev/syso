package com.chuonye.syso.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.servlet.WebMvcEndpointManagementContextConfiguration;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.chuonye.syso.web.interceptor.LoginInterceptor;

/**
 * 配置拦截访问监控指标，验证登录
 * 
 * @author chuonye@foxmail.com
 */
@Configuration
public class ActuatorConfig extends WebMvcEndpointManagementContextConfiguration {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier,
            ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier,
            EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
            WebEndpointProperties webEndpointProperties, Environment environment) {

        WebMvcEndpointHandlerMapping mapping = super.webEndpointServletHandlerMapping(webEndpointsSupplier,
                servletEndpointsSupplier, controllerEndpointsSupplier, endpointMediaTypes, corsProperties,
                webEndpointProperties, environment);

        mapping.setInterceptors(loginInterceptor);
        
        return mapping;
    }

}
