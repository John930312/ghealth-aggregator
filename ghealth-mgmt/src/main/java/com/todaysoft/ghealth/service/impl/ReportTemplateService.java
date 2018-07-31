package com.todaysoft.ghealth.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.document.generate.sdk.DocumentGenerateSDK;
import com.todaysoft.document.generate.sdk.SDKException;
import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.ReportTemplateDTO;
import com.todaysoft.ghealth.mgmt.product.report.template.ReportTemplateMaintainRequest;
import com.todaysoft.ghealth.mgmt.request.QueryReportTemplateRequest;
import com.todaysoft.ghealth.model.reportTemplate.ReportTemplate;
import com.todaysoft.ghealth.model.reportTemplate.ReportTemplateForm;
import com.todaysoft.ghealth.model.reportTemplate.ReportTemplateSearcher;
import com.todaysoft.ghealth.service.IReportTemplateService;
import com.todaysoft.ghealth.service.wrapper.ReportTemplateWrapper;
import com.todaysoft.ghealth.support.Pagination;
import com.todaysoft.ghealth.support.ServiceException;

@Service
public class ReportTemplateService implements IReportTemplateService
{
    private static Logger log = LoggerFactory.getLogger(ReportTemplateService.class);
    
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private DocumentGenerateConfig generateConfig;
    
    @Autowired
    private ReportTemplateWrapper wrapper;
    
    @Override
    public Pagination<ReportTemplate> pagination(ReportTemplateSearcher searcher, int pageNo, int pageSize)
    {
        QueryReportTemplateRequest request = new QueryReportTemplateRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<ReportTemplateDTO> response = gateway.request("/mgmt/reportTemplate/pager", request, new ParameterizedTypeReference<PagerResponse<ReportTemplateDTO>>()
        {
        });
        Pager<ReportTemplateDTO> pager = response.getData();
        
        Pagination<ReportTemplate> pagination = new Pagination<ReportTemplate>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        
        if (CollectionUtils.isEmpty(pager.getRecords()))
        {
            return pagination;
        }
        pagination.setRecords(wrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public ReportTemplate get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        
        ObjectResponse<ReportTemplateDTO> response = gateway.request("/mgmt/reportTemplate/get", request, new ParameterizedTypeReference<ObjectResponse<ReportTemplateDTO>>()
        {
        });
        ReportTemplate reportTemplate = new ReportTemplate();
        if (null == response.getData())
        {
            return reportTemplate;
        }
        return wrapper.wrap(response.getData());
    }
    
    @Override
    public void create(ReportTemplateForm data)
    {
        if (!data.isCustomized())
        {
            data.setAgencyId(null);
        }
        
        DocumentGenerateSDK sdk = new DocumentGenerateSDK(generateConfig.getEndpoint(), generateConfig.getAccessKeyId(), generateConfig.getAccessKeySecret());
        
        String templateKey = null;
        
        try
        {
            templateKey =
                sdk.createTemplate(generateConfig.getAppid(), data.getName(), data.getFile().getInputStream(), data.getFile().getOriginalFilename(), data.getDescription());
            
            if (StringUtils.isEmpty(templateKey))
            {
                throw new ServiceException(ErrorCode.REPORT_TEMPLATE_CREATE_TSDG_ERROR);
            }
            
            ReportTemplateMaintainRequest request = new ReportTemplateMaintainRequest();
            request.setTemplateName(data.getName());
            request.setProductId(data.getProductId());
            request.setCustomized(data.isCustomized());
            request.setAgencyId(data.getAgencyId());
            request.setDescription(data.getDescription());
            request.setTemplateKey(templateKey);
            gateway.request("/mgmt/reportTemplate/create", request);
        }
        catch (Exception e)
        {
            if (!StringUtils.isEmpty(templateKey))
            {
                try
                {
                    sdk.deleteTemplate(templateKey);
                }
                catch (SDKException ex)
                {
                    log.error(ex.getMessage(), ex);
                }
            }
            
            if (e instanceof ServiceException)
            {
                throw (ServiceException)e;
            }
            else
            {
                log.error(e.getMessage(), e);
                throw new ServiceException(ErrorCode.UNDEFINED_ERROR);
            }
        }
    }
    
    @Override
    public void modify(ReportTemplateForm data)
    {
        if (!data.isCustomized())
        {
            data.setAgencyId(null);
        }
        
        if (null == data.getFile() || data.getFile().isEmpty())
        {
            modifyAsNofileUpload(data);
        }
        else
        {
            modifyAsFileUpload(data);
        }
    }
    
    private void modifyAsNofileUpload(ReportTemplateForm data)
    {
        ReportTemplateMaintainRequest request = new ReportTemplateMaintainRequest();
        request.setId(data.getId());
        request.setTemplateName(data.getName());
        request.setProductId(data.getProductId());
        request.setCustomized(data.isCustomized());
        request.setAgencyId(data.getAgencyId());
        request.setDescription(data.getDescription());
        gateway.request("/mgmt/reportTemplate/modify", request);
    }
    
    private void modifyAsFileUpload(ReportTemplateForm data)
    {
        DocumentGenerateSDK sdk = new DocumentGenerateSDK(generateConfig.getEndpoint(), generateConfig.getAccessKeyId(), generateConfig.getAccessKeySecret());
        
        String templateKey = null;
        
        try
        {
            templateKey =
                sdk.createTemplate(generateConfig.getAppid(), data.getName(), data.getFile().getInputStream(), data.getFile().getOriginalFilename(), data.getDescription());
            
            if (StringUtils.isEmpty(templateKey))
            {
                throw new ServiceException(ErrorCode.REPORT_TEMPLATE_CREATE_TSDG_ERROR);
            }
            
            ReportTemplate template = this.get(data.getId());
            String oldTemplateKey = template.getTsdgKey();
            
            ReportTemplateMaintainRequest request = new ReportTemplateMaintainRequest();
            request.setId(data.getId());
            request.setTemplateName(data.getName());
            request.setProductId(data.getProductId());
            request.setCustomized(data.isCustomized());
            request.setAgencyId(data.getAgencyId());
            request.setDescription(data.getDescription());
            request.setTemplateKey(templateKey);
            gateway.request("/mgmt/reportTemplate/modify", request);
            
            // 修改成功之后删除旧模板
            if (!StringUtils.isEmpty(oldTemplateKey))
            {
                try
                {
                    sdk.deleteTemplate(oldTemplateKey);
                }
                catch (SDKException ex)
                {
                    log.error(ex.getMessage(), ex);
                }
            }
        }
        catch (Exception e)
        {
            if (!StringUtils.isEmpty(templateKey))
            {
                try
                {
                    sdk.deleteTemplate(templateKey);
                }
                catch (SDKException ex)
                {
                    log.error(ex.getMessage(), ex);
                }
            }
            
            if (e instanceof ServiceException)
            {
                throw (ServiceException)e;
            }
            else
            {
                log.error(e.getMessage(), e);
                throw new ServiceException(ErrorCode.UNDEFINED_ERROR);
            }
        }
    }
    
    @Override
    public void delete(String id)
    {
        ReportTemplate template = get(id);
        
        ReportTemplateMaintainRequest request = new ReportTemplateMaintainRequest();
        request.setId(id);
        gateway.request("/mgmt/reportTemplate/delete", request);
        
        DocumentGenerateSDK sdk = new DocumentGenerateSDK(generateConfig.getEndpoint(), generateConfig.getAccessKeyId(), generateConfig.getAccessKeySecret());
        
        try
        {
            sdk.deleteTemplate(template.getTsdgKey());
        }
        catch (SDKException e)
        {
            log.error(e.getMessage(), e);
        }
    }
}
