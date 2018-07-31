package com.todaysoft.ghealth.model.role;

import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.model.agencyAccount.AgencyAccount;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Role {
    private String name;

    private String id;

    private String agencyId;

    private Date createTime;

    private String creatorName;

    private Date updateTime;

    private String updatorName;

    private boolean deleted;

    private Date deleteTime;

    private String deletorName;

    private String authorityIds;

    private List<Authority> roleAuthorities;

    private Set<String> userIds;

    public Set<String> getUserIds()
    {
        return userIds;
    }

    public List<AgencyAccount> agencyAccounts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAuthorityIds() {
        return authorityIds;
    }

    public void setAuthorityIds(String authorityIds) {
        this.authorityIds = authorityIds;
    }

    public List<Authority> getRoleAuthorities() {
        return roleAuthorities;
    }

    public void setRoleAuthorities(List<Authority> roleAuthorities) {
        this.roleAuthorities = roleAuthorities;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    public List<AgencyAccount> getAgencyAccounts() {
        return agencyAccounts;
    }

    public void setAgencyAccounts(List<AgencyAccount> agencyAccounts) {
        this.agencyAccounts = agencyAccounts;
    }
}
