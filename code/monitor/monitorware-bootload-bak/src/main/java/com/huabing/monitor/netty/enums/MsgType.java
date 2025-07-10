package com.huabing.monitor.gatewayconfig.tcp.server.netty.enums;

/**
 * @author ping
 * @classname MsgType
 * @description 4种用电器上报数据类型
 * @date 2019/7/16 10:40
 */
public enum MsgType {

    /** 空 **/
    NULL,
    /** 电弧包 **/
    ARC,
    /** 用电器包 **/
    APP,
    /** 环境包 **/
    ENVIRONMENT,
    /** 剩余电流包 **/
    REMAINCURRENT,
    /** 握手包 **/
    SHOOKHANDS,
    /** bootload 握手包 **/
    BOOTLOADSHO0KHANDS,
    /** bootload 下发状态包 **/
    BOOTLOADSTATUS,
    /** 更新指令 **/
    UPDATE,
    /** 网站反馈 **/
    BOOTLOADER_RESPONSE,
    /** bootloader信息包 **/
    BOOTLOADER_REQUEST,
    BOOTLOADER_INIT
}
