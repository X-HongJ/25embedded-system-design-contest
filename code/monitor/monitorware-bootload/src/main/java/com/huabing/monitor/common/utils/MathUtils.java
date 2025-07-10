package com.huabing.monitor.common.utils;

public class MathUtils {
    /**
     * 16进制字符串转有符号10进制数字
     * @param src
     * @return
     */
    public static Integer SignHextoDec(String src) {
        int width = 32;
        long dec_temp = Long.parseLong(src, 16);
        if (dec_temp > Math.pow(2,width-1) -1 ) {
            dec_temp = (long) (Math.pow(2,width) - dec_temp);
            return Math.toIntExact(-dec_temp);
        } else {
            return Math.toIntExact(dec_temp);
        }
    }

    /**
     * 有符号十进制数转16进制字符串
     * @param dec
     * @return
     */
    public static String SignDectoHex(Integer dec) {
        return Integer.toHexString(dec);
    }

}
