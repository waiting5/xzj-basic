package com.xzj.basic.aspect;

import com.xzj.basic.base.dto.AjaxResult;
import com.xzj.basic.cache.ThreadTimeCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Category com.xzj.basic.aspect
 * @Description 【功能描述】
 * @Author xiaj07
 * @Since 2022/11/17 14:48
 **/
@Slf4j
@Aspect
@Component
public class WebAspect {

    @Pointcut("execution(public * com..controller..*(..))")
    public void pointCut() {
        // 只是作为一个切入点，没有实际意义
    }

    @Before("pointCut()")
    public void before() {
        ThreadTimeCache.putStartTime();
    }

    @AfterReturning(returning = "result",pointcut = "pointCut()")
    public void doAfterReturning(AjaxResult result) {
        ThreadTimeCache.countTime(result);
    }
}
