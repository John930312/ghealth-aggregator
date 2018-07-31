package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.ReportTemplateDTO;
import com.todaysoft.ghealth.mgmt.product.report.template.ReportTemplateMaintainRequest;
import com.todaysoft.ghealth.mgmt.request.QueryReportTemplateRequest;
import com.todaysoft.ghealth.mybatis.model.Agency;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.model.Product;
import com.todaysoft.ghealth.mybatis.model.ReportTemplate;
import com.todaysoft.ghealth.mybatis.searcher.ReportTemplateSearcher;
import com.todaysoft.ghealth.portal.mgmt.MgmtErrorCode;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IReportTemplateService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.wrapper.ReportTemplateWrapper;
import com.todaysoft.ghealth.utils.IdGen;
import com.todaysoft.ghealth.utils.SerialNumber;

@Component
public class ReportTemplateFacade
{
    private static String SEQUENCE_NAME = "REPORT_TEMPLATE_CODE";
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IReportTemplateService reportTemplateService;
    
    @Autowired
    private ReportTemplateWrapper wrapper;
    
    @Autowired
    private SerialNumber serialNumber;
    
    public PagerResponse<ReportTemplateDTO> pager(QueryReportTemplateRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        ReportTemplateSearcher searcher = new ReportTemplateSearcher();
        BeanUtils.copyProperties(request, searcher);
        PagerQueryer<ReportTemplate> queryer = new PagerQueryer<ReportTemplate>(reportTemplateService);
        Pager<ReportTemplate> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<ReportTemplateDTO> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<ReportTemplateDTO>(result);
    }
    
    public ObjectResponse<ReportTemplateDTO> get(QueryDetailsRequest request)
    {
        ReportTemplate template = reportTemplateService.get(request.getId());
        return new ObjectResponse<ReportTemplateDTO>(wrapper.wrap(template));
    }
    
    public void create(ReportTemplateMaintainRequest request)
    {
        if (StringUtils.isEmpty(request.getTemplateName()) || StringUtils.isEmpty(request.getTemplateKey()) || StringUtils.isEmpty(request.getProductId()))
        {
            throw new ServiceException(MgmtErrorCode.API_ILLEGAL_ARGUMENT);
        }
        
        ReportTemplateSearcher searcher = new ReportTemplateSearcher();
        searcher.setProductId(request.getProductId());
        searcher.setAgencyId(request.getAgencyId());
        searcher.setCustomized(request.isCustomized());
        int count = reportTemplateService.count(searcher);
        
        if (count > 0)
        {
            throw new ServiceException(MgmtErrorCode.REPORT_TEMPLATE_CREATE_DUPLICATE);
        }
        
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        ReportTemplate data = new ReportTemplate();
        data.setId(IdGen.uuid());
        data.setName(request.getTemplateName());
        data.setCustomized(request.isCustomized());
        
        Product product = new Product();
        product.setId(request.getProductId());
        data.setProduct(product);
        
        if (request.isCustomized() && !StringUtils.isEmpty(request.getAgencyId()))
        {
            Agency agency = new Agency();
            agency.setId(request.getAgencyId());
            data.setAgency(agency);
        }
        
        data.setTsdgKey(request.getTemplateKey());
        data.setCode(serialNumber.getCode(SEQUENCE_NAME));
        data.setCreatorName(account.getName());
        data.setCreateTime(new Date());
        reportTemplateService.create(data);
    }
    
    public void modify(ReportTemplateMaintainRequest request)
    {
        if (StringUtils.isEmpty(request.getId()) || StringUtils.isEmpty(request.getTemplateName()) || StringUtils.isEmpty(request.getProductId()))
        {
            throw new ServiceException(MgmtErrorCode.API_ILLEGAL_ARGUMENT);
        }
        
        ReportTemplate entity = reportTemplateService.get(request.getId());
        
        if (null == entity)
        {
            throw new ServiceException(MgmtErrorCode.API_ILLEGAL_ARGUMENT);
        }
        
        ReportTemplateSearcher searcher = new ReportTemplateSearcher();
        searcher.setExcludeId(request.getId());
        searcher.setProductId(request.getProductId());
        searcher.setAgencyId(request.getAgencyId());
        searcher.setCustomized(request.isCustomized());
        int count = reportTemplateService.count(searcher);
        
        if (count > 0)
        {
            throw new ServiceException(MgmtErrorCode.REPORT_TEMPLATE_CREATE_DUPLICATE);
        }
        
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        entity.setName(request.getTemplateName());
        entity.setCustomized(request.isCustomized());
        
        Product product = new Product();
        product.setId(request.getProductId());
        entity.setProduct(product);
        
        if (request.isCustomized() && !StringUtils.isEmpty(request.getAgencyId()))
        {
            Agency agency = new Agency();
            agency.setId(request.getAgencyId());
            entity.setAgency(agency);
        }
        else
        {
            entity.setAgency(null);
        }
        
        if (!StringUtils.isEmpty(request.getTemplateKey()))
        {
            entity.setTsdgKey(request.getTemplateKey());
        }
        
        entity.setUpdatorName(account.getName());
        entity.setUpdateTime(new Date());
        reportTemplateService.modify(entity);
    }
    
    public void delete(ReportTemplateMaintainRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new ServiceException(MgmtErrorCode.API_ILLEGAL_ARGUMENT);
        }
        
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        ReportTemplate template = reportTemplateService.get(request.getId());
        template.setDeleted(true);
        template.setDeleteTime(new Date());
        template.setDeletorName(account.getName());
        reportTemplateService.modify(template);
    }
}
