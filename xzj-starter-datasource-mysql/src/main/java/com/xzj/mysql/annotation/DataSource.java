package com.xzj.mysql.annotation;

import com.xzj.mysql.constants.CommonConstant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Category com.xzj.mysql.common
 * @Description 【自定义多数据源切换注解:优先级：先方法，后类，如果方法覆盖了类上的数据源类型，以方法的为准，否则以类上的为准】
 * @Author xiaj07
 * @Since 2022/11/21 8:49
 **/
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource
{
    /**
     * 切换数据源名称
     */
    public String value() default CommonConstant.MASTER;

}
