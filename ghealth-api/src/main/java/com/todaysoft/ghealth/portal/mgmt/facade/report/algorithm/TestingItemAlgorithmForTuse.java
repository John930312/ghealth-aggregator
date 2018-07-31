package com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.impl.core.TestingItemAlgorithmConfig;
import com.todaysoft.ghealth.service.impl.core.TestingItemLocusEvaluateResult;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestingItemAlgorithmForTuse extends AbstractTestingItemAlgorithm
{
    @Override
    public TestingItemAlgorithmConfig getTestingItemAlgorithmConfig(TestingItem testingItem)
    {
        TestingItemAlgorithmConfig config = new TestingItemAlgorithmConfig();
        String evalAlgorithmDetails = testingItem.getEvalAlgorithmDetails();
        if (StringUtils.isEmpty(evalAlgorithmDetails))
        {
            throw new ServiceException("项目为" + testingItem.getName() + "没有配置算法");
        }
        List<CancerData> cancerData = JsonUtils.fromJson(evalAlgorithmDetails, new TypeReference<List<CancerData>>()
        {
        });
        if (Objects.isNull(cancerData))
        {
            throw new ServiceException("项目为" + testingItem.getName() + "json数据转对象有误");
        }
        config.setCancerData(cancerData);
        return config;
    }
    
    @Override
    public Double calculate(TestingItemAlgorithmConfig testingItemAlgorithmConfig, String sex, Map<String, TestingItemLocusEvaluateResult> map)
    {
        return 1D;
    }
    
    @Override
    public CancerData getTuseDatas(TestingItemAlgorithmConfig testingItemAlgorithmConfig, String sex, Map<String, TestingItemLocusEvaluateResult> map)
    {
        CancerData data = new CancerData();
        List<CancerData> tuseCancerDatas = testingItemAlgorithmConfig.getCancerData();
        
        MutableObject rs1042522Mutable = new MutableObject();
        MutableBoolean rs2070676Mutable = new MutableBoolean();
        String rs2070676 = null;
        String rs1799793 = null;
        MutableBoolean rs1799793Mutable = new MutableBoolean();
        for (Map.Entry<String, TestingItemLocusEvaluateResult> entry : map.entrySet())
        {
            if ("rs1042522".equals(entry.getKey()))
            {
                rs1042522Mutable.setValue(entry.getValue().getGenetype());
            }
            else if ("rs2070676".equals(entry.getKey()))
            {
                rs2070676 = entry.getValue().getGenetype();
                rs2070676Mutable.setValue(rs2070676.equals("GG"));
            }
            else if ("rs1799793".equals(entry.getKey()))
            {
                rs1799793 = entry.getValue().getGenetype();
                rs1799793Mutable.setValue(rs1799793.equals("AA"));
            }
        }
        
        Optional<CancerData> cancerData = tuseCancerDatas.stream().filter(a -> rs1042522Mutable.getValue().equals(a.getGeneType())).findFirst();
        
        List<TuseCancerData> list = Collections.EMPTY_LIST;
        Predicate<TuseCancerData> filter1 = x -> x.getSex().equals(0);
        Predicate<TuseCancerData> filter2 = x -> x.getSex().equals(Integer.valueOf(sex));
        
        if (cancerData.isPresent())
        {
            list = cancerData.get().getValue().stream().filter(filter1.or(filter2)).map(a -> {
                
                if (rs2070676Mutable.getValue() && rs2070676Mutable.getValue())
                {
                    a.setRealRisk(a.getRealRisk() * 1.1 * 1.1);
                    return a;
                }
                else if (rs2070676Mutable.getValue() || rs2070676Mutable.getValue())
                {
                    a.setRisk(a.getRealRisk() * 1.1 * 1.1);
                    return a;
                }
                return a;
                
            }).collect(Collectors.toList());
        }
        String flag = getflag(rs2070676, rs1799793, rs1042522Mutable.getValue().toString());
        data.setCancerFlag(flag);
        data.setValue(list);
        return data;
        
    }
    
    private String getflag(String CYP2E1, String ERCC2, String p53)
    {
        
        // flag = 1 --> -
        // flag = 2 --> -/+
        // flag = 3 --> +
        // flag = 4 --> ++
        // flag = 5 --> +++
        int flag = 0;
        if ("cc".equals(p53.toLowerCase()))
        {
            flag = 4;
        }
        else if ("gg".equals(p53.toLowerCase()))
        {
            flag = 2;
        }
        else
        {
            flag = 3;
        }
        if ("gg".equals(CYP2E1.toLowerCase()) && "aa".equals(ERCC2.toLowerCase()))
        {
            flag++;
        }
        if (1 == flag)
        {
            return "-";
        }
        else if (2 == flag)
        {
            return "-/+";
        }
        else if (3 == flag)
        {
            return "+";
        }
        else if (4 == flag)
        {
            return "++";
        }
        else
        {
            return "+++";
        }
    }
}
