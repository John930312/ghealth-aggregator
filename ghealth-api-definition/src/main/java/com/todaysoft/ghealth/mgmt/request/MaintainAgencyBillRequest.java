package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

import java.math.BigDecimal;
import java.util.Map;

public class MaintainAgencyBillRequest extends SignatureTokenRequest
{
    private String id;
    
    private String agencyId;
    
    private String title;
    
    private Boolean increased;
    
    private String billType;
    
    private String eventDetails;
    
    private BigDecimal amountBefore;
    
    private BigDecimal amountAfter;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("id", id);
        fields.put("agencyId", title);
        fields.put("title", title);
        fields.put("increased", String.valueOf(increased));
        fields.put("billType", billType);
        fields.put("eventDetails", eventDetails);
        fields.put("amountBefore", String.valueOf(amountBefore));
        fields.put("amountAfter", String.valueOf(amountAfter));
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
}
