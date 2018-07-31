package com.todaysoft.ghealth.base.response.model;

import java.util.List;

public class AgencyDetails extends Agency
{
    private List<AgencyProduct> agentProducts;
    
    public List<AgencyProduct> getAgentProducts()
    {
        return agentProducts;
    }
    
    public void setAgentProducts(List<AgencyProduct> agentProducts)
    {
        this.agentProducts = agentProducts;
    }
}
