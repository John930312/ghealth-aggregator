package com.todaysoft.ghealth.portal.mgmt.facade;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.dto.TestingItemLocusDTO;
import com.todaysoft.ghealth.mgmt.request.MaintainTestingItemLocusRequest;
import com.todaysoft.ghealth.mgmt.request.QueryItemLocusRequest;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.model.TestingItemLocus;
import com.todaysoft.ghealth.mybatis.searcher.ItemLocusSearcher;
import com.todaysoft.ghealth.portal.mgmt.MgmtErrorCode;
import com.todaysoft.ghealth.portal.mgmt.facade.wrapper.TestingItemLocusWrapper;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IItemLocusService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.wrapper.PagerWrapper;
import com.todaysoft.ghealth.utils.IdGen;

@Component
public class ItemLocusFacade
{
    @Autowired
    private IItemLocusService itemLocusService;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private TestingItemLocusWrapper testingItemLocusWrapper;
    
    public PagerResponse<TestingItemLocusDTO> pager(QueryItemLocusRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        ItemLocusSearcher searcher = new ItemLocusSearcher();
        BeanUtils.copyProperties(request, searcher);
        PagerQueryer<TestingItemLocus> queryer = new PagerQueryer<TestingItemLocus>(itemLocusService);
        Pager<TestingItemLocus> pager = queryer.query(searcher, pageNo, pageSize);
        return new PagerResponse<TestingItemLocusDTO>(new PagerWrapper<TestingItemLocus, TestingItemLocusDTO>(testingItemLocusWrapper).wrap(pager));
    }
    
    public ObjectResponse<TestingItemLocusDTO> get(MaintainTestingItemLocusRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new ServiceException(MgmtErrorCode.API_ILLEGAL_ARGUMENT);
        }
        
        TestingItemLocus entity = itemLocusService.get(request.getId());
        return new ObjectResponse<TestingItemLocusDTO>(testingItemLocusWrapper.wrap(entity));
    }
    
    public void create(MaintainTestingItemLocusRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        TestingItemLocus entity = new TestingItemLocus();
        entity.setId(IdGen.uuid());
        BeanUtils.copyProperties(request, entity, "id");
        itemLocusService.create(entity);
    }
    
    public void modify(MaintainTestingItemLocusRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new ServiceException(MgmtErrorCode.API_ILLEGAL_ARGUMENT);
        }
        
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        TestingItemLocus entity = itemLocusService.get(request.getId());
        entity.setLocusId(request.getLocusId());
        entity.setTestingItemId(request.getTestingItemId());
        entity.setInfluenceFactors(request.getInfluenceFactors());
        itemLocusService.modify(entity);
    }
    
    public void delete(MaintainTestingItemLocusRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new ServiceException(MgmtErrorCode.API_ILLEGAL_ARGUMENT);
        }
        
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        itemLocusService.delete(request.getId());
    }
}
