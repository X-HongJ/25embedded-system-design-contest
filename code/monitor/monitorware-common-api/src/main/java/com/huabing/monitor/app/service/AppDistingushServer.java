package com.huabing.monitor.app.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author ping
 * @classname AppDistingushServer
 * @description 用电器识别
 * @date 2019/9/17 16:29
 */
public interface AppDistingushServer {

    /**
     * 识别app包中的用电器
     *
     * @param appMsg
     * @return
     */
    public JSONObject handleApp(JSONObject appMsg);

    /** 对逻辑移除的电器识别包在appinline中进行清除操作 */
    public boolean removeAppInline(JSONObject jsonObject);
}
