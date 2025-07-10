package com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo;

import com.huabing.monitor.common.utils.ByteUtils;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.enums.MsgType;

/**
 * @author kylinz
 * @description 空程序的板子初始化
 * @date 2020/11/12 22:53
 */
public class BootloadShookhandInit extends BaseMsg{
    private short start;
    private byte length;
    private byte stationId;
    private byte boardId;
    private short end;
    // 使用deviceId代替原来的station和board
    private int deviceId;

    public BootloadShookhandInit() {
        super(MsgType.BOOTLOADER_INIT);
    }

    public BootloadShookhandInit(short start, byte length, byte stationId, byte boardId, short end) {
        super(MsgType.BOOTLOADER_INIT);
        this.start = start;
        this.length = length;
        this.stationId = stationId;
        this.boardId = boardId;
        this.end = end;
        this.deviceId = ByteUtils.getShort(new byte[]{stationId, boardId});
    }

    public BootloadShookhandInit(short start, byte length, byte stationId, byte boardId, short end, int deviceId) {
        super(MsgType.BOOTLOADER_INIT);
        this.start = start;
        this.length = length;
        this.stationId = stationId;
        this.boardId = boardId;
        this.end = end;
        this.deviceId = deviceId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
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

    public short getEnd() {
        return end;
    }

    public void setEnd(short end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "BootloadShookhandInit{" +
                "start=" + start +
                ", length=" + length +
                ", stationId=" + stationId +
                ", boardId=" + boardId +
                ", end=" + end +
                ", deviceId=" + deviceId +
                '}';
    }
}
