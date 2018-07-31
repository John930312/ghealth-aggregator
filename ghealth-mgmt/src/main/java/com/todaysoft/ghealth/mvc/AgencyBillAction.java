package com.todaysoft.ghealth.mvc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.ghealth.model.agency.AgencyBill;
import com.todaysoft.ghealth.model.agency.AgencyBillSearcher;
import com.todaysoft.ghealth.service.IAgencyBillService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import com.todaysoft.ghealth.utils.ExportExcel;

@Controller
@RequestMapping("/agencyBill")
public class AgencyBillAction extends BaseAction
{
    @Autowired
    private IAgencyBillService service;
    
    private String url = "/agencyBill/list.jsp";
    
    @RequestMapping("/list.jsp")
    public String list(AgencyBillSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<AgencyBill> pagination = service.searcher(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "agencyBill/agencyBill_list";
    }
    
    @RequestMapping("/agencyBillDownload.jsp")
    public void agencyBillDownload(AgencyBillSearcher searcher, HttpServletResponse response, ModelMap model, HttpSession session)
    {
        List<AgencyBill> list = service.list(searcher);
        String fileName = "账单列表"+"_"+new SimpleDateFormat("yyyyMMdd").format(new Date()).toString()+"_" + new SimpleDateFormat("HHmm").format(new Date()).toString()+".xlsx";
        try
        {
            new ExportExcel(null, AgencyBill.class).setDataList(list).write(response, fileName).dispose();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
