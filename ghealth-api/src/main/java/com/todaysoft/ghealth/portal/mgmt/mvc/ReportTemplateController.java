package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.ReportTemplateDTO;
import com.todaysoft.ghealth.mgmt.product.report.template.ReportTemplateMaintainRequest;
import com.todaysoft.ghealth.mgmt.request.QueryReportTemplateRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.ReportTemplateFacade;

@RestController
@RequestMapping("/mgmt/reportTemplate")
public class ReportTemplateController
{
    @Autowired
    private ReportTemplateFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<ReportTemplateDTO> pager(@RequestBody QueryReportTemplateRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/get")
    public ObjectResponse<ReportTemplateDTO> get(@RequestBody QueryDetailsRequest request)
    {
        return facade.get(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody ReportTemplateMaintainRequest request)
    {
        facade.create(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody ReportTemplateMaintainRequest request)
    {
        facade.modify(request);
    }
    
    @RequestMapping("/delete")
    public void delete(@RequestBody ReportTemplateMaintainRequest request)
    {
        facade.delete(request);
    }
}
