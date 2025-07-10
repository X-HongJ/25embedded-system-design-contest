package com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo;

import com.huabing.monitor.common.utils.ByteUtils;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.enums.MsgType;

/**
 * @author ping
 * @classname BootloadStatus
 * @description boot下发状态
 * @date 2019/11/13 16:28
 */
public class BootloadStatus extends BaseMsg {

    private short start;
    private byte length;
    private byte stationId;
    private byte boardId;
    private byte status;
    private short end;
    // 使用deviceId代替原来的station和board
    private int deviceId;

    @Override
    public String toString() {
        return "BootloadStatus{" +
                "start=" + start +
                ", length=" + length +
                ", stationId=" + stationId +
                ", boardId=" + boardId +
                ", status=" + status +
                ", end=" + end +
                ", deviceId=" + deviceId +
                '}';
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public BootloadStatus(short start, byte length, byte stationId, byte boardId, byte status, short end, int deviceId) {
        super(MsgType.BOOTLOADSTATUS);
        this.start = start;
        this.length = length;
        this.stationId = stationId;
        this.boardId = boardId;
        this.status = status;
        this.end = end;
        this.deviceId = deviceId;
    }

    public BootloadStatus() {
        super(MsgType.BOOTLOADSTATUS);
    }

    public BootloadStatus(short start, byte length, byte stationId, byte boardId, byte status, short end) {
        super(MsgType.BOOTLOADSTATUS);
        this.start = start;
        this.length = length;
        this.stationId = stationId;
        this.boardId = boardId;
        this.status = status;
        this.end = end;
        this.deviceId = ByteUtils.getShort(new byte[]{stationId, boardId});
    }

    public short getStart() {
        return start;
    }

    public void setStart(short start) {
        this.start = start;
    }

    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
        this.length = length;
    }

    public byte getStationId() {
        return stationId;
    }

    public void setStationId(byte stationId) {
        this.stationId = stationId;
    }

    public byte getBoardId() {
        return boardId;
    }

    public void setBoardId(byte boardId) {
        this.boardId = boardId;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public short getEnd() {
        return end;
    }

    public void setEnd(short end) {
        this.end = end;
    }

}
