package com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo;

import com.huabing.monitor.gatewayconfig.tcp.server.netty.enums.BootloaderStatusEnum;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.enums.MsgType;

/**
 * @author ：kylinz
 * @description：向bootloader网站发送的更新状态
 * @date ：Created in 2020/11/12 19:56
 */
public class BootloaderResponse extends BaseMsg{
    private int deviceId;
    private BootloaderStatusEnum status;
    private String desc;
    private long timestamp;
    private String deviceIdHex;

    public BootloaderResponse(int deviceId, BootloaderStatusEnum status, String desc, long timestamp) {
        super.setType(MsgType.BOOTLOADER_RESPONSE);
        this.deviceId = deviceId;
        this.status = status;
        this.desc = desc;
        this.timestamp = timestamp;
        this.deviceIdHex = Integer.toHexString(deviceId);
    }

    public BootloaderResponse(Integer deviceId) {
        super.setType(MsgType.BOOTLOADER_RESPONSE);
        this.deviceId = deviceId;
        this.deviceIdHex = Integer.toHexString(deviceId);
    }

    public BootloaderResponse(Integer deviceId,long timestamp) {
        super.setType(MsgType.BOOTLOADER_RESPONSE);
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.deviceIdHex = Integer.toHexString(deviceId);
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public BootloaderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BootloaderStatusEnum status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeviceIdHex() {
        return deviceIdHex;
    }

    public void setDeviceIdHex(String deviceIdHex) {
        this.deviceIdHex = deviceIdHex;
    }

    @Override
    public String toString() {
        return "BootloaderResponse{" +
                "deviceId=" + deviceId +
                ", status=" + status +
                ", desc='" + desc + '\'' +
                ", timestamp=" + timestamp +
                ", deviceIdHex='" + deviceIdHex + '\'' +
                '}';
    }
}
