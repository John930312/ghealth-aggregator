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

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.toList;

public class TuseContentsGenerator extends AbstractReportContentsGenerator
{
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
                    }).collect(toList());
                    if (!CollectionUtils.isEmpty(datas))
                    {
                        Map<Integer, List<TuseCancerData>> collect = datas.stream().collect(Collectors.groupingBy(TuseCancerData::getLevel));
                        if (!CollectionUtils.isEmpty(collect))
                        {
                            List<Integer> sortList = new ArrayList<>(collect.keySet()).stream().sorted(reverseOrder()).collect(toList());
                            String cancerName = "";
                            for (int i = 0; i < sortList.size(); i++)
                            {
                                if (i == 2)
                                {
                                    break;
                                }
                                if (sortList.get(i) < 3)
                                {
                                    continue;
                                }
                                String name = collect.get(sortList.get(i)).stream().map(b -> DictUtils.getTuseCancerMap().get(b.getCancerName())).collect(
                                    Collectors.joining(" "));
                                if (!StringUtils.isEmpty(name))
                                {
                                    cancerName += name + " ";
                                }
                            }
                            if (org.apache.commons.lang3.StringUtils.isNotEmpty(cancerName))
                            {
                                contents.add(new TextBookmarkContent("TUSE_FOLLOW_CANCERS", "尤其是" + cancerName));
                            }
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
        NumberFormat format = NumberFormat.getInstance();
        List<String[]> records = new ArrayList<>();
        List<String[]> datas = tuseCancerDatas.stream().map(x -> {
            List<String> strs = new ArrayList<>();
            strs.add(String.valueOf(DictUtils.getTuseCancerMap().get(x.getCancerName())));
            strs.add(format.format(x.getAvgRiskBySex()));
            strs.add(format.format(x.getRiskBySex()));
            strs.add(DictUtils.getTuseLevelTextByValue(x.getLevel()));
            return strs.toArray(new String[strs.size()]);
        }).collect(toList());
        TableBookmarkContent content = new TableBookmarkContent();
        content.setBookmarkName("TUSE_RELATED_CANCERS");
        content.setRecords(datas);
        return content;
    }
}
