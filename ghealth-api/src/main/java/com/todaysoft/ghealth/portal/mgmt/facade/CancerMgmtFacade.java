package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Cancer;
import com.todaysoft.ghealth.mgmt.request.MaintainCancerRequest;
import com.todaysoft.ghealth.mgmt.request.QueryCancersRequest;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.CancerSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.ICancerService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.CancerWrapper;
import com.todaysoft.ghealth.utils.IdGen;

@Component
public class CancerMgmtFacade
{
    @Autowired
    private CancerWrapper cancerWrapper;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private ICancerService service;
    
    public PagerResponse<Cancer> pager(@RequestBody QueryCancersRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        
        CancerSearcher searcher = new CancerSearcher();
        searcher.setCancerName(request.getCancerName());
        
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Cancer> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Cancer>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.Cancer> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Cancer> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), cancerWrapper.wrap(pager.getRecords()));
        return new PagerResponse<Cancer>(result);
    }
    
    public ObjectResponse<Cancer> get(QueryDetailsRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Cancer cancer = service.get(request.getId());
        Cancer data = cancerWrapper.wrap(cancer);
        return new ObjectResponse<Cancer>(data);
    }
    
    public void delete(MaintainCancerRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Cancer cancer = service.get(request.getId());
        cancer.setDeleted(true);
        cancer.setDeletorName(account.getName());
        cancer.setDeleteTime(new Date());
        service.modify(cancer);
    }
    
    public void create(MaintainCancerRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Cancer cancer = new com.todaysoft.ghealth.mybatis.model.Cancer();
        cancer.setId(IdGen.uuid());
        cancer.setDescription(request.getDescription());
        cancer.setName(request.getName());
        cancer.setRiskfemale(request.getRiskfemale());
        cancer.setRiskmale(request.getRiskmale());
        cancer.setDeleted(false);
        cancer.setCreatorName(account.getName());
        cancer.setCreateTime(new Date());
        service.create(cancer);
    }
    
    public void modify(MaintainCancerRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Cancer cancer = new com.todaysoft.ghealth.mybatis.model.Cancer();
        BeanUtils.copyProperties(request, cancer);
        cancer.setUpdatorName(account.getName());
        cancer.setUpdateTime(new Date());
        service.modify(cancer);
    }

    public ObjectResponse<Boolean> isNameUnique(MaintainCancerRequest request)
    {
        boolean unique = service.isNameUnique(request.getId(), request.getName());
        return new ObjectResponse<Boolean>(unique);
    }
}
