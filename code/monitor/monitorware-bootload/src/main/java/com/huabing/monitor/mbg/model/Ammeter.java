package com.huabing.monitor.mbg.model;

import java.io.Serializable;

public class Ammeter implements Serializable {
    private Integer ammeterId;

    private String ammeterAppCode;

    private String ammeterDistination;

    private String ammeterInfo;

    private Integer fileId;

    private static final long serialVersionUID = 1L;

    public Integer getAmmeterId() {
        return ammeterId;
    }

    public void setAmmeterId(Integer ammeterId) {
        this.ammeterId = ammeterId;
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

    public String getAmmeterInfo() {
        return ammeterInfo;
    }

    public void setAmmeterInfo(String ammeterInfo) {
        this.ammeterInfo = ammeterInfo;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ammeterId=").append(ammeterId);
        sb.append(", ammeterAppCode=").append(ammeterAppCode);
        sb.append(", ammeterDistination=").append(ammeterDistination);
        sb.append(", ammeterInfo=").append(ammeterInfo);
        sb.append(", fileId=").append(fileId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}