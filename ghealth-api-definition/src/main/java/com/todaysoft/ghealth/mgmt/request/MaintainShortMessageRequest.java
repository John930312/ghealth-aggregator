package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

public class MaintainShortMessageRequest extends SignatureTokenRequest {
    private String id;

    private String configDetails;

    private Boolean  agencyCustomized;

    private String agencyId;

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
}
