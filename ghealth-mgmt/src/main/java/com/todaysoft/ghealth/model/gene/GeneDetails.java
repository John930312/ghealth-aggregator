package com.todaysoft.ghealth.model.gene;

import java.util.List;

public class GeneDetails extends Gene
{
    public List<GeneDocument> getGeneDocuments()
    {
        return geneDocuments;
    }
    
    public void setGeneDocuments(List<GeneDocument> geneDocuments)
    {
        this.geneDocuments = geneDocuments;
    }
    
    List<GeneDocument> geneDocuments;
}
