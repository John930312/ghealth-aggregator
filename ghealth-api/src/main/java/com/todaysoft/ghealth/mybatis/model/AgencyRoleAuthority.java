package com.todaysoft.ghealth.mybatis.model;

import com.todaysoft.ghealth.base.response.model.Authority;

public class AgencyRoleAuthority {
    private AgencyRole agencyRole;

    private Authority authority;

    public AgencyRole getAgencyRole() {
        return agencyRole;
    }

    public void setAgencyRole(AgencyRole agencyRole) {
        this.agencyRole = agencyRole;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}
