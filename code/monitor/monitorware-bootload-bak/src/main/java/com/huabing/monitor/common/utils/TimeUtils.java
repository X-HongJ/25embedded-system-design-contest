package com.huabing.monitor.common.utils;

import java.util.Date;

/**
 * @author ping
 * @classname TimeUtils
 * @description TODO
 * @date 2019/9/17 10:33
 */
public class TimeUtils {

    /**
     * 获取10位的时间戳
     */
    public static Long getCurrentTimeStamp10(){
        return getCurrentTimeStamp13()/1000;
    }

    /**
     * 获取13位的时间戳
     */
    public static Long getCurrentTimeStamp13(){
        return System.currentTimeMillis();
    }


    public static Date getCurrentDate(){
        return new Date();
    }


}
