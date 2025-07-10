package com.huabing.monitor.mapper;

import com.huabing.monitor.mbg.model.Sendstatus;

/**
 * @author ping
 * @classname SendFileStatusCustomMapper
 * @description TODO
 * @date 2019/12/27 23:31
 */
public interface SendFileStatusCustomMapper {

    public int updateByStationIdBoardId(Sendstatus sendstatus);
}
