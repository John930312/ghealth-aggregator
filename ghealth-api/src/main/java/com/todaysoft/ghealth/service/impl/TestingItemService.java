package com.todaysoft.ghealth.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.mybatis.mapper.TestingItemMapper;
import com.todaysoft.ghealth.mybatis.model.Cancer;
import com.todaysoft.ghealth.mybatis.model.InfluenceFactor;
import com.todaysoft.ghealth.mybatis.model.Locus;
import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.mybatis.model.TestingItemLocus;
import com.todaysoft.ghealth.mybatis.searcher.ItemLocusSearcher;
import com.todaysoft.ghealth.mybatis.searcher.TestingItemSearcher;
import com.todaysoft.ghealth.service.ICancerService;
import com.todaysoft.ghealth.service.IItemLocusService;
import com.todaysoft.ghealth.service.ITestingItemService;
import com.todaysoft.ghealth.service.impl.core.TestingItemEvaluateConfig;
import com.todaysoft.ghealth.service.impl.core.TestingItemEvaluateReferenceValue;
import com.todaysoft.ghealth.service.impl.core.TestingItemLocusEvaluateConfig;
import com.todaysoft.ghealth.utils.JsonUtils;

@Service
public class TestingItemService implements ITestingItemService
{
    @Autowired
    private TestingItemMapper mapper;
    
    @Autowired
    private ICancerService cancerService;
    
    @Autowired
    private IItemLocusService testingItemLocusService;
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof TestingItemSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((TestingItemSearcher)searcher);
    }
    
    @Override
    public List<TestingItem> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof TestingItemSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            TestingItemSearcher tis = (TestingItemSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.search((TestingItemSearcher)searcher);
    }
    
    @Override
    public List<TestingItem> list(TestingItemSearcher searcher)
    {
        return mapper.search(searcher);
    }
    
    @Override
    public List<TestingItem> getItemsForProduct(String productId)
    {
        TestingItemSearcher searcher = new TestingItemSearcher();
        searcher.setProductId(productId);
        return list(searcher);
    }
    
    @Override
    public TestingItemEvaluateConfig getTestingItemEvaluateConfig(TestingItem testingItem)
    {
        if (null == testingItem)
        {
            return null;
        }
        
        ItemLocusSearcher searcher = new ItemLocusSearcher();
        searcher.setItemId(testingItem.getId());
        List<TestingItemLocus> records = testingItemLocusService.list(searcher);
        
        if (CollectionUtils.isEmpty(records))
        {
            return null;
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
        
        if (CollectionUtils.isEmpty(configs))
        {
            return null;
        }
        
        TestingItemEvaluateReferenceValue referenceValue = getEvaluateReferenceValue(testingItem);
        
        TestingItemEvaluateConfig testingItemEvaluateConfig = new TestingItemEvaluateConfig();
        testingItemEvaluateConfig.setTestingItem(testingItem);
        testingItemEvaluateConfig.setReferenceValue(referenceValue);
        testingItemEvaluateConfig.setLocusConfig(configs);
        return testingItemEvaluateConfig;
    }
    
    private TestingItemEvaluateReferenceValue getEvaluateReferenceValue(TestingItem testingItem)
    {
        String category = testingItem.getCategory();
        
        if (TestingItem.CATEGORY_DISEASE_RISK.equals(category))
        {
            Cancer cancer = cancerService.get(testingItem.getCategoryMapping());
            
            if (null == cancer)
            {
                return null;
            }
            
            TestingItemEvaluateReferenceValue referenceValue = new TestingItemEvaluateReferenceValue();
            referenceValue.setMaleValue(cancer.getRiskmale());
            referenceValue.setFemaleValue(cancer.getRiskfemale());
            return referenceValue;
        }
        
        return null;
    }
    
    @Override
    public TestingItem get(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    @Transactional
    public void create(TestingItem data)
    {
        mapper.insert(data);
    }
    
    @Override
    @Transactional
    public void modify(TestingItem data)
    {
        mapper.update(data);
    }
    
    @Override
    public boolean isCodeUnique(String id, String code)
    {
        TestingItemSearcher searcher = new TestingItemSearcher();
        searcher.setCode(code);
        searcher.setCodeExactMatches(true);
        
        if (!StringUtils.isEmpty(id))
        {
            searcher.setExcludeKeys(Collections.singleton(id));
        }
        
        int count = mapper.count(searcher);
        return count == 0;
    }
    
    @Override
    public boolean isRequiredSexForGenerate(List<TestingItem> testingItems)
    {
        if (CollectionUtils.isEmpty(testingItems))
        {
            return false;
        }
        
        for (TestingItem testingItem : testingItems)
        {
            if (isRequiredSexForGenerate(testingItem))
            {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean isRequiredSexForGenerate(TestingItem testingItem)
    {
        return true;
    }
}
