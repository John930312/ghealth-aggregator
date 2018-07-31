package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

public class QueryLocusByNamesRequest extends SignatureTokenRequest
{
    private Set<String> names;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        fields.put("names", CollectionUtils.isEmpty(names) ? "" : StringUtils.join(names, ","));
    }
    
    public Set<String> getNames()
    {
        return names;
    }
    
    public void setNames(Set<String> names)
    {
        this.names = names;
    }
}
