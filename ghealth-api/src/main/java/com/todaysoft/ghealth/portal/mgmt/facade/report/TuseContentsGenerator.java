package com.todaysoft.ghealth.portal.mgmt.facade.report;

import com.todaysoft.document.generate.sdk.request.TableBookmarkContent;
import com.todaysoft.document.generate.sdk.request.TextBookmarkContent;
import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm.TuseCancerData;
import com.todaysoft.ghealth.service.impl.core.ItemLevelEvaluator;
import com.todaysoft.ghealth.service.impl.core.TestingItemAlgorithmConfig;
import com.todaysoft.ghealth.service.impl.core.TestingItemEvaluateResult;
import com.todaysoft.ghealth.utils.DictUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TuseContentsGenerator extends AbstractReportContentsGenerator
{
    private final Integer LevelForFour = 4;
    
    private final Integer LevelForThree = 3;
    
    @Override
    protected List<TextBookmarkContent> generateTextBookmarkContents(ReportGenerateContext context)
    {
        List<TextBookmarkContent> contents = new ArrayList<>();
        List<TestingItemEvaluateResult> itemResults = context.getTestingItemEvaluateResults();
        
        for (TestingItemEvaluateResult result : itemResults)
        {
            TestingItemAlgorithmConfig algorithmConfig = result.getAlgorithmConfig();
            if (Objects.isNull(algorithmConfig))
            {
                continue;
            }
            TestingItem testingItem = algorithmConfig.getTestingItem();
            if (Objects.isNull(testingItem))
            {
                continue;
            }
            // 抑癌基因项目
            if (testingItem.getCode().startsWith("TUSE"))
            {
                Optional.ofNullable(result.getCancerData()).ifPresent(x -> {
                    List<TuseCancerData> datas = x.getValue().stream().map(a -> {
                        Integer leve = ItemLevelEvaluator.getLevelInterval(testingItem, a.getRealRisk(), 5);
                        a.setLevel(Optional.ofNullable(leve).orElse(0));
                        return a;
                    }).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(datas))
                    {
                        String cancerName =
                            datas.stream().filter(b -> LevelForFour.equals(b.getLevel())).map(b -> DictUtils.getTuseCancerMap().get(b.getCancerName())).collect(
                                Collectors.joining("、"));
                        if (StringUtils.isEmpty(cancerName))
                        {
                            cancerName = datas.stream()
                                .filter(b -> LevelForThree.equals(b.getLevel()))
                                .map(b -> DictUtils.getTuseCancerMap().get(b.getCancerName()))
                                .collect(Collectors.joining("、"));
                        }
                        if (org.apache.commons.lang3.StringUtils.isNotEmpty(cancerName))
                        {
                            contents.add(new TextBookmarkContent("TUSE_FOLLOW_CANCERS", "尤其是" + cancerName));
                        }
                    }
                    contents.add(new TextBookmarkContent("TUSE_RISK_FLAG", result.getCancerData().getCancerFlag()));
                });
            }
        }
        
        return contents;
    }
    
    @Override
    protected List<TableBookmarkContent> generateTableBookmarkContents(ReportGenerateContext context)
    {
        List<TableBookmarkContent> contents = new ArrayList<>();
        List<TestingItemEvaluateResult> itemResults = context.getTestingItemEvaluateResults();
        
        for (TestingItemEvaluateResult result : itemResults)
        {
            TestingItemAlgorithmConfig algorithmConfig = result.getAlgorithmConfig();
            if (Objects.isNull(algorithmConfig))
            {
                continue;
            }
            TestingItem testingItem = algorithmConfig.getTestingItem();
            if (Objects.isNull(testingItem))
            {
                continue;
            }
            // 抑癌基因
            if (testingItem.getCode().startsWith("TUSE"))
            {
                Optional.ofNullable(result.getCancerData()).ifPresent(x -> {
                    contents.add(getTableBookmarkContents(x.getValue()));
                });
            }
        }
        return contents;
    }
    
    private TableBookmarkContent getTableBookmarkContents(List<TuseCancerData> tuseCancerDatas)
    {
        List<String[]> records = new ArrayList<>();
        List<String[]> datas = tuseCancerDatas.stream().map(x -> {
            List<String> strs = new ArrayList<>();
            strs.add(String.valueOf(DictUtils.getTuseCancerMap().get(x.getCancerName())));
            strs.add(String.valueOf(x.getAvgRiskBySex()));
            strs.add(String.valueOf(x.getRiskBySex()));
            strs.add(String.valueOf(DictUtils.getTuseLevelTextByValue(x.getLevel())));
            return strs.toArray(new String[strs.size()]);
        }).collect(Collectors.toList());
        TableBookmarkContent content = new TableBookmarkContent();
        content.setBookmarkName("TUSE_RELATED_CANCERS");
        content.setRecords(datas);
        return content;
    }
    
}
