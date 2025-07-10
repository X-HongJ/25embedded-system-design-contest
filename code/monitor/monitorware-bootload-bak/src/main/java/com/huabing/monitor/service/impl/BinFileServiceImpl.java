package com.huabing.monitor.service.impl;


import com.huabing.monitor.mbg.mapper.BinfileMapper;
import com.huabing.monitor.mbg.model.Binfile;
import com.huabing.monitor.service.BinFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ping
 * @classname BinFileServiceImpl
 * @description 获取BinFile文件服务
 * @date 2019/12/27 22:52
 */
@Service
public class BinFileServiceImpl implements BinFileService {

    @Autowired
    BinfileMapper binfileMapper;

    @Override
    public Binfile queryBinfileByFileId(int fileId) {
        return binfileMapper.selectByPrimaryKey(fileId);
    }
}
