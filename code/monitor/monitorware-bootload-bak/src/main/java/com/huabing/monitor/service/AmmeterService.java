package com.huabing.monitor.service;

import com.huabing.monitor.mbg.model.Ammeter;

/**
 * @author ping
 * @classname AmmeterService
 * @description ammeter服务接口
 * @date 2019/11/27 14:04
 */
public interface AmmeterService {

    public Ammeter queryAmmeterByStationIdBoardId(String stationId, String boardId);

}
