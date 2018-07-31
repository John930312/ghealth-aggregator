package com.todaysoft.ghealth.portal.agcy.mvc;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.Dict;
import com.todaysoft.ghealth.mgmt.request.QueryDictRequest;
import com.todaysoft.ghealth.portal.agcy.facade.DictFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agcy/dict")
public class DictController
{
    @Autowired
    private DictFacade dictFacade;
    
    @RequestMapping("/getDictsByCategory")
    public ListResponse<Dict> getDictsByCategory(@RequestBody QueryDictRequest request)
    {
        return dictFacade.getDictsByCategory(request);
    }
    
    @RequestMapping("/getDictByCategoryAndValue")
    public ObjectResponse<Dict> getDictByCategoryAndValue(@RequestBody QueryDictRequest request)
    {
        return dictFacade.getDictByCategoryAndValue(request);
    }
}
