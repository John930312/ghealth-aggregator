package com.todaysoft.ghealth.model.agency;

import java.util.List;

import com.todaysoft.ghealth.model.apa.AgencyProduct;

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
