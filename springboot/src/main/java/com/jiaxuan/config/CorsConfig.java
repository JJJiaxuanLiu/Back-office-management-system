package com.jiaxuan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Configuration
public class CorsConfig {
    //当前请求最大有效期 一天
    private static final long MAX_AGE = 24 * 60 *60;

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080"); //1 设置访问原地址
        corsConfiguration.addAllowedHeader("*"); //2 设置访问源请求头
        corsConfiguration.addAllowedMethod("*"); //3 设置访问源请求方法
        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", corsConfiguration); //4 对接口配置跨域设置
        return new CorsFilter(source);

    }
}
