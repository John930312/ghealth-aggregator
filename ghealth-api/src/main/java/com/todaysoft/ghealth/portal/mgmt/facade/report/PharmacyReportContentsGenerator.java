package com.todaysoft.ghealth.portal.mgmt.facade.report;

import com.todaysoft.document.generate.sdk.request.TableBookmarkContent;
import com.todaysoft.document.generate.sdk.request.TextBookmarkContent;
import com.todaysoft.ghealth.base.response.model.Drug;
import com.todaysoft.ghealth.config.RootContext;
import com.todaysoft.ghealth.mybatis.model.Dict;
import com.todaysoft.ghealth.mybatis.model.PharmacyTemModel;
import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.mybatis.searcher.DictSearcher;
import com.todaysoft.ghealth.mybatis.searcher.DrugSearcher;
import com.todaysoft.ghealth.portal.mgmt.facade.DrugMgmtFacade;
import com.todaysoft.ghealth.service.IDictService;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.impl.core.*;
import com.todaysoft.ghealth.utils.DictUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class PharmacyReportContentsGenerator extends AbstractReportContentsGenerator
{
    private DrugMgmtFacade drugMgmtFacade = RootContext.getBean(DrugMgmtFacade.class);
    
    private IDictService dictService = RootContext.getBean(IDictService.class);
    
    private Map<Integer, String> resultMap = DictUtils.getResultMap();
    
    private Map<Integer, String> doseMap = DictUtils.getDoseMap();
    
    private final String DICT_CATEGORY = "DRUG";
    
    private List<TextBookmarkContent> textBookmarkContent = new ArrayList<>();
    
    private List<TableBookmarkContent> tableBookmarkContent = new ArrayList<>();
    
    /**
     * 药物性耳聋相关基因型
     */
    private List<String> drugInducedDeafnessGeneses = Arrays.asList("A1555G", "C1494T", "T1095C", "961InsCorDelT");
    
    @Override
    protected List<TextBookmarkContent> generateTextBookmarkContents(ReportGenerateContext context)
    {
        List<TestingItemEvaluateResult> itemResults = context.getTestingItemEvaluateResults();
        
        for (TestingItemEvaluateResult itemResult : itemResults)
        {
            TestingItemAlgorithmConfig algorithmConfig = itemResult.getAlgorithmConfig();
            if (Objects.isNull(algorithmConfig))
            {
                continue;
            }
            TestingItem testingItem = algorithmConfig.getTestingItem();
            if (Objects.isNull(testingItem))
            {
                continue;
            }
            
            // 是安全用药的项目
            if (TestingItem.CATEGORY_PHARMACY_RISK.equals(testingItem.getCategory()))
            {
                Map<String, PharmacyTemModel> map = getCalPharmacyMap(true, algorithmConfig, itemResult.getLocusEvaluateResultsAsMap());
                map.forEach((k, v) -> {
                    textBookmarkContent.add(new TextBookmarkContent(MessageFormat.format("GENE_{0}_RESULT", k), v.getResult()));
                    textBookmarkContent.add(new TextBookmarkContent(MessageFormat.format("GENE_{0}_DOSE", k), v.getDose()));
                });
            }
        }
        
        return textBookmarkContent;
    }
    
    @Override
    protected List<TableBookmarkContent> generateTableBookmarkContents(ReportGenerateContext context)
    {
        
        List<TestingItemEvaluateResult> itemResults = context.getTestingItemEvaluateResults();
        
        for (TestingItemEvaluateResult itemResult : itemResults)
        {
            TestingItemAlgorithmConfig algorithmConfig = itemResult.getAlgorithmConfig();
            if (Objects.isNull(algorithmConfig))
            {
                continue;
            }
            TestingItem testingItem = algorithmConfig.getTestingItem();
            if (Objects.isNull(testingItem))
            {
                continue;
            }
            // 是安全用药的项目
            if (TestingItem.CATEGORY_PHARMACY_RISK.equals(testingItem.getCategory()))
            {
                Map<String, PharmacyTemModel> map = getCalPharmacyMap(false, algorithmConfig, itemResult.getLocusEvaluateResultsAsMap());
                List<Dict> dictList = getDicts();
                //category  为36属于药物性耳聋
                Predicate<Dict> predicate = x -> "36".equals(x.getDictValue());
                List<Dict> dicts = "DID".equals(testingItem.getCode()) ? dictList.stream().filter(predicate).collect(toList())
                    : dictList.stream().filter(predicate.negate()).collect(toList());
                
                for (int i = 0; i < dicts.size(); i++)
                {
                    Dict dict = dicts.get(i);
                    
                    DrugSearcher searcher = new DrugSearcher();
                    searcher.setCategory(dict.getDictValue());
                    
                    boolean isASME = false;
                    //成人安全用药
                    if ("ASME".equals(testingItem.getCode()))
                    {
                        searcher.setIsAdultUsed(1);
                        isASME = true;
                    }
                    else
                    {
                        searcher.setIsChildrenUsed(1);
                    }
                    
                    List<Drug> drugs = drugMgmtFacade.getDrugByCategory(searcher);
                    
                    if (CollectionUtils.isEmpty(drugs))
                    {
                        continue;
                    }
                    tableBookmarkContent.add(getTableBookmarkContents(i + 1, drugs, map, isASME));
                }
            }
        }
        
        return tableBookmarkContent;
    }
    
    private TableBookmarkContent getTableBookmarkContents(Integer i, List<Drug> drugs, Map<String, PharmacyTemModel> map, boolean isASME)
    {
        List<String[]> records = new ArrayList<>();
        for (Drug drug : drugs)
        {
            List<String> record = new ArrayList<>();
            if (isASME)
            {
                record.add(drug.getIngredientCn());
                record.add(drug.getIngredientEn());
                record.add(drug.getGeneName());
                record.add(getDetectionResult(map, drug));
            }
            else
            {
                record.add(drug.getProductName());
                record.add(drug.getIngredientCn());
                record.add(drug.getGeneName());
                record.add(getDetectionResult(map, drug));
            }
            records.add(record.toArray(new String[record.size()]));
        }
        
        TableBookmarkContent content = new TableBookmarkContent();
        content.setBookmarkName(MessageFormat.format("DRUG_CATEGORY_{0}", i));
        content.setRecords(records);
        return content;
    }
    
    private Map<String, PharmacyTemModel> getCalPharmacyMap(Boolean isTextBookMark, TestingItemAlgorithmConfig algorithmConfig, Map<String, TestingItemLocusEvaluateResult> map)
    {
        String rs4244285 = "rs4244285";
        String rs4986893 = "rs4986893";
        int levelFour = 4;
        int levelFive = 5;
        
        Map<String, PharmacyTemModel> modelMap = new HashMap<>();
        List<PharmacyTemModel> pharmacyTemModels = new ArrayList<>();
        List<PharmacyTemModel> didTemModels = new ArrayList<>();
        TestingItem testingItem = algorithmConfig.getTestingItem();
        
        Map<String, TestingItemLocusEvaluateConfig> locusConfigMap = algorithmConfig.getLocusConfig();
        
        for (Map.Entry<String, TestingItemLocusEvaluateConfig> locusConfig : locusConfigMap.entrySet())
        {
            TestingItemLocusEvaluateResult result = map.get(locusConfig.getKey());
            String locusName = result.getLocus().getName();
            
            int levelForFour = ItemLevelEvaluator.getLevelInterval(testingItem, result.getFactor(), levelFour);
            int levelForFive = ItemLevelEvaluator.getLevelInterval(testingItem, result.getFactor(), levelFive);
            PharmacyTemModel model =
                new PharmacyTemModel(result.getLocus().getName(), result.getGenetype(), resultMap.get(levelForFour), doseMap.get(levelForFive), levelForFour);
            
            if (rs4244285.equals(locusName) || rs4986893.equals(locusName))
            {
                pharmacyTemModels.add(model);
            }
            else if (drugInducedDeafnessGeneses.contains(locusName))
            {
                PharmacyTemModel temModel = new PharmacyTemModel(result.getLocus().getName(), result.getGenetype(),
                    DictUtils.getDidResultMap().get(levelForFour), DictUtils.getDidIllustrateMap().get(levelForFive), levelForFour);
                didTemModels.add(temModel);
                if (isTextBookMark)
                {
                    textBookmarkContent.add(
                        new TextBookmarkContent(MessageFormat.format("GENE_{0}_LOCUS_{1}_RESULT", "MTRNR1", temModel.getLocusName()), temModel.getResult()));
                }
            }
            else
            {
                modelMap.put(result.getLocus().getGeneName(), model);
            }
        }
        
        putWorstModel("CYP2C19", pharmacyTemModels, modelMap, false);
        //药物性耳聋项目
        if ("DID".equals(testingItem.getCode()))
        {
            putWorstModel("MTRNR1", didTemModels, modelMap, true);
        }
        
        return modelMap;
    }
    
    private void putWorstModel(String geneName, List<PharmacyTemModel> pharmacyTemModels, Map<String, PharmacyTemModel> modelMap, boolean reverse)
    {
        Comparator comparator = Comparator.comparingInt(PharmacyTemModel::getLevel);
        Optional<PharmacyTemModel> first = pharmacyTemModels.stream().sorted(reverse ? comparator.reversed() : comparator).findFirst();
        first.ifPresent(x -> modelMap.put(geneName, x));
    }
    
    private String getDetectionResult(Map<String, PharmacyTemModel> map, Drug drug)
    {
        List<Integer> list = new ArrayList<>();
        
        for (String geneName : drug.getGeneName().split(","))
        {
            if (StringUtils.isNotEmpty(geneName))
            {
                Optional.ofNullable(map.get(geneName)).ifPresent(x -> list.add(x.getLevel()));
            }
        }
        
        if (CollectionUtils.isEmpty(list))
        {
            return null;
        }
        
        Integer min = Collections.min(list);
        
        for (Map.Entry<String, PharmacyTemModel> entry : map.entrySet())
        {
            if (min.equals(entry.getValue().getLevel()))
            {
                return entry.getValue().getDose();
            }
        }
        return null;
    }
    
    private List<Dict> getDicts()
    {
        DictSearcher searcher = new DictSearcher();
        searcher.setCategory(DICT_CATEGORY);
        List<Dict> dicts = dictService.findList(searcher);
        
        if (CollectionUtils.isEmpty(dicts))
        {
            throw new ServiceException("没有配置用药类型的字典项");
        }
        return dicts;
    }
    
}
