package com.todaysoft.ghealth.mvc;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import com.todaysoft.ghealth.model.email.Email;
import com.todaysoft.ghealth.service.IEmailService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/email")
public class EmailAction extends BaseAction{

    @Autowired

    private IEmailService service;

    @RequestMapping("/list.jsp")
    public String list( PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Email> pagination = service.searcher(pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        return "email/email_list";
    }

    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        return "email/email_form";
    }

    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(Email data, ModelMap model, HttpSession session)
    {
        service.create(data);
        return redirectList(model, session, "/email/list.jsp");
    }


    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap model)
    {
        Email data = service.get(id);
        model.addAttribute("data", data);
        return "email/email_form";
    }

    @RequestMapping(value = "modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(Email data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session, "/email/list.jsp");
    }


    @RequestMapping(value = "delete.jsp", method = RequestMethod.GET)
    public String delete(String id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return redirectList(model, session, "/email/list.jsp");
    }
}
