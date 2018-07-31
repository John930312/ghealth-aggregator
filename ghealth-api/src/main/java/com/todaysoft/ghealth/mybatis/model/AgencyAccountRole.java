package com.todaysoft.ghealth.mybatis.model;

public class AgencyAccountRole {
    private AgencyRole agencyRole;

    private AgencyAccount agencyAccount;

    public AgencyRole getAgencyRole() {
        return agencyRole;
    }

    public void setAgencyRole(AgencyRole agencyRole) {
        this.agencyRole = agencyRole;
    }

    public AgencyAccount getAgencyAccount() {
        return agencyAccount;
    }

    public void setAgencyAccount(AgencyAccount agencyAccount) {
        this.agencyAccount = agencyAccount;
    }
}
