package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

/**
 * Created by xjw on 2017/9/14.
 */
public class QueryProductAgencyRequest extends SignatureTokenListRequest
{
    private String productId;
    
    private String productName;

    private String agencyId;
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("productName", productName);
        fields.put("productId", productId);
        fields.put("agencyId", agencyId);
    }
}
