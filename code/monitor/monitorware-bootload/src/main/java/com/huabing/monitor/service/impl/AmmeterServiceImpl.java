package com.huabing.monitor.service.impl;

import com.huabing.monitor.mbg.mapper.AmmeterMapper;
import com.huabing.monitor.mbg.model.Ammeter;
import com.huabing.monitor.mbg.model.AmmeterExample;
import com.huabing.monitor.service.AmmeterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ping
 * @classname AmmeterServiceImpl
 * @description TODO
 * @date 2019/11/27 14:07
 */
@Service
public class AmmeterServiceImpl implements AmmeterService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AmmeterServiceImpl.class);

    @Autowired
    AmmeterMapper ammeterMapper;

    @Override
    public Ammeter queryAmmeterByStationIdBoardId(String stationId, String boardId) {
        AmmeterExample example = new AmmeterExample();
        example.createCriteria().andAmmeterDistinationEqualTo(stationId).andAmmeterAppCodeEqualTo(boardId);
        List<Ammeter> ammeters = ammeterMapper.selectByExample(example);
        if (ammeters != null && ammeters.size() == 1) {
            return ammeters.get(0);
        } else {
            if (ammeters!=null){
                LOGGER.error("ammeters.size()={}",ammeters.size());
            }else {
                LOGGER.error("ammeters=null");
            }
            return null;
        }
    }
}
