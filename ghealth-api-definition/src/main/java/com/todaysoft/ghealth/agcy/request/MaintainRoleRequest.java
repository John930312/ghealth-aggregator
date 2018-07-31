package com.todaysoft.ghealth.agcy.request;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

import java.util.Map;

public class MaintainRoleRequest extends SignatureTokenRequest {
    private String name;

    private String id;

    private String authorityIds;

    private String userId;

    private String agencyId;

    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("id", id);
        fields.put("name", name);
        fields.put("userId", userId);
        fields.put("authorityIds", authorityIds);
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAuthorityIds()
    {
        return authorityIds;
    }

    public void setAuthorityIds(String authorityIds)
    {
        this.authorityIds = authorityIds;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }
}
