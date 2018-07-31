package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.model.Gene;
import com.todaysoft.ghealth.base.response.model.GeneDetails;

@Component
public class GeneWrapper
{
    public List<Gene> wrap(List<com.todaysoft.ghealth.mybatis.model.Gene> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        Gene gene;
        List<Gene> genes = new ArrayList<Gene>();
        for (com.todaysoft.ghealth.mybatis.model.Gene record : records)
        {
            gene = new Gene();
            wrap(record, gene);
            genes.add(gene);
        }
        
        return genes;
    }
    
    public GeneDetails wrap(com.todaysoft.ghealth.mybatis.model.Gene record)
    {
        GeneDetails details = new GeneDetails();
        wrap(record, details);
        return details;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.Gene source, Gene target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        
        target.setCreateTime(null == source.getCreateTime() ? null : source.getCreateTime().getTime());
        target.setUpdateTime(null == source.getUpdateTime() ? null : source.getUpdateTime().getTime());
        target.setDeleteTime(null == source.getDeleteTime() ? null : source.getDeleteTime().getTime());
    }
    
}
