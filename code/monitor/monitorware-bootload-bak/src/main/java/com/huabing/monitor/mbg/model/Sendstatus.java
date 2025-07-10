package com.huabing.monitor.mbg.model;

import java.io.Serializable;
import java.util.Date;

public class Sendstatus implements Serializable {
    private Integer id;

    private Date sendTime;

    private Boolean status;

    private String ammeterAppCode;

    private String ammeterDistination;

    private Date replyTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAmmeterAppCode() {
        return ammeterAppCode;
    }

    public void setAmmeterAppCode(String ammeterAppCode) {
        this.ammeterAppCode = ammeterAppCode;
    }

    public String getAmmeterDistination() {
        return ammeterDistination;
    }

    public void setAmmeterDistination(String ammeterDistination) {
        this.ammeterDistination = ammeterDistination;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", status=").append(status);
        sb.append(", ammeterAppCode=").append(ammeterAppCode);
        sb.append(", ammeterDistination=").append(ammeterDistination);
        sb.append(", replyTime=").append(replyTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}