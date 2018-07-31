package com.todaysoft.ghealth.base.response.model;

import java.util.List;

public class AgencyRole {
    private String name;

    private String id;

    private String agencyId;

    private Long createTime;

    private String creatorName;

    private Long updateTime;

    private String updatorName;

    private boolean deleted;

    private Long deleteTime;

    private String deletorName;

    private String authorityIds;

    private List<Authority> roleAuthorities;

    public List<Authority> getRoleAuthorities()
    {
        return roleAuthorities;
    }

    public List<AgencyAccount> agencyAccounts;

    public List<AgencyAccount> getAgencyAccounts() {
        return agencyAccounts;
    }

    public void setAgencyAccounts(List<AgencyAccount> agencyAccounts) {
        this.agencyAccounts = agencyAccounts;
    }

    public void setRoleAuthorities(List<Authority> roleAuthorities)
    {
        this.roleAuthorities = roleAuthorities;
    }

    public String getAuthorityIds()
    {
        return authorityIds;
    }

    public void setAuthorityIds(String authorityIds)
    {
        this.authorityIds = authorityIds;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCreatorName()
    {
        return creatorName;
    }

    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }

    public String getUpdatorName()
    {
        return updatorName;
    }

    public void setUpdatorName(String updatorName)
    {
        this.updatorName = updatorName;
    }

    public boolean isDeleted()
    {
        return deleted;
    }

    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    public String getDeletorName()
    {
        return deletorName;
    }

    public void setDeletorName(String deletorName)
    {
        this.deletorName = deletorName;
    }

    public Long getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Long createTime)
    {
        this.createTime = createTime;
    }

    public Long getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime)
    {
        this.updateTime = updateTime;
    }

    public Long getDeleteTime()
    {
        return deleteTime;
    }

    public void setDeleteTime(Long deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }
}
