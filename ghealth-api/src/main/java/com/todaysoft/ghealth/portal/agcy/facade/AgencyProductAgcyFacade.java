package com.todaysoft.ghealth.portal.agcy.facade;

import java.util.List;
import java.util.Objects;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.mybatis.mapper.AgencyAccountMapper;
import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.agcy.request.QueryAgencyProductsRequest;
import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyProduct;
import com.todaysoft.ghealth.base.response.model.AgencyProductDetails;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.AgencyProductSearcher;

import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IAgencyProductService;

import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.AgencyProductWrapper;

@Component
public class AgencyProductAgcyFacade
{
    
    @Autowired
    private AgencyProductWrapper agencyProductWrapper;
    
    @Autowired
    private IAgencyProductService agencyProductService;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private AgencyAccountMapper agencyAccountMapper;
    
    public PagerResponse<AgencyProduct> pager(QueryAgencyProductsRequest request)
    {
        String agencyId;
        if (request.isLogin())
        {
            AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
            
            if (null == account || StringUtils.isEmpty(account.getAgencyId()))
            {
                throw new IllegalStateException();
            }
            agencyId = account.getAgencyId();
        }
        else
        {
            agencyId = request.getAgencyId();
        }
        
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        
        AgencyProductSearcher searcher = new AgencyProductSearcher();
        searcher.setAgencyId(agencyId);
        searcher.setProductCode(request.getCode());
        searcher.setProductName(request.getName());
        
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyProduct> queryer =
            new PagerQueryer<com.todaysoft.ghealth.mybatis.model.AgencyProduct>(agencyProductService);
        Pager<com.todaysoft.ghealth.mybatis.model.AgencyProduct> pager = queryer.query(searcher, pageNo, pageSize);
        
        Pager<AgencyProduct> result =
            Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), agencyProductWrapper.wrap(pager.getRecords()));
        return new PagerResponse<AgencyProduct>(result);
    }
    
    public ObjectResponse<AgencyProductDetails> details(QueryDetailsRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new IllegalArgumentException();
        }
        
        com.todaysoft.ghealth.mybatis.model.AgencyProduct agencyProduct = agencyProductService.get(request.getId());
        
        if (null == agencyProduct)
        {
            return new ObjectResponse<AgencyProductDetails>(null);
        }
        
        return new ObjectResponse<AgencyProductDetails>(agencyProductWrapper.wrapAgencyProductDateails(agencyProduct));
    }
    
    public ListResponse<AgencyProduct> list(QueryAgencyProductsRequest request)
    {
        AgencyProductSearcher searcher = new AgencyProductSearcher();
        if (!StringUtils.isEmpty(request.getAgencyId()))
        {
            searcher.setAgencyId(request.getAgencyId());
        }
        else
        {
            AgencyAccount agencyAccount = agencyAccountMapper.getAccountByAccountId(request.getAgencyAccountId());
            searcher.setAgencyId(agencyAccount.getAgencyId());
        }
        List<com.todaysoft.ghealth.mybatis.model.AgencyProduct> list = agencyProductService.list(searcher);
        return new ListResponse<AgencyProduct>(agencyProductWrapper.wrap(list));
    }
    
    public ObjectResponse<Boolean> isUniqueCode(QueryAgencyProductsRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        String data = agencyProductService.getIdByCode(request.getCode(), account.getAgencyId());
        
        return new ObjectResponse<>(!Objects.isNull(data));
    }
}
