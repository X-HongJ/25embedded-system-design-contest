package com.huabing.monitor.common.utils;

/**
 * @author ping
 * @classname SystemUtils
 * @description TODO
 * @date 2019/10/24 15:46
 */
public class SystemUtils {

    public static String getOsName(){
        return System.getProperties().getProperty("os.name");
    }

    public static boolean isWindows(){
        return getOsName().startsWith("Windows");
    }

}
