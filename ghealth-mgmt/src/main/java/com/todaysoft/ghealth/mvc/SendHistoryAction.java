package com.todaysoft.ghealth.mvc;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.ghealth.model.signInHistory.SignInHistory;
import com.todaysoft.ghealth.model.signInHistory.SignInHistorySearcher;
import com.todaysoft.ghealth.service.ISendHistoryService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/sendHistory")
public class SendHistoryAction extends BaseAction
{
    @Autowired
    private ISendHistoryService service;
    
    @RequestMapping("/list.jsp")
    public String list(SignInHistorySearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<SignInHistory> pagination = service.search(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-searcher", searcher);
        return "sendHistory/sendHistory_list";
    }
    
    @RequestMapping(value = "/details.jsp")
    public String detail(String id, ModelMap model, HttpSession session)
    {
        List<SignInHistory> signInHistoryList = service.getSearch(id);
        model.addAttribute("signInHistoryList", signInHistoryList);
        return "sendHistory/sendHistory_detail";
    }
}
