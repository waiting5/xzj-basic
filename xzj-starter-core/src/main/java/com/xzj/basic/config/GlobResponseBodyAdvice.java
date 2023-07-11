package com.xzj.basic.config;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xzj.basic.base.dto.AjaxResult;
import com.xzj.basic.cache.ThreadTimeCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import javax.annotation.Resource;

/**
 * TODO 描述
 *
 * @author xiazunjun
 * @date 2023/7/3 11:37
 */
@RestControllerAdvice
@Slf4j
public class GlobResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private ObjectMapper objectMapper;


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 处理返回结果
     */
    @Override
    public Object beforeBodyWrite(Object result, MethodParameter methodParameter, MediaType mediaType,
        Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
        ServerHttpResponse serverHttpResponse) {
        AjaxResult ajaxResult;
        // 返回结果不为空，且返回类型已经封装
        if(ObjectUtil.isNotEmpty(result) && result instanceof AjaxResult){
            ajaxResult = (AjaxResult)result;
        }else {
            ajaxResult = AjaxResult.successResult().putData(result);
            ThreadTimeCache.countTime(ajaxResult);
        }
        return ajaxResult;
    }
}
