package com.huabing.monitor.netty.handler;

import com.alibaba.fastjson.JSON;
import com.huabing.monitor.common.utils.ByteUtils;
import com.huabing.monitor.common.utils.TimeUtils;
import com.huabing.monitor.component.FileThreadPoolComponent;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.*;
import com.huabing.monitor.netty.dto.BootloadTask;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.enums.BootloaderStatusEnum;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.enums.MsgType;
import com.huabing.monitor.manager.BootloaderTaskManage;
import com.huabing.monitor.netty.manage.ClientBinFileManage;
import com.huabing.monitor.netty.manage.ClientManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author ping
 * @classname LongLinkHandler
 * @description TODO
 * @date 2019/7/3 10:14
 * @func 完成业务逻辑
 */
@Component
@ChannelHandler.Sharable
@Scope("singleton")
public class LongLinkHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LongLinkHandler.class);

    private final AttributeKey<Integer> mecheineIdKey = AttributeKey.valueOf("mecheineId");

    @Autowired private ClientManager clientManager;
    @Autowired private ClientBinFileManage clientBinFileManage;
    @Autowired private FileThreadPoolComponent fileThreadPoolComponent;
    @Autowired private BootloaderTaskManage bootloaderTaskManage;
    // kylinz added.use mq to tell website the statue of bootloader
    @Autowired private RabbitTemplate rabbitTemplate;

    @Value("${zookeeper.client.bootload.filePath}")
    private String bootloadFilePathPre;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIP = insocket.getAddress().getHostAddress();
        LOGGER.info("client online clientIP={}", clientIP);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("client offline clientIP={{}", ctx.channel().remoteAddress().toString());
        Attribute<Integer> attribute = ctx.channel().attr(mecheineIdKey);
        if (attribute.get() != null) {
            clientManager.removeClient(attribute.get());
        }
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BaseMsg baseMsg = (BaseMsg) msg;
        String nodeIndex = ctx.channel().remoteAddress().toString();
        LOGGER.info("nodeIndex={}，msg={}", nodeIndex, msg);
        boolean flag = false;
        int mecheineId = 0;
        switch (baseMsg.getType()) {
            case UPDATE:  // 同步
                LOGGER.info("收到同步包");
                flag = true;
                UpdateMsg updateMsg = (UpdateMsg) baseMsg;
                mecheineId = updateMsg.getDeviceId();
                ctx.channel().writeAndFlush("BD1");
                break;
            case BOOTLOADSHO0KHANDS:  // 发送bin文件----->网站更新模式
                BootloadShookhand bootloadShookhand = (BootloadShookhand) baseMsg;
                mecheineId = bootloadShookhand.getDeviceId();
                LOGGER.info(
                        "passive update request from："
                                + mecheineId
                                + "hexid:"
                                + Integer.toHexString(mecheineId));
                if (!bootloaderTaskManage.hasTask(mecheineId)) {
                    // 任务没有生成
                    bootloaderTaskManage.addTask(mecheineId, new BootloadTask(mecheineId));
                    bootloaderTaskManage.setBootloaderRequest(
                            mecheineId,
                            new BootloaderRequest(mecheineId, TimeUtils.getCurrentTimeStamp10()));
                }
                bootloaderTaskManage.setBoardReady(mecheineId);
                // 执行任务
                bootloaderTaskManage.process(mecheineId);
                break;
            case BOOTLOADER_INIT:  // 空板子更新---->bin文件下发
                BootloadShookhandInit bootloadShookhandInit = (BootloadShookhandInit) baseMsg;
                mecheineId = bootloadShookhandInit.getDeviceId();
                LOGGER.info(
                        "forward update request from："
                                + mecheineId
                                + "hexid:"
                                + Integer.toHexString(mecheineId));
                String path = clientBinFileManage.removeBinFile(mecheineId);
                if (!bootloaderTaskManage.hasTask(mecheineId)) {
                    // 任务没有生成
                    bootloaderTaskManage.addTask(mecheineId, new BootloadTask(mecheineId));
                    bootloaderTaskManage.setBootloaderRequest(
                            mecheineId,
                            new BootloaderRequest(
                                    mecheineId, path, TimeUtils.getCurrentTimeStamp10(), true));
                }
                bootloaderTaskManage.setBoardReady(mecheineId);
                // 执行任务
                bootloaderTaskManage.process(mecheineId);
                // Todo: 下发默认的bin文件
                break;
            case BOOTLOADSTATUS:  // bootloader状态回复
                BootloadStatus bootloadStatus = (BootloadStatus) baseMsg;
                mecheineId = bootloadStatus.getDeviceId();
                LOGGER.info("get update response：" + JSON.toJSON(bootloadStatus));
                BootloaderStatusEnum statusEnum =
                        bootloadStatus.getStatus() == (byte) 1
                                ? BootloaderStatusEnum.SUCCESS
                                : BootloaderStatusEnum.FAILED;
                bootloaderTaskManage.setBootloaderResponseStatus(mecheineId, statusEnum);
                bootloaderTaskManage.sendBootloaderResponse(mecheineId);
                // flag = true;
                // 测试时关闭
                // mqThreadPoolComponent.addTask(baseMsg);
                break;
            default:
                break;
        }
        if (flag) {
            clientManager.addClient(mecheineId, ctx);
            ctx.channel().attr(mecheineIdKey).set((int) mecheineId);
        }
        ctx.channel().flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("client offline ", cause);
        Attribute<Integer> attribute = ctx.channel().attr(mecheineIdKey);
        BootloadStatus bootloadStatus = new BootloadStatus();
        bootloadStatus.setBoardId(ByteUtils.getBytes(attribute.get())[1]);
        bootloadStatus.setStationId(ByteUtils.getBytes(attribute.get())[0]);
        bootloadStatus.setStatus((byte) 0);
        bootloadStatus.setType(MsgType.BOOTLOADSTATUS);
        bootloadStatus.setLength((byte) 8);
        rabbitTemplate.convertAndSend("bootloaderExchange", "", JSON.toJSONString(bootloadStatus));
        LOGGER.info("更新失败！" + "------->" + bootloadStatus);
        if (attribute.get() != null) {
            clientManager.removeClient(attribute.get());
        }
        ctx.close();
    }
}
