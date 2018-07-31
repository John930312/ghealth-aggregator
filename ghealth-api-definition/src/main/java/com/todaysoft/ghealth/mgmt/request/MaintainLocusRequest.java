package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

public class MaintainLocusRequest extends SignatureTokenRequest
{
    private String id;
    
    private String name;
    
    private String geneId;
    
    private String site;
    
    private String fragment;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSite()
    {
        return site;
    }
    
    public void setSite(String site)
    {
        this.site = site;
    }
    
    public String getFragment()
    {
        return fragment;
    }
    
    public void setFragment(String fragment)
    {
        this.fragment = fragment;
    }
    
    public String getGeneId()
    {
        return geneId;
    }
    
    public void setGeneId(String geneId)
    {
        this.geneId = geneId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
