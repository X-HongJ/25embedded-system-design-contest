//package com.huabing.monitor.com.huabing.monitor.component;
//
//
//import com.huabing.monitor.common.com.huabing.monitor.common.utils.ByteUtils;
//import com.huabing.monitor.netty.manage.ClientBinFileManage;
//import com.huabing.monitor.netty.manage.ClientManager;
//import org.apache.zookeeper.KeeperException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * @author ping
// * @classname OrderThread
// * @description 监控bootload文件路径线程池
// * @date 2019/11/24 16:54
// */
//@Component
//@Scope("prototype")//spring多例
//public class BootloadFliePathThread implements Runnable {
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(BootloadFliePathThread.class);
//
//    @Autowired
//    private ClientManager clientManager;
//    @Autowired
//    private ClientBinFileManage clientBinFileManage;
//
//    private String path;
//    private List<String> list;
//
//    public BootloadFliePathThread() {
//    }
//
//    public void setList(List<String> list) {
//        this.list = list;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    @Override
//    public void run() {
//        Iterator<String> s = list.iterator();
//        String mechiId = null;
//        String filePath = null;
//        String dataPath = null;
//        while (s.hasNext()) {
//            try {
//                mechiId = s.next();
//                dataPath = path + "/" + mechiId;
//                filePath = serviceRegister.getData(dataPath);
//                LOGGER.info("mechiId：{},filePath：{} ", mechiId, filePath);
//                String[] mech = mechiId.split("-");
//                byte stationId = Byte.valueOf(mech[0]);
//                byte boardId = Byte.valueOf(mech[1]);
//                File file = new File(filePath);
//                String[] strings = filePath.split("\\.");
//                if ("bin".equals(strings[strings.length - 1]) && file.isFile() && file.canRead()) {
//                    clientBinFileManage.addBinFile(
//                            (int) ByteUtils.getShort(new byte[]{stationId, boardId}), filePath);
//                }
////                else {
////                    LOGGER.info("\"bin\".equals(strings[strings.length - 1])={},file.isFile()={},file.canRead()={}",
////                            "bin".equals(strings[strings.length - 1]), file.isFile(), file.canRead());
////                    LOGGER.info("file.canRead()={}", file.canRead());
////                }
//            } catch (KeeperException e) {
//                LOGGER.error("WatchOrder com.huabing.monitor.handler error: ", e);
//            } catch (InterruptedException e) {
//                LOGGER.error("WatchOrder com.huabing.monitor.handler error: ", e);
//            }
//        }
//    }
//}
