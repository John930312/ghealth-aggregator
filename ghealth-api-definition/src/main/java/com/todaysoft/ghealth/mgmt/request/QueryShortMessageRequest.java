package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

public class QueryShortMessageRequest extends SignatureTokenListRequest {
    public Boolean getAgencyCustomized() {
        return agencyCustomized;
    }

    public void setAgencyCustomized(Boolean agencyCustomized) {
        this.agencyCustomized = agencyCustomized;
    }

    private Boolean  agencyCustomized;

    private String  agencyId;

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }
}
