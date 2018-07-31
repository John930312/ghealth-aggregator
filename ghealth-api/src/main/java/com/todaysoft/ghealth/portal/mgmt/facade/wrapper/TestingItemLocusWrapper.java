package com.todaysoft.ghealth.portal.mgmt.facade.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.base.response.dto.TestingItemLocusDTO;
import com.todaysoft.ghealth.mgmt.model.InfluenceFactorDTO;
import com.todaysoft.ghealth.mybatis.model.TestingItemLocus;
import com.todaysoft.ghealth.service.wrapper.Wrapper;
import com.todaysoft.ghealth.utils.JsonUtils;

@Component
public class TestingItemLocusWrapper implements Wrapper<TestingItemLocus, TestingItemLocusDTO>
{
    @Override
    public List<TestingItemLocusDTO> wrap(List<TestingItemLocus> sources)
    {
        if (CollectionUtils.isEmpty(sources))
        {
            return Collections.emptyList();
        }
        
        TestingItemLocusDTO target;
        List<TestingItemLocusDTO> targets = new ArrayList<TestingItemLocusDTO>();
        
        for (TestingItemLocus source : sources)
        {
            target = new TestingItemLocusDTO();
            wrap(source, target);
            targets.add(target);
        }
        
        return targets;
    }
    
    public TestingItemLocusDTO wrap(TestingItemLocus source)
    {
        if (null == source)
        {
            return null;
        }
        
        TestingItemLocusDTO target = new TestingItemLocusDTO();
        wrap(source, target);
        return target;
    }
    
    private void wrap(TestingItemLocus source, TestingItemLocusDTO target)
    {
        BeanUtils.copyProperties(source, target, "influenceFactors");
        
        if (!StringUtils.isEmpty(source.getInfluenceFactors()))
        {
            List<InfluenceFactorDTO> factors = JsonUtils.fromJson(source.getInfluenceFactors(), new TypeReference<List<InfluenceFactorDTO>>()
            {
            });
            
            target.setInfluenceFactors(factors);
        }
    }
}
