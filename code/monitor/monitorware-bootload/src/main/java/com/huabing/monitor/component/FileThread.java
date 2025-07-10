package com.huabing.monitor.component;

import com.huabing.monitor.common.utils.ByteUtils;
import com.huabing.monitor.common.utils.MathUtils;
import com.huabing.monitor.common.utils.TimeUtils;
import com.huabing.monitor.manager.ZookeeperManager;
import com.huabing.monitor.mbg.model.Ammeter;
import com.huabing.monitor.mbg.model.Binfile;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.enums.BootloaderStatusEnum;
import com.huabing.monitor.manager.BootloaderTaskManage;
import com.huabing.monitor.netty.manage.ClientManager;
import com.huabing.monitor.gatewayconfig.tcp.server.netty.pojo.BootloaderResponse;
import com.huabing.monitor.service.AmmeterService;
import com.huabing.monitor.service.BinFileService;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ping
 * @classname FileThread
 * @description 发送文件的线程
 * @date 2019/11/24 17:29
 */
@Component
@Scope("prototype")//spring多例
@EnableScheduling
public class FileThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileThread.class);

    private final ClientManager clientManager;
    private final AmmeterService ammeterService;
    private final BinFileService binFileService;
    private String path;
    private Integer mechieId;
    private BootloaderResponse response;
    @Autowired private BootloaderTaskManage taskManager;

    @Autowired private ZookeeperManager zookeeperManager;

    @Value("${bootloader.default-bin-file}")
    private String defaultBinFile;

    private List<String> bigFileDeviceList = new LinkedList<>();

    public FileThread(
            ClientManager clientManager,
            AmmeterService ammeterService,
            BinFileService binFileService) {
        this.clientManager = clientManager;
        this.ammeterService = ammeterService;
        this.binFileService = binFileService;
    }

    public BootloaderResponse getResponse() {
        return response;
    }

    public void setResponse(BootloaderResponse response) {
        this.response = response;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMechieId(Integer mechieId) {
        this.mechieId = mechieId;
    }

    /**
     * 通过mechieId在clientManager中查出ctx
     *
     * @param mechieId
     * @return
     */
    public ChannelHandlerContext findCtx(Integer mechieId) {
        return clientManager.findClient(mechieId);
    }

    @Override
    public void run() {
        /** 适应空板子主动请求更新的情况 */
        // 若文件路径为空，说明为板子主动更新，好像没有用了
        if (path == null) {
            byte[] bytes =
                    ByteUtils.getBytes(
                            mechieId.shortValue()); // 用于返回此Short对象表示的值，该对象转换为short类型(通过强制转换)
            Ammeter ammeter =
                    ammeterService.queryAmmeterByStationIdBoardId(
                            String.valueOf(bytes[0]), String.valueOf(bytes[1]));
            Binfile binfile = null;
            try {
                if (ammeter != null) {
                    if (ammeter.getFileId() != null) {
                        // 查询数据库中的相应文件名
                        binfile = binFileService.queryBinfileByFileId(ammeter.getFileId());
                        LOGGER.info("binfile name:" + binfile);
                    }
                }
                if (binfile == null || binfile.getPath() == null) {
                    path = defaultBinFile;
                    LOGGER.warn("no bin file found in database, use default bin:" + path);
                    // return;
                } else {
                    path = binfile.getPath();
                    LOGGER.info("binfile path is:" + path);
                }
            } catch (Exception e) {
                LOGGER.error("find file error: ", e);
                response.setStatus(BootloaderStatusEnum.FAILED);
                response.setTimestamp(TimeUtils.getCurrentTimeStamp10());
                response.setDesc("no target file in database");
                taskManager.sendBootloaderResponse(mechieId);
                return;
            }
        }
        // 根据文件路径创建文件对象
        File file = new File(path);
        if (!file.exists()) {  // 若文件不存在
            LOGGER.error("send file error: 文件不存在");
            response.setStatus(BootloaderStatusEnum.FAILED);
            response.setTimestamp(TimeUtils.getCurrentTimeStamp10());
            response.setDesc("文件不存在");
            taskManager.sendBootloaderResponse(mechieId);
            return;
        }
        // 文件存在，查找板子客户端
        ChannelHandlerContext ctx = findCtx(mechieId);
        //        doSendFile(ctx, file);
        try {
            // 开始向板子发送文件
            LOGGER.info("start sending to mechieId={} while bin file is {}：===》》》", mechieId, path);
            // kylinz comment it
            // doSendFileByZeroCopy(ctx, file);

            // 判断是用什么
            bigFileDeviceList = zookeeperManager.getBigFileDeviceList();
            LOGGER.info("bigFileDeviceList is ={}", bigFileDeviceList);
            boolean isBigFile = false;
            for (String id : bigFileDeviceList) {
                String perfix = id.toLowerCase();  // 将ID转为小写
                String machineId = MathUtils.SignDectoHex(mechieId);  // 十进制转十六进制
                LOGGER.info("perfix = {},machineId={}", perfix, machineId);
                if (machineId.startsWith(perfix)) {
                    isBigFile = true;  // 文件类型校验成功
                }
            }
            if (isBigFile) {  // 如果是大文件
                LOGGER.info("进入了大文件的发送");
                doBigSendFile(ctx, file);
            } else {  // 如果是小文件
                doSendFile(ctx, file);
                LOGGER.info("进入了小文件的发送");
            }

            // response.setStatus(BootloaderStatusEnum.SUCCESS);
            response.setTimestamp(TimeUtils.getCurrentTimeStamp10());
        } catch (Exception e) {
            LOGGER.error("send file error: ", e);
            response.setStatus(BootloaderStatusEnum.FAILED);
            response.setTimestamp(TimeUtils.getCurrentTimeStamp10());
            response.setDesc(e.toString());
            taskManager.sendBootloaderResponse(mechieId);
        }
    }

    /**
     * 打开文件进行拷贝,最大文件大小64K
     *
     * @param ctx
     * @param file
     */
    private void doSendFile(ChannelHandlerContext ctx, File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] buf = new byte[1024];
            short flen = (short) file.length();
            byte[] flenbytes = new byte[2];
            // 小端对齐
            flenbytes[0] = (byte) ((flen & 0xff00) >> 8);
            flenbytes[1] = (byte) (flen & 0xff);
            ctx.channel().writeAndFlush(Unpooled.copiedBuffer(flenbytes));
            int i = 0;
            while ((i = in.read(buf, 0, buf.length)) >= 0) {
                ctx.channel().writeAndFlush(Unpooled.copiedBuffer(buf, 0, i));
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("send binFile error cause File:{} not found", file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("send binFile error cause {}", e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                LOGGER.error("close binFile error cause {}", e.getMessage());
            }
            LOGGER.info("send file={} ok", path);
        }
    }

    private void doBigSendFile(ChannelHandlerContext ctx, File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int flen = (int) file.length();
            byte[] flenbytes = new byte[4];
            // 小端对齐
            flenbytes[0] = (byte) ((flen & 0xff000000) >> 24);
            flenbytes[1] = (byte) ((flen & 0xff0000) >> 16);
            flenbytes[2] = (byte) ((flen & 0xff00) >> 8);
            flenbytes[3] = (byte) (flen & 0xff);

            ctx.channel().writeAndFlush(Unpooled.copiedBuffer(flenbytes));
            int i = 0;
            while ((i = in.read(buf, 0, buf.length)) >= 0) {
                ctx.channel().writeAndFlush(Unpooled.copiedBuffer(buf, 0, i));
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("send binFile error cause File:{} not found", file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("send binFile error cause {}", e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                LOGGER.error("close binFile error cause {}", e.getMessage());
            }
            LOGGER.info("send file={} ok", path);
        }
    }

    /**
     * 零拷贝方式实现文件发送
     *
     * @param ctx
     * @param file
     */
    private void doSendFileByZeroCopy(ChannelHandlerContext ctx, File file) throws IOException {
        RandomAccessFile in = null;
        short flen = -1;
        try {
            in = new RandomAccessFile(file, "r");
            flen = (short) file.length();
        } catch (FileNotFoundException e) {
            LOGGER.error("send binFile error cause File:{} not found", file.getAbsolutePath());
            return;
        } finally {
            try {
                if (flen < 0 && in != null) {
                    in.close();
                }
                LOGGER.info("find file: {}", path);
            } catch (IOException e) {
                LOGGER.error("close binFile error cause {}", e.getMessage());
            }
        }
        byte[] flenbytes = new byte[2];
        // 小端对齐
        flenbytes[0] = (byte) ((flen & 0xff00) >> 8);
        flenbytes[1] = (byte) (flen & 0xff);
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer(flenbytes));
        if (ctx.pipeline().get(SslHandler.class) == null) {
            ctx.channel().write(new DefaultFileRegion(in.getChannel(), 0, flen));
        } else {
            ctx.channel().write(new ChunkedFile(in));
        }
        ctx.channel().flush();
    }
}
