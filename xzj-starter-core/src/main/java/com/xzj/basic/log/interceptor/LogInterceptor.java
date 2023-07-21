package com.xzj.basic.log.interceptor;

import cn.hutool.core.util.IdUtil;
import com.xzj.basic.log.constants.LogConstants;
import com.xzj.basic.util.SnowflakeIdUtil;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志拦截器
 *
 * @author xiazunjun
 * @date 2023/7/21 9:37
 */
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(LogConstants.TRACE_ID);
        if (traceId == null) {
            traceId = IdUtil.fastSimpleUUID();
        }
        MDC.put(LogConstants.TRACE_ID, traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        //调用结束后删除
        MDC.remove(LogConstants.TRACE_ID);
    }
}
