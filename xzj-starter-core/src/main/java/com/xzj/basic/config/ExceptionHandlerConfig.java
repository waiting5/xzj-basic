package com.xzj.basic.config;

import com.xzj.basic.base.dto.AjaxResult;
import com.xzj.basic.base.enums.BaseEnum;
import com.xzj.basic.exception.ErrorPageException;
import com.xzj.basic.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Category com.xzj.basic.config
 * @Description 【统一异常处理，捕获并返回统一返回对象Result，同时把异常信息打印到日志中】
 * @Author xiaj07
 * @Since 2022/11/17 16:15
 **/
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerConfig {

    /**
     * 业务异常 统一处理
     */
    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public Object exceptionHandler400(ServiceException e){
        return returnResult(e, AjaxResult.failResult().putEnum(e.getErrorEnum()));
    }

    /**
     * 错误页面异常 统一处理
     */
    @ExceptionHandler(value = ErrorPageException.class)
    @ResponseBody
    public Object exceptionHandler(ErrorPageException e){
        BaseEnum errorEnum;
        switch (Integer.parseInt(e.getCode())) {
            case 404:
                errorEnum = BaseEnum.NOT_FOUND;
                break;
            case 403:
                errorEnum = BaseEnum.FORBIDDEN;
                break;
            case 401:
                errorEnum = BaseEnum.UNAUTHORIZED;
                break;
            case 400:
                errorEnum = BaseEnum.BAD_REQUEST;
                break;
            default:
                errorEnum = BaseEnum.UNKNOWN;
                break;
        }

        return returnResult(e,AjaxResult.failResult().putEnum(errorEnum));
    }

    /**
     * 空指针异常 统一处理
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public Object exceptionHandler500(NullPointerException e){
        return returnResult(e,AjaxResult.failResult().putEnum(BaseEnum.INTERNAL_SERVER_ERROR));
    }

    /**
     * 其他异常 统一处理
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Object exceptionHandler(Exception e){
        AjaxResult ajaxResult = AjaxResult.failResult().putCode(BaseEnum.UNKNOWN.getCode());
        return returnResult(e,ajaxResult.putMsg("【" + e.getClass().getName() + "】" + e.getMessage()));
    }

    /**
     * 是否为ajax请求
     * ajax请求，响应json格式数据，否则应该响应html页面
     */
    private Object returnResult(Exception e,AjaxResult errorResult){
        //把错误信息输入到日志中
        log.error("{}",e.getMessage(),e);
        return errorResult;
    }

}
