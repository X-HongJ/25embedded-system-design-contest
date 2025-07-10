package com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo;

import com.huabing.monitor.common.utils.ByteUtils;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.enums.MsgType;

/**
 * @author ping
 * @classname ShookHandsMsg
 * @description TODO
 * @date 2019/9/16 22:30
 */
public class ShookHandsMsg extends BaseMsg {

    private short start;
    private byte length;
    private byte stationId;
    private byte boardId;
    private short end;

    // 使用deviceId代替原来的station和board
    private int deviceId;

    public ShookHandsMsg() {
        super(MsgType.SHOOKHANDS);
    }

    public ShookHandsMsg(short start, byte length, byte stationId, byte boardId, short end) {
        super(MsgType.SHOOKHANDS);
        this.start = start;
        this.length = length;
        this.stationId = stationId;
        this.boardId = boardId;
        this.end = end;
        this.deviceId = ByteUtils.getShort(new byte[]{stationId, boardId});
    }

    public ShookHandsMsg(short start, byte length, byte stationId, byte boardId, short end, int deviceId) {
        super(MsgType.SHOOKHANDS);
        this.start = start;
        this.length = length;
        this.stationId = stationId;
        this.boardId = boardId;
        this.end = end;
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

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "ShookHandsMsg{" +
                "start=" + start +
                ", length=" + length +
                ", stationId=" + stationId +
                ", boardId=" + boardId +
                ", end=" + end +
                ", deviceId=" + deviceId +
                '}';
    }
}
