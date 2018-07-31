package com.todaysoft.ghealth.base.response.model;

public class ShortMessage {
    private String id;

    private String configDetails;

    private Boolean agencyCustomized;

    private String agencyId;

    private Long createTime;

    private String creatorName;

    private Long updateTime;

    private String updatorName;

    private boolean deleted;

    private Long deleteTime;

    private String deletorName;


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

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Long deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeletorName() {
        return deletorName;
    }

    public void setDeletorName(String deletorName) {
        this.deletorName = deletorName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

}
