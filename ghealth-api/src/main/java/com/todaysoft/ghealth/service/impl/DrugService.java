package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.DrugMapper;
import com.todaysoft.ghealth.mybatis.model.Drug;
import com.todaysoft.ghealth.mybatis.model.DrugGene;
import com.todaysoft.ghealth.mybatis.searcher.DrugSearcher;
import com.todaysoft.ghealth.service.IDrugService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class DrugService implements IDrugService{

    @Autowired(required = false)
    private DrugMapper mapper;


    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof DrugSearcher))
        {
            throw new IllegalArgumentException();
        }

        return mapper.count((DrugSearcher)searcher);
    }

    @Override
    public List<Drug> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof DrugSearcher))
        {
            throw new IllegalArgumentException();
        }

        if (limit > 0)
        {
            DrugSearcher tis = (DrugSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }

        return mapper.search((DrugSearcher)searcher);
    }

    @Override
    @Transactional
    public void create(Drug data)
    {
        mapper.create(data);
    }

    @Override
    @Transactional
    public void createDrugGene(DrugGene data)
    {
        mapper.createDrugGene(data);
    }

    @Override
    public Drug get(String id)
    {
        return mapper.get(id);
    }

    @Override
    @Transactional
    public void modify(Drug data)
    {
        mapper.modify(data);
    }

    @Override
    public List<Drug> getDrugByCategory(DrugSearcher searcher)
    {
        return mapper.getDrugByCategory(searcher);
    }

    @Override
    public String getGeneName(String id){
       return StringUtils.join(mapper.getGeneName(id),",");

    }


    @Override
    public String getGeneId(String id){
        return StringUtils.join(mapper.getGeneId(id),",");

    }


    @Override
    @Transactional
    public void deleteDrugGene(DrugGene drugGene)
    {
        mapper.deleteDrugGene(drugGene);
    }

    @Override
    public boolean isIngredientCnUnique(String id, String ingredientCn)
    {
        DrugSearcher searcher = new DrugSearcher();
        searcher.setIngredientCn(ingredientCn);
        searcher.setIngredientCnExactMatches(true);

        if (!StringUtils.isEmpty(id))
        {
            searcher.setExcludeKeys(Collections.singleton(id));
        }

        int count = mapper.count(searcher);
        return count == 0;
    }

}
