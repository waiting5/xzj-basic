package com.xzj.basic.config;

import com.xzj.basic.aspect.WebAspect;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Category com.xzj.basic.config
 * @Description 【基础starter中添加配置扫描】
 * @Author xiaj07
 * @Since 2022/11/18 8:47
 **/
@Configuration
// 先于加载ErrorMvcAutoConfiguration中的BasicErrorController，使用自己重写的方法
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class BaseAutoConfig {

    /**
     * 增加controller层访问切面控制
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public WebAspect webAspect(){
        return new WebAspect();
    }

    /**
     * 重写BasicErrorController方法
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public BasicErrorController errorPageConfig(){
        return new ErrorPageConfig();
    }

    /**
     * 增加统一异常处理机制
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public ExceptionHandlerConfig exceptionHandlerConfig(){
        return new ExceptionHandlerConfig();
    }
}
