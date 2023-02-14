package com.xzj.mysql.plus.aspect;

import com.xzj.mysql.plus.annotation.DataSource;
import com.xzj.mysql.plus.config.DynamicDataSourceHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Category com.xzj.mysql.common
 * @Description 【功能描述】
 * @Author xiaj07
 * @Since 2022/11/21 8:51
 **/
@Aspect
@Component
public class DataSourceAspect {

    // 设置DataSource注解的切点表达式
    @Pointcut("@annotation(com.xzj.mysql.plus.annotation.DataSource)")
    public void dynamicDataSourcePointCut(){

    }

    //环绕通知
    @Around("dynamicDataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        String key = getDefineAnnotation(joinPoint).value();
        DynamicDataSourceHolder.setDynamicDataSourceKey(key);
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceHolder.removeDynamicDataSourceKey();
        }
    }

    /**
     * 先判断方法的注解，后判断类的注解，以方法的注解为准
     * @param joinPoint
     * @return
     */
    private DataSource getDefineAnnotation(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DataSource dataSourceAnnotation = methodSignature.getMethod().getAnnotation(DataSource.class);
        if (Objects.nonNull(methodSignature)) {
            return dataSourceAnnotation;
        } else {
            Class<?> dsClass = joinPoint.getTarget().getClass();
            return dsClass.getAnnotation(DataSource.class);
        }
    }

}
