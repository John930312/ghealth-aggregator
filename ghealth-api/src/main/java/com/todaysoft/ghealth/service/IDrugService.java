package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.Drug;
import com.todaysoft.ghealth.mybatis.model.DrugGene;
import com.todaysoft.ghealth.mybatis.searcher.DrugSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.List;

public interface IDrugService extends PagerQueryHandler<Drug>
{
    void create(Drug data);
    
    void createDrugGene(DrugGene data);
    
    Drug get(String id);
    
    void modify(Drug data);
    
    List<Drug> getDrugByCategory(DrugSearcher searchery);
    
    String getGeneName(String id);
    
    String getGeneId(String id);
    
    void deleteDrugGene(DrugGene drugGene);
    
    boolean isIngredientCnUnique(String id, String ingredientCn);
    
}
