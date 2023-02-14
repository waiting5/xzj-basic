package com.xzj.basic.exception;

/**
 * @Category com.xzj.basic.exception
 * @Description 【自定义错误页面异常】
 * @Author xiaj07
 * @Since 2022/11/17 16:19
 **/
public class ErrorPageException extends ServiceException {
    public ErrorPageException(String code,String msg) {
        super(code, msg);
    }
}
