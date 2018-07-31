package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.base.response.model.Dict;

import java.util.List;

public interface IDictService
{
    List<Dict> getDictsByCategory(String category);
    
    Dict getDictByCategoryAndValue(String category, String dictValue);
}
