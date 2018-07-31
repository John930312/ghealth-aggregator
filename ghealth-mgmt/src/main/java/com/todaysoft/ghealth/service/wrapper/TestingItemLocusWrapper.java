package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.todaysoft.ghealth.model.item.TestingItem;
import com.todaysoft.ghealth.model.locus.Locus;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.dto.TestingItemLocusDTO;
import com.todaysoft.ghealth.mgmt.model.InfluenceFactorDTO;
import com.todaysoft.ghealth.model.itemLocus.InfluenceFactor;
import com.todaysoft.ghealth.model.itemLocus.TestingItemLocus;

@Component
public class TestingItemLocusWrapper
{
    public List<TestingItemLocus> wrap(List<TestingItemLocusDTO> sources)
    {
        if (CollectionUtils.isEmpty(sources))
        {
            return Collections.emptyList();
        }
        
        TestingItemLocus target;
        List<TestingItemLocus> targets = new ArrayList<TestingItemLocus>();
        
        for (TestingItemLocusDTO source : sources)
        {
            target = new TestingItemLocus();
            wrap(source, target);
            targets.add(target);
        }
        
        return targets;
    }
    
    public TestingItemLocus wrap(TestingItemLocusDTO source)
    {
        if (null == source)
        {
            return null;
        }
        
        TestingItemLocus target = new TestingItemLocus();
        wrap(source, target);
        return target;
    }
    
    private void wrap(TestingItemLocusDTO source, TestingItemLocus target)
    {
        BeanUtils.copyProperties(source, target, "influenceFactors");

        if(StringUtils.isNotEmpty(source.getLocusId()))
        {
            Locus l = new Locus();
            l.setId(source.getLocusId());
            l.setName(source.getLocusName());
            target.setLocusJson(JsonUtils.toJson(l));
        }
        if(StringUtils.isNotEmpty(source.getTestingItemId()))
        {
            TestingItem t = new TestingItem();
            t.setId(source.getTestingItemId());
            t.setName(source.getTestingItemName());
            t.setSexRestraint(source.getTestingItemSexRestraint());
            target.setTestingItemJson(JsonUtils.toJson(t));
        }
        if (!CollectionUtils.isEmpty(source.getInfluenceFactors()))
        {
            InfluenceFactor targetInfluenceFactor;
            List<InfluenceFactor> targetInfluenceFactors = new ArrayList<InfluenceFactor>();
            
            for (InfluenceFactorDTO sourceInfluenceFactor : source.getInfluenceFactors())
            {
                targetInfluenceFactor = new InfluenceFactor();
                BeanUtils.copyProperties(sourceInfluenceFactor, targetInfluenceFactor);
                targetInfluenceFactors.add(targetInfluenceFactor);
            }
            
            target.setInfluenceFactors(targetInfluenceFactors);
        }
    }
}
