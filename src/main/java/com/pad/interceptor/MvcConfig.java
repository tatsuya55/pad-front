package com.pad.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry
        .addInterceptor(new LoginInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns(
                "/","/index","/index.html",
                "/assets/**","/bootstrap-3.3.7-dist/**","/images/**","/jquery/**","/layui/**",
                "/signIn","/signUp",
                "/company/**",
                "/register",
                "/alipay/notify");
    }
}
