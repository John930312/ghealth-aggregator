package com.todaysoft.ghealth.portal.agcy.facade;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.Dict;
import com.todaysoft.ghealth.mgmt.request.QueryDictRequest;
import com.todaysoft.ghealth.mybatis.searcher.DictSearcher;
import com.todaysoft.ghealth.service.IDictService;
import com.todaysoft.ghealth.service.wrapper.DictWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DictFacade
{
    @Autowired
    private IDictService dictService;
    
    @Autowired
    private DictWrapper dictWrapper;
    
    public ListResponse<Dict> getDictsByCategory(QueryDictRequest request)
    {
        DictSearcher searcher = new DictSearcher();
        BeanUtils.copyProperties(request, searcher);
        return new ListResponse<Dict>(dictWrapper.wrap(dictService.findList(searcher)));
    }
    
    public ObjectResponse<Dict> getDictByCategoryAndValue(QueryDictRequest request)
    {
        DictSearcher searcher = new DictSearcher();
        BeanUtils.copyProperties(request, searcher);
        return new ObjectResponse<Dict>(dictWrapper.wrap(dictService.findByCategoryAndValue(searcher)));
    }
}
