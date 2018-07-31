package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Dict;
import com.todaysoft.ghealth.mgmt.request.MaintainDictRequest;
import com.todaysoft.ghealth.mgmt.request.QueryDictRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.DictMgmtFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mgmt/dict")
public class DictMgmtController
{
    @Autowired
    private DictMgmtFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<Dict> pager(@RequestBody QueryDictRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/details")
    public ObjectResponse<Dict> details(@RequestBody QueryDetailsRequest request)
    {
        return facade.get(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainDictRequest request)
    {
        facade.modify(request);
    }
    
    @RequestMapping("/change")
    public void change(@RequestBody MaintainDictRequest request)
    {
        facade.change(request);
    }
    
    @RequestMapping("/getDictsByCategory")
    public ListResponse<Dict> getDictsByCategory(@RequestBody QueryDictRequest request)
    {
        return facade.getDictsByCategory(request);
    }
    
    @RequestMapping("/getDictByCategoryAndValue")
    public ObjectResponse<Dict> getDictByCategoryAndValue(@RequestBody QueryDictRequest request)
    {
        return facade.findByCategoryAndValue(request);
    }
}
