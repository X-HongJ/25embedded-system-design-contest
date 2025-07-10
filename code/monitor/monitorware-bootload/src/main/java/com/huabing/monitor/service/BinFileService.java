package com.huabing.monitor.service;

import com.huabing.monitor.mbg.model.Binfile;

/**
 * @author ping
 * @classname BinFileService
 * @description 获取binfile文件服务
 * @date 2019/12/27 22:51
 */
public interface BinFileService {

    /**
     * 通过fileid查询file文件
     * @param fileId
     * @return
     */
    public Binfile queryBinfileByFileId(int fileId);

}
