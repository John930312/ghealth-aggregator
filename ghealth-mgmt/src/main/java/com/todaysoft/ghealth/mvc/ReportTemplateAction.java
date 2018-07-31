package com.todaysoft.ghealth.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import com.todaysoft.ghealth.model.reportTemplate.ReportTemplate;
import com.todaysoft.ghealth.model.reportTemplate.ReportTemplateForm;
import com.todaysoft.ghealth.model.reportTemplate.ReportTemplateSearcher;
import com.todaysoft.ghealth.mvc.view.ReportTemplateDownloadView;
import com.todaysoft.ghealth.service.IReportTemplateService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/reportTemplate")
public class ReportTemplateAction extends BaseAction
{
    @Autowired
    private ReportTemplateDownloadView templateDownloadView;
    
    @Autowired
    private IReportTemplateService reportTemplateService;
    
    private String url = "/reportTemplate/list.jsp";
    
    @RequestMapping("/list.jsp")
    public String pagination(ReportTemplateSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<ReportTemplate> pagination = reportTemplateService.pagination(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "reportTemplate/reportTemplate_list";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create()
    {
        return "reportTemplate/reportTemplate_form";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(ReportTemplateForm data, ModelMap model, HttpServletRequest request, HttpSession session)
    {
        reportTemplateService.create(data);
        return redirectList(model, session, url);
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap model)
    {
        ReportTemplate data = reportTemplateService.get(id);
        model.addAttribute("data", data);
        return "reportTemplate/reportTemplate_form";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(ReportTemplateForm data, ModelMap model, HttpServletRequest request, HttpSession session)
    {
        reportTemplateService.modify(data);
        return redirectList(model, session, url);
    }
    
    @RequestMapping(value = "/download.jsp", method = RequestMethod.GET)
    public View download(String id, ModelMap model)
    {
        model.addAttribute("id", id);
        return templateDownloadView;
    }
    
    @RequestMapping(value = "/delete.jsp", method = RequestMethod.GET)
    public String delete(String id, ModelMap model, HttpSession session)
    {
        reportTemplateService.delete(id);
        return redirectList(model, session, url);
    }
}
