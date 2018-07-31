package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.drug.Drug;
import com.todaysoft.ghealth.model.drug.DrugSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface IDrugService {
    Pagination<Drug> pagination(DrugSearcher searcher, int pageNo, int pageSize);

    void create(Drug data);

    Drug get(String id);

    void modify(Drug data);

    void delete(String id);

    boolean isIngredientCnUnique(String id, String ingredientCn);
}
