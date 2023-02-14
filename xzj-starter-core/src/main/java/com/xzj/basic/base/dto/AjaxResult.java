package com.xzj.basic.base.dto;

import com.xzj.basic.base.enums.BaseEnum;
import lombok.Data;

/**
 * @Category com.xzj.basic.base.dto
 * @Description 【基础统一返回对象】
 * @Author xiaj07
 * @Since 2022/11/17 14:23
 **/
@Data
public class AjaxResult {

    /**
     *  成功与否标志
     */
    private boolean success = true;

    /**
     * 返回状态码，默认200.前端需要拦截一些常见的状态码如403、404、500等
     */
    private Integer status = 200;

    /**
     * 编码，可用于前端处理多语言，不需要则不用返回编码
     */
    private String code;

    /**
     * 相关消息
     */
    private String msg;

    /**
     * 返回相关数据
     */
    private Object data;

    /**
     * 执行时长，毫秒，通过aop进行计算
     */
    private Long executionTime;


    private AjaxResult() {
    }

    public static AjaxResult successResult(){
        return new AjaxResult().putCode("200");
    }

    public static AjaxResult failResult(){
        AjaxResult ajaxResult = new AjaxResult().putCode("400");
        ajaxResult.setSuccess(false);
        return ajaxResult;
    }

    public AjaxResult putStatus(Integer status){
        this.status = status;
        return this;
    }

    public AjaxResult putCode(String code){
        this.code = code;
        return this;
    }

    public AjaxResult putMsg(String msg){
        this.msg = msg;
        return this;
    }

    public AjaxResult putEnum(BaseEnum baseEnum){
        this.code = baseEnum.getCode();
        this.msg = baseEnum.getDesc();
        return this;
    }

    public AjaxResult putData(Object data){
        this.data = data;
        return this;
    }

    public AjaxResult putExecutionTime(long executionTime){
        this.executionTime = executionTime;
        return this;
    }

}
