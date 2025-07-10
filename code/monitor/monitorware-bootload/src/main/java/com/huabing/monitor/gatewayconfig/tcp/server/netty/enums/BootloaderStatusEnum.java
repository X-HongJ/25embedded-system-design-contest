package com.huabing.monitor.gatewayconfig.tcp.server.netty.enums;

/**
 * @author ：kylinz
 * @description：bootloader的三种状态
 * @date ：Created in 2020/11/12 20:15
 */
public enum BootloaderStatusEnum {

    APPENDING("appending"),
    SUCCESS("success"),
    FAILED("failed");



    public String status;
    BootloaderStatusEnum(String s){
        status=s;
    }
}
