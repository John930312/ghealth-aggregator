package com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.mybatis.model.InfluenceFactor;
import com.todaysoft.ghealth.mybatis.model.Locus;

import com.todaysoft.ghealth.mybatis.model.TestingItemLocus;

import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.impl.core.TestingItemLocusEvaluateConfig;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractTestingItemAlgorithm implements TestingItemAlgorithm
{
    public Map<String, TestingItemLocusEvaluateConfig> getLocusConfig(String itemName, List<TestingItemLocus> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            throw new ServiceException("项目-" + itemName + "没有关联位点！");
        }
        
        final Map<String, TestingItemLocusEvaluateConfig> configs = new HashMap<String, TestingItemLocusEvaluateConfig>();
        
        records.forEach(record -> {
            
            TestingItemLocusEvaluateConfig config = new TestingItemLocusEvaluateConfig();
            Locus locus = new Locus();
            locus.setId(record.getLocusId());
            locus.setName(record.getLocusName());
            locus.setGeneName(record.getGeneName());
            config.setLocus(locus);
            
            if (!StringUtils.isEmpty(record.getInfluenceFactors()))
            {
                List<InfluenceFactor> factors = JsonUtils.fromJson(record.getInfluenceFactors(), new TypeReference<List<InfluenceFactor>>()
                {
                });
                
                if (!CollectionUtils.isEmpty(factors))
                {
                    final Map<String, InfluenceFactor> mappings = new HashMap<String, InfluenceFactor>();
                    factors.forEach(factor -> mappings.put(factor.getGenetype(), factor));
                    config.setFactors(mappings);
                }
            }
            
            configs.put(record.getLocusName(), config);
        });
        return configs;
    }
}
