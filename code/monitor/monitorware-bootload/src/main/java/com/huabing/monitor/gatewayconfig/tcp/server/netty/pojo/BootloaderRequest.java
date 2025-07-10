package com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo;

import com.huabing.monitor.gatewayconfig.tcp.server.netty.enums.MsgType;

/**
 * @author ：kylinz
 * @description：bootloader更新必要数据包
 * @date ：Created in 2020/11/12 20:20
 */
public class BootloaderRequest extends BaseMsg{

    private int deviceId;
    private String filePath;
    private boolean requestReady;
    private long timestamp;

    public BootloaderRequest(int deviceId, String filePath, long timestamp) {
        super.setType(MsgType.BOOTLOADER_REQUEST);
        this.deviceId = deviceId;
        this.filePath = filePath;
        this.timestamp = timestamp;
    }

    public BootloaderRequest(int deviceId, String filePath, long timestamp, boolean ready) {
        super.setType(MsgType.BOOTLOADER_REQUEST);
        this.deviceId = deviceId;
        this.filePath = filePath;
        this.timestamp = timestamp;
        this.requestReady = ready;
    }

    public BootloaderRequest(int deviceId, long timestamp) {
        super.setType(MsgType.BOOTLOADER_REQUEST);
        this.deviceId = deviceId;
        this.timestamp = timestamp;
    }

    public BootloaderRequest() {
        super.setType(MsgType.BOOTLOADER_REQUEST);
    }

    public boolean isRequestReady() {
        return requestReady;
    }

    public void setRequestReady(boolean requestReady) {
        this.requestReady = requestReady;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "BootloaderRequest{" +
                "deviceId=" + deviceId +
                ", filePath='" + filePath + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
