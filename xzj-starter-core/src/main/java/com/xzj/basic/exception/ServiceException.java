package com.xzj.basic.exception;

import com.xzj.basic.base.enums.BaseEnum;
import lombok.Data;

/**
 * @Category com.xzj.basic.exception
 * @Description 【自定义业务异常】
 * @Author xiaj07
 * @Since 2022/11/17 16:10
 **/
@Data
public class ServiceException extends RuntimeException {

    /**
     * 自定义异常枚举类
     */
    private BaseEnum errorEnum;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String errorMsg;


    public ServiceException() {
        super();
    }

    public ServiceException(BaseEnum errorEnum) {
        super("{code:" + errorEnum.getCode() + ",errorMsg:" + errorEnum.getDesc() + "}");
        this.errorEnum = errorEnum;
        this.code = errorEnum.getCode();
        this.errorMsg = errorEnum.getDesc();
    }

    public ServiceException(String code,String errorMsg) {
        super("{code:" + code + ",errorMsg:" + errorMsg + "}");
        this.code = code;
        this.errorMsg = errorMsg;
    }
}
