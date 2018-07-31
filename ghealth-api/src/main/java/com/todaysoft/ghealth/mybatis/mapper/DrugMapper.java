package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.Drug;
import com.todaysoft.ghealth.mybatis.model.DrugGene;
import com.todaysoft.ghealth.mybatis.searcher.DrugSearcher;

import java.util.List;

public interface DrugMapper
{
    int count(DrugSearcher searcher);
    
    List<Drug> search(DrugSearcher searcher);
    
    void create(Drug data);
    
    void createDrugGene(DrugGene data);
    
    Drug get(String id);
    
    void modify(Drug data);

    List<Drug> getDrugByCategory(DrugSearcher searcher);

    List<String> getGeneName(String id);

    List<String> getGeneId(String id);

    void deleteDrugGene(DrugGene drugGene);
}
