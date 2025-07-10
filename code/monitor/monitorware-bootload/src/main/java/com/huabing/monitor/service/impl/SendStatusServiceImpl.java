package com.huabing.monitor.service.impl;

import com.huabing.monitor.mapper.SendFileStatusCustomMapper;
import com.huabing.monitor.mbg.mapper.SendstatusMapper;
import com.huabing.monitor.mbg.model.Sendstatus;
import com.huabing.monitor.mbg.model.SendstatusExample;
import com.huabing.monitor.service.SendStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ping
 * @classname SendStatusServiceImpl
 * @description 发送状态设置服务
 * @date 2019/12/27 23:02
 */
@Service
public class SendStatusServiceImpl implements SendStatusService {

    @Autowired
    SendstatusMapper sendstatusMapper;
    @Autowired
    SendFileStatusCustomMapper sendFileStatusCustomMapper;

    @Override
    public int insertSendStatus(Integer stationId, Integer boardId, boolean status) {
        Sendstatus sendstatus = new Sendstatus();
        sendstatus.setAmmeterAppCode(boardId.toString());
        sendstatus.setAmmeterDistination(stationId.toString());
        sendstatus.setStatus(status);
        sendstatus.setSendTime(new Date());
        return sendstatusMapper.insertSelective(sendstatus);
    }

    @Override
    public int updateSendStatus(Integer stationId, Integer boardId, boolean status) {
        Sendstatus sendstatus = new Sendstatus();
        sendstatus.setStatus(status);
        sendstatus.setReplyTime(new Date());
        sendstatus.setAmmeterAppCode(boardId.toString());
        sendstatus.setAmmeterDistination(stationId.toString());
        return sendFileStatusCustomMapper.updateByStationIdBoardId(sendstatus);
    }
}
