package com.todaysoft.ghealth.base.response.model;

import java.util.List;

public class GeneDetails extends Gene
{
    private List<GeneDocument> geneDocuments;
    
    public List<GeneDocument> getGeneDocuments()
    {
        return geneDocuments;
    }
    
    public void setGeneDocuments(List<GeneDocument> geneDocuments)
    {
        this.geneDocuments = geneDocuments;
    }
}
