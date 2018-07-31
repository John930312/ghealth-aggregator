package com.todaysoft.ghealth.portal.mgmt.facade;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Dict;
import com.todaysoft.ghealth.mgmt.request.MaintainDictRequest;
import com.todaysoft.ghealth.mgmt.request.QueryDictRequest;
import com.todaysoft.ghealth.mybatis.model.DictForm;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.DictSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IDictService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.DictWrapper;
import com.todaysoft.ghealth.utils.IdGen;

@Component
public class DictMgmtFacade
{
    @Autowired
    private IDictService service;
    
    @Autowired
    private DictWrapper wrapper;
    
    @Autowired
    private IAccountService accountService;
    
    public PagerResponse<Dict> pager(@RequestBody QueryDictRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        
        DictSearcher searcher = new DictSearcher();
        searcher.setDictText(request.getDictText());
        
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Dict> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Dict>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.Dict> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Dict> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<Dict>(result);
    }
    
    public ObjectResponse<Dict> get(QueryDetailsRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Dict dict = service.get(request.getId());
        Dict data = wrapper.wrap(dict);
        return new ObjectResponse<Dict>(data);
    }
    
    public void modify(MaintainDictRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        service.deleteByCategory(request.getCategory());
        for (int i = 0; i < request.getDicts().size(); i++)
        {
            
            DictForm dictForm = new DictForm();
            dictForm.setCategory(request.getCategory());
            dictForm.setDictText(request.getDicts().get(i).getDictText());
            dictForm.setDictValue(request.getDicts().get(i).getDictValue());
            dictForm.setId(IdGen.uuid());
            dictForm.setParentId(request.getId());
            dictForm.setEditable(request.getEditable());
            dictForm.setSort(i + 1);
            dictForm.setEditable(request.getEditable());
            service.create(dictForm);
        }
    }
    
    public void change(MaintainDictRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        for (int i = 0; i < request.getDicts().size(); i++)
        {
            DictForm dictForm = new DictForm();
            dictForm.setId(request.getDicts().get(i).getId());
            dictForm.setDictText(request.getDicts().get(i).getDictText());
            service.modify(dictForm);
        }
        
    }
    
    public ListResponse<Dict> getDictsByCategory(QueryDictRequest request)
    {
        DictSearcher searcher = new DictSearcher();
        BeanUtils.copyProperties(request, searcher);
        return new ListResponse<>(wrapper.wrap(service.findList(searcher)));
    }
    
    public ObjectResponse<Dict> findByCategoryAndValue(QueryDictRequest request)
    {
        DictSearcher searcher = new DictSearcher();
        BeanUtils.copyProperties(request, searcher);
        Dict wrap = wrapper.wrap(service.findByCategoryAndValue(searcher));
        return new ObjectResponse<Dict>(wrap);
    }
    
}
