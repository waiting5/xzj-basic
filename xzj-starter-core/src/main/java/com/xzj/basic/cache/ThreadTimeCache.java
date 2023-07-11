package com.xzj.basic.cache;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.xzj.basic.base.dto.AjaxResult;
import lombok.extern.slf4j.Slf4j;

/**
 * 线程执行时间缓存
 *
 * @author xiazunjun
 * @date 2023/7/3 14:43
 */
@Slf4j
public class ThreadTimeCache {

    // 定义静态线程开始时间记录
    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();

    /**
     *
     * 线程执行开始时间设置
     * @return
     * @author xiazunjun
     * @date 2023/7/3 14:49
     */
    public static void putStartTime(){
        START_TIME.set(DateUtil.current());
    }

    /**
     *
     * 根据开始时间计算线程执行时长
     * @Param result
     *
     * @return
     * @author xiazunjun
     * @date 2023/7/3 14:47
     */
    public static void countTime(AjaxResult result){
        if(ObjectUtil.isNotEmpty(START_TIME.get())){
            result.setExecutionTime(DateUtil.current()-START_TIME.get());
            START_TIME.remove();
        }
    }

}
