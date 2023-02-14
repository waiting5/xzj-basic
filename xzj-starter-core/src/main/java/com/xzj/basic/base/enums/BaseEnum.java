package com.xzj.basic.base.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Category com.xzj.basic.base.enums
 * @Description 【功能描述】
 * @Author xiaj07
 * @Since 2022/11/17 14:56
 **/
public enum BaseEnum {
    /**
     * 正常基础枚举 start
     */
    SUCCESS("request.success", "请求成功"),
    FAILURE("request.failure", "请求失败"),
    OPERATION_SUCCESS("operation.success", "操作成功"),
    OPERATION_FAILURE("operation.failure", "操作失败"),
    ERROR("system.error", "系统异常"),
    VERSION_NOT_MATCH("record_not_exists_or_version_not_match", "记录版本不存在或不匹配"),
    PARAMETER_NOT_NULL("parameter_not_be_null", "参数不能为空"),
    /**
     * 正常基础枚举 end
     */
    /**
     * 异常枚举 start
     */
    //自定义系列
    USER_NAME_IS_NOT_NULL("10001","【参数校验】用户名不能为空"),
    PWD_IS_NOT_NULL("10002","【参数校验】密码不能为空"),
    //400系列
    BAD_REQUEST("400","请求的数据格式不符!"),
    UNAUTHORIZED("401","登录凭证过期!"),
    FORBIDDEN("403","抱歉，你无权限访问!"),
    NOT_FOUND("404", "请求的资源找不到!"),
    //500系列
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVICE_UNAVAILABLE("503","服务器正忙，请稍后再试!"),
    //未知异常
    UNKNOWN("10000","未知异常!")
    /**
     * 异常枚举 end
     */

    ;

    private String code;

    private String desc;

    private static Map<String, String> allMap = new HashMap<>();

    static {
        for(BaseEnum enums : BaseEnum.values()){
            allMap.put(enums.code, enums.desc);
        }
    }

    BaseEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDesc(String code){
        return allMap.get(code);
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
