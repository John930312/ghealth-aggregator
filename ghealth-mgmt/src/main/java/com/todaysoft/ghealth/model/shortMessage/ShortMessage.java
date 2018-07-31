package com.todaysoft.ghealth.model.shortMessage;

import java.util.Date;

public class ShortMessage {
    private String id;

    private String configDetails;

    private Boolean  agencyCustomized;

    private String agencyId;

    private Date createTime;

    private String creatorName;

    private Date updateTime;

    private String updatorName;

    private boolean deleted;

    private Date deleteTime;

    private String deletorName;

    private String agencyName;

    private ShortMessageCon shortMessageCon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConfigDetails() {
        return configDetails;
    }

    public void setConfigDetails(String configDetails) {
        this.configDetails = configDetails;
    }

    public Boolean getAgencyCustomized() {
        return agencyCustomized;
    }

    public void setAgencyCustomized(Boolean agencyCustomized) {
        this.agencyCustomized = agencyCustomized;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeletorName() {
        return deletorName;
    }

    public void setDeletorName(String deletorName) {
        this.deletorName = deletorName;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public ShortMessageCon getShortMessageCon() {
        return shortMessageCon;
    }

    public void setShortMessageCon(ShortMessageCon shortMessageCon) {
        this.shortMessageCon = shortMessageCon;
    }
}
