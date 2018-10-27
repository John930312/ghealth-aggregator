package com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.impl.core.ItemLevelEvaluator;
import com.todaysoft.ghealth.service.impl.core.TestingItemAlgorithmConfig;
import com.todaysoft.ghealth.service.impl.core.TestingItemEvaluateResult;
import com.todaysoft.ghealth.service.impl.core.TestingItemLocusEvaluateResult;
import com.todaysoft.ghealth.utils.DictUtils;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableDouble;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
    public CancerData getTuseDatas(TestingItemAlgorithmConfig testingItemAlgorithmConfig, String sex, TestingItemEvaluateResult result)
    {
        Map<String, TestingItemLocusEvaluateResult> map = result.getLocusEvaluateResultsAsMap();
        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
        CancerData data = new CancerData();
        List<CancerData> tuseCancerDatas = testingItemAlgorithmConfig.getCancerData();
        TestingItem testingItem = testingItemAlgorithmConfig.getTestingItem();
        
        TestingItemLocusEvaluateResult rs1042522 = null;
        TestingItemLocusEvaluateResult rs2070676 = null;
        TestingItemLocusEvaluateResult rs1799793 = null;
        for (Map.Entry<String, TestingItemLocusEvaluateResult> entry : map.entrySet())
        {
            if ("rs1042522".equals(entry.getKey()))
            {
                rs1042522 = entry.getValue();
            }
            else if ("rs2070676".equals(entry.getKey()))
            {
                rs2070676 = entry.getValue();
            }
            else if ("rs1799793".equals(entry.getKey()))
            {
                rs1799793 = entry.getValue();
            }
        }
        
        final String rs1042522GeneType = rs1042522.getGenetype();
        
        final Double rs1042522Factor = rs1042522.getFactor();
        
        Predicate<CancerData> equals1 = a -> rs1042522GeneType.equals(a.getGeneType());
        Predicate<CancerData> equals2 = a -> rs1042522GeneType.equals(StringUtils.reverse(a.getGeneType()));
        Optional<CancerData> cancerData = tuseCancerDatas.stream().filter(equals1.or(equals2)).findFirst();
        
        List<TuseCancerData> list;
        Predicate<TuseCancerData> filter1 = x -> x.getSex().equals(0);
        Predicate<TuseCancerData> filter2 = x -> x.getSex().equals(Integer.valueOf(sex));
        Integer flag;
        
        MutableDouble ageRisk = new MutableDouble(1.0);
        String ageByBirthday = DictUtils.getAgeByBirthday(result.getCustomerBirthday());
        if (StringUtils.isNotEmpty(ageByBirthday))
        {
            ageRisk.setValue(DictUtils.getByAge(Integer.valueOf(ageByBirthday)));
        }
        
        if ("TUSE".equals(testingItem.getCode()) && cancerData.isPresent())
        {
            final Double rs2070676Factor = rs2070676.getFactor();
            final Double rs1799793Factor = rs1799793.getFactor();
            list = cancerData.get().getValue().stream().filter(filter1.or(filter2)).map(a -> {
                Optional<TuseCancerRisk> risk = a.getRisk().stream().filter(x -> Integer.valueOf(sex).equals(x.getSex())).findFirst();
                Optional<TuseCancerRisk> avgRisk = a.getAvgRisk().stream().filter(x -> Integer.valueOf(sex).equals(x.getSex())).findFirst();
                Double realRisk =
                    Double.valueOf(decimalFormat.format(risk.get().getRisk() * rs1042522Factor * rs2070676Factor * rs1799793Factor * ageRisk.doubleValue()));
                a.setRiskBySex(realRisk);
                a.setAvgRiskBySex(avgRisk.get().getRisk());
                a.setRealRisk(Double.valueOf(decimalFormat.format(realRisk / avgRisk.get().getRisk())));
                return a;
            }).collect(toList());
            flag = ItemLevelEvaluator
                .getLevelInterval(testingItem, Double.valueOf(decimalFormat.format(rs1042522.getFactor() * rs2070676Factor * rs1799793Factor)), 5);
        }
        else
        {
            list = cancerData.get().getValue().stream().filter(filter1.or(filter2)).collect(toList());
            cancerData.get().getValue().stream().filter(filter1.or(filter2)).map(a -> {
                Optional<TuseCancerRisk> risk = a.getRisk().stream().filter(x -> Integer.valueOf(sex).equals(x.getSex())).findFirst();
                Optional<TuseCancerRisk> avgRisk = a.getAvgRisk().stream().filter(x -> Integer.valueOf(sex).equals(x.getSex())).findFirst();
                a.setRiskBySex(Double.valueOf(decimalFormat.format(risk.get().getRisk() * ageRisk.getValue() * rs1042522Factor)));
                a.setAvgRiskBySex(avgRisk.get().getRisk());
                a.setRealRisk(Double.valueOf(decimalFormat.format(a.getRiskBySex() / avgRisk.get().getRisk())));
                return a;
            }).collect(toList());
            flag = ItemLevelEvaluator.getLevelInterval(testingItem, rs1042522.getFactor(), 5);
        }
        
        data.setCancerFlag(DictUtils.getTuseLevelTextByValue(flag));
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
            return "低风险";
        }
        else if (2 == flag)
        {
            return "一般风险";
        }
        else if (3 == flag || 4 == flag)
        {
            return "较高风险";
        }
        else
        {
            return "高风险";
        }
    }
    
}
