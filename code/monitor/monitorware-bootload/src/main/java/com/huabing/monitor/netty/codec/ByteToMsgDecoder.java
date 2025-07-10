package com.huabing.monitor.netty.codec;

import com.huabing.monitor.common.utils.ByteUtils;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author ping
 * @classname ByteToMsgDecoder
 * @description 字节流解析
 * @date 2019/7/1 18:00
 */
public class ByteToMsgDecoder extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ByteToMsgDecoder.class);

    private static final short SHOOKHANDS =
            ByteUtils.getShort(new byte[] {(byte) 0xAA, (byte) 0xDD});
    // 板子被动更新起始包
    private static final short BOOTLOADSHO0KHANDS =
            ByteUtils.getShort(new byte[] {(byte) 0xBC, (byte) 0xDD});
    private static final short BOOTLOADSTATUS =
            ByteUtils.getShort(new byte[] {(byte) 0xCC, (byte) 0xDD});
    // 空板子初始化起始包
    private static final short BOOTLOADINIT =
            ByteUtils.getShort(new byte[] {(byte) 0xBB, (byte) 0xDD});
    private static final short UPDATE = ByteUtils.getShort(new byte[] {(byte) 0x99, (byte) 0xDD});

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out)
            throws Exception {
        in.markReaderIndex();
        byte b1 = in.readByte();
        byte b2 = in.readByte();
        short start = ByteUtils.getShort(new byte[] {b1, b2});
        byte length = in.readByte();
        if (length <= 3) {
            in.clear();
            return;
        }
        if (in.readableBytes() >= (length - 3)) {
            byte[] info = new byte[length - 3];
            in.readBytes(info);
            BaseMsg o = createMsg(start, length, info);
            if (o != null) {
                out.add(o);
                LOGGER.debug("createMsg return " + o);
            } else {
                LOGGER.debug("createMsg return null");
            }
        }
    }

    /**
     * 只有BOOTLOADSHO0KHANDS和BOOTLOADSTATUS有效
     *
     * @param start
     * @param length
     * @param info
     * @return
     */
    private BaseMsg createMsg(short start, byte length, byte[] info) {
        BaseMsg baseMsg = null;
        // 切换工厂模式+策略模式
        if (start == SHOOKHANDS) {
            baseMsg = createShookHands(length, info);
        } else if (start == BOOTLOADSHO0KHANDS) {
            baseMsg = createBootloadShookHands(length, info);
        } else if (start == BOOTLOADSTATUS) {
            baseMsg = createBootloadStatus(length, info);
        } else if (start == UPDATE) {
            baseMsg = createUpdate(length, info);
        } else if (start == BOOTLOADINIT) {
            baseMsg = createBootloadShookInit(length, info);
        }
        return baseMsg;
    }

    private BaseMsg createUpdate(byte length, byte[] info) {
        UpdateMsg msg;
        if (length <= 7) {
            byte stationId = info[0];
            byte boardId = info[1];
            short end = ByteUtils.getShort(new byte[] {info[2], info[3]});
            msg = new UpdateMsg(UPDATE, length, stationId, boardId, end);
        } else {
            int deviceId = ByteUtils.getInt(new byte[] {info[0], info[1], info[2], info[3]});
            short end = ByteUtils.getShort(new byte[] {info[4], info[5]});
            msg = new UpdateMsg(UPDATE, length, (byte) 0, (byte) 0, end, deviceId);
        }
        return msg;
    }

    private BaseMsg createBootloadStatus(byte length, byte[] info) {
        BootloadStatus bootloadStatus;
        if (length <= 8) {
            byte stationId = info[0];
            byte boardId = info[1];
            byte status = info[2];
            short end = ByteUtils.getShort(new byte[] {info[3], info[4]});
            bootloadStatus =
                    new BootloadStatus(BOOTLOADSTATUS, length, stationId, boardId, status, end);
        } else {
            int deviceId = ByteUtils.getInt(new byte[] {info[0], info[1], info[2], info[3]});
            byte status = info[4];
            short end = ByteUtils.getShort(new byte[] {info[5], info[6]});
            bootloadStatus =
                    new BootloadStatus(
                            BOOTLOADSTATUS, length, (byte) 0, (byte) 0, status, end, deviceId);
        }
        return bootloadStatus;
    }

    private BaseMsg createBootloadShookHands(byte length, byte[] info) {
        BootloadShookhand bootloadShookhand;
        if (length <= 7) {
            byte stationId = info[0];
            byte boardId = info[1];
            short end = ByteUtils.getShort(new byte[] {info[2], info[3]});
            bootloadShookhand =
                    new BootloadShookhand(BOOTLOADSHO0KHANDS, length, stationId, boardId, end);
        } else {
            int deviceId = ByteUtils.getInt(new byte[] {info[0], info[1], info[2], info[3]});
            short end = ByteUtils.getShort(new byte[] {info[4], info[5]});
            bootloadShookhand =
                    new BootloadShookhand(
                            BOOTLOADSHO0KHANDS, length, (byte) 0, (byte) 0, end, deviceId);
        }
        return bootloadShookhand;
    }

    private BaseMsg createShookHands(byte length, byte[] info) {
        ShookHandsMsg shookHandsMsg;
        if (length <= 7) {
            byte stationId = info[0];
            byte borderId = info[1];
            short end = ByteUtils.getShort(new byte[] {info[2], info[3]});
            shookHandsMsg = new ShookHandsMsg(SHOOKHANDS, length, stationId, borderId, end);
        } else {
            int deviceId = ByteUtils.getInt(new byte[] {info[0], info[1], info[2], info[3]});
            short end = ByteUtils.getShort(new byte[] {info[4], info[5]});
            shookHandsMsg =
                    new ShookHandsMsg(SHOOKHANDS, length, (byte) 0, (byte) 0, end, deviceId);
        }
        return shookHandsMsg;
    }

    private BaseMsg createBootloadShookInit(byte length, byte[] info) {
        BootloadShookhandInit bootloadShookhandInit;
        if (length <= 7) {
            byte stationId = info[0];
            byte boardId = info[1];
            short end = ByteUtils.getShort(new byte[] {info[2], info[3]});
            bootloadShookhandInit =
                    new BootloadShookhandInit(BOOTLOADINIT, length, stationId, boardId, end);
        } else {
            int deviceId = ByteUtils.getInt(new byte[] {info[0], info[1], info[2], info[3]});
            short end = ByteUtils.getShort(new byte[] {info[4], info[5]});
            bootloadShookhandInit =
                    new BootloadShookhandInit(
                            BOOTLOADINIT, length, (byte) 0, (byte) 0, end, deviceId);
        }
        return bootloadShookhandInit;
    }
}
