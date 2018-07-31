package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.dto.LocusDTO;
import com.todaysoft.ghealth.mgmt.request.MaintainLocusRequest;
import com.todaysoft.ghealth.mgmt.request.QueryLocusByNamesRequest;
import com.todaysoft.ghealth.mgmt.request.QueryLocusRequest;
import com.todaysoft.ghealth.mybatis.model.Locus;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.LocusSearcher;
import com.todaysoft.ghealth.portal.mgmt.MgmtErrorCode;
import com.todaysoft.ghealth.portal.mgmt.facade.wrapper.LocusWrapper;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.ILocusService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.wrapper.PagerWrapper;
import com.todaysoft.ghealth.utils.IdGen;

@Component
public class LocusFacade
{
    @Autowired
    private LocusWrapper locusWrapper;
    
    @Autowired
    private ILocusService locusService;
    
    @Autowired
    private IAccountService accountService;
    
    public PagerResponse<LocusDTO> pager(QueryLocusRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        LocusSearcher searcher = new LocusSearcher();
        searcher.setName(request.getName());
        searcher.setGeneName(request.getGeneName());
        PagerQueryer<Locus> queryer = new PagerQueryer<Locus>(locusService);
        Pager<Locus> pager = queryer.query(searcher, pageNo, pageSize);
        return new PagerResponse<LocusDTO>(new PagerWrapper<Locus, LocusDTO>(locusWrapper).wrap(pager));
    }
    
    public ListResponse<LocusDTO> list(QueryLocusByNamesRequest request)
    {
        List<Locus> orders = locusService.list(request.getNames());
        return new ListResponse<LocusDTO>(locusWrapper.wrap(orders));
    }
    
    public ObjectResponse<LocusDTO> get(MaintainLocusRequest request)
    {
        Locus locus = locusService.get(request.getId());
        return new ObjectResponse<LocusDTO>(locusWrapper.wrap(locus));
    }
    
    public void create(MaintainLocusRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        Locus entity = new Locus();
        entity.setId(IdGen.uuid());
        entity.setName(request.getName());
        entity.setGeneId(request.getGeneId());
        entity.setCreateTime(new Date());
        entity.setCreatorName(account.getName());
        entity.setDeleted(false);
        locusService.create(entity);
    }
    
    public void modify(MaintainLocusRequest request)
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
        
        Locus entity = locusService.get(request.getId());
        entity.setName(request.getName());
        entity.setGeneId(request.getGeneId());
        entity.setUpdatorName(account.getName());
        entity.setUpdateTime(new Date());
        locusService.modify(entity);
    }
    
    public void delete(MaintainLocusRequest request)
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
        
        Locus entity = locusService.get(request.getId());
        entity.setDeleted(true);
        entity.setDeletorName(account.getName());
        entity.setDeleteTime(new Date());
        locusService.modify(entity);
    }
    
    public ObjectResponse<Boolean> isNameUnique(MaintainLocusRequest request)
    {
        boolean unique = locusService.isNameUnique(request.getId(), request.getName());
        return new ObjectResponse<Boolean>(unique);
    }
}
