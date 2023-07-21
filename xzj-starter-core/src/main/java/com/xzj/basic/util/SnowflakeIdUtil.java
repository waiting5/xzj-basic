package com.xzj.basic.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 雪花算法id生成器
 *
 * @author xiazunjun
 * @date 2023/7/21 10:33
 */
@Slf4j
public class SnowflakeIdUtil {

    private static long workerId = 0;  //第几号机房
    private static Snowflake snowflake;

    //构造后开始执行，加载初始化工作
    static {
        try{
            //获取本机的ip地址编码
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            workerId = workerId >> 16 & 31;
            log.info("当前机器的workerId: " + workerId);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("当前机器的workerId获取失败 ----> " + e);
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
        snowflake = IdUtil.getSnowflake(workerId);
    }

    public static synchronized long snowflakeId(){
        return snowflake.nextId();
    }

    public static synchronized String snowflakeIdStr(){
        return snowflake.nextId()+"";
    }

}
