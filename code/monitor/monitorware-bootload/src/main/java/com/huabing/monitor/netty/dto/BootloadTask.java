package com.huabing.monitor.netty.dto;

import com.huabing.monitor.common.utils.TimeUtils;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.BootloaderRequest;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.BootloaderResponse;

/**
 * @author ：kylinz
 * @description：bootloader更新任务的封装
 * @date ：Created in 2020/11/13 10:43
 */
public class BootloadTask {
    private Integer deviceId;
    private BootloaderRequest request;
    private BootloaderResponse response;
    private boolean boardReady;
    private boolean isComplete;

    public BootloadTask(Integer deviceId) {
        this.deviceId = deviceId;
        this.response = new BootloaderResponse(deviceId, TimeUtils.getCurrentTimeStamp10());
    }

    public BootloadTask(Integer deviceId, BootloaderRequest request, boolean boardOnline) {
        this.deviceId = deviceId;
        this.request = request;
        this.boardReady = boardOnline;
        this.response = new BootloaderResponse(deviceId,TimeUtils.getCurrentTimeStamp10());
    }

    public BootloadTask(Integer deviceId, BootloaderRequest request, BootloaderResponse response, boolean boardReady) {
        this.deviceId = deviceId;
        this.request = request;
        this.response = response;
        this.boardReady = boardReady;
        this.response = new BootloaderResponse(deviceId);
    }

    public BootloaderResponse getResponse() {
        return response;
    }

    public void setResponse(BootloaderResponse response) {
        this.response = response;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public BootloaderRequest getRequest() {
        return request;
    }

    public void setRequest(BootloaderRequest request) {
        this.request = request;
    }

    public boolean isBoardReady() {
        return boardReady;
    }

    public void setBoardReady(boolean boardReady) {
        this.boardReady = boardReady;
    }

    public long getCreateTime(){
        return request.getTimestamp();
    }
}
