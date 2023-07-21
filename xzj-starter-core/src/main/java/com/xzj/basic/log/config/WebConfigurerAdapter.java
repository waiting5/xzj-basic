package com.xzj.basic.log.config;

import com.xzj.basic.log.interceptor.LogInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mdc 日志自动配置类
 *
 * @author xiazunjun
 * @date 2023/7/21 11:24
 */
@Configuration
public class WebConfigurerAdapter implements WebMvcConfigurer {

    @ConditionalOnMissingBean
    @Bean
    public LogInterceptor logInterceptor(){
        return new LogInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor());
    }

}
