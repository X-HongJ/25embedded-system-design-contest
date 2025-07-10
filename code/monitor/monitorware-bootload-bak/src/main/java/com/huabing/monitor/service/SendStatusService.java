package com.huabing.monitor.service;

/**
 * @author ping
 * @classname SendStatusService
 * @description 更新状态服务
 * @date 2019/12/27 22:54
 */
public interface SendStatusService {

    /**
     * 插入一条发送更新的机器
     * @param stationId
     * @param boardId
     * @param status
     * @return
     */
    public int insertSendStatus(Integer stationId, Integer boardId, boolean status);

    /**
     * 更新发送成功状态
     * @param stationId
     * @param boardId
     * @param status
     * @return
     */
    public int updateSendStatus(Integer stationId, Integer boardId, boolean status);

}
