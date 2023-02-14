package com.xzj.mysql.config;

import com.xzj.mysql.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * @Category com.xzj.mysql.config
 * @Description 【数据源切换处理】
 * @Author xiaj07
 * @Since 2022/11/21 8:46
 **/
@Slf4j
public class DynamicDataSourceHolder {
    /**
     * 保存动态数据源名称
     */
    private static final ThreadLocal<String> DYNAMIC_DATASOURCE_KEY = new ThreadLocal<>();

    /**
     * 设置/切换数据源，决定当前线程使用哪个数据源
     */
    public static void setDynamicDataSourceKey(String key){
        log.info("数据源切换为：{}",key);
        DYNAMIC_DATASOURCE_KEY.set(key);
    }

    /**
     * 获取动态数据源名称，默认使用mater数据源
     */
    public static String getDynamicDataSourceKey(){
        String key = DYNAMIC_DATASOURCE_KEY.get();
        return key == null ? CommonConstant.MASTER : key;
    }

    /**
     * 移除当前数据源
     */
    public static void removeDynamicDataSourceKey(){
        log.info("移除数据源：{}",DYNAMIC_DATASOURCE_KEY.get());
        DYNAMIC_DATASOURCE_KEY.remove();
    }

}
