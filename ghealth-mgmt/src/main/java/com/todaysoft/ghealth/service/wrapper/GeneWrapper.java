package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.GeneDetails;
import com.todaysoft.ghealth.model.gene.Gene;

@Component
public class GeneWrapper
{
    public List<Gene> wrap(List<com.todaysoft.ghealth.base.response.model.Gene> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Gene gene;
        List<Gene> genes = new ArrayList<Gene>();
        for (com.todaysoft.ghealth.base.response.model.Gene record : records)
        {
            gene = new Gene();
            wrapRecord(record, gene);
            genes.add(gene);
        }
        return genes;
    }
    
    public GeneDetails wrap(com.todaysoft.ghealth.base.response.model.GeneDetails record)
    {
        if (null == record)
        {
            return null;
        }
        
        GeneDetails details = new GeneDetails();
        wrapDetails(record, details);
        return details;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.Gene source, Gene target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
    }
    
    private void wrapDetails(com.todaysoft.ghealth.base.response.model.GeneDetails source, GeneDetails target)
    {
        /* BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime", "agentProducts");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
        target.setDeleteTime(null == source.getDeleteTime() ? null : new Date(source.getDeleteTime()));
        target.setGeneDocuments(agencyProductWrapper.wrap(source.getAgentProducts()));*/
    }
}
