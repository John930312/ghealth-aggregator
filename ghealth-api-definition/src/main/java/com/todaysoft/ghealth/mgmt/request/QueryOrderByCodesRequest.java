package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

public class QueryOrderByCodesRequest extends SignatureTokenRequest
{
    private Set<String> codes;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        fields.put("codes", CollectionUtils.isEmpty(codes) ? "" : StringUtils.join(codes, ","));
    }
    
    public Set<String> getCodes()
    {
        return codes;
    }
    
    public void setCodes(Set<String> codes)
    {
        this.codes = codes;
    }
}
