package com.todaysoft.ghealth.mvc;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import com.todaysoft.ghealth.model.agencyAccount.AgencyAccount;
import com.todaysoft.ghealth.model.agencyAccount.AgencyAccountSearcher;
import com.todaysoft.ghealth.model.role.Role;
import com.todaysoft.ghealth.service.IAgencyAccountService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class AgencyAccountrAction extends BaseAction {

    @Autowired
    private IAgencyAccountService service;

    @RequestMapping("/list.jsp")
    public String list(AgencyAccountSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<AgencyAccount> pagination = service.searcher(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "/user/user_list";
    }

    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        return "user/user_create";
    }

    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(AgencyAccount data, ModelMap model, HttpSession session)
    {
        service.create(data);
        return redirectList(model, session, "/user/list.jsp");
    }

    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String form(AgencyAccount data, ModelMap model, HttpSession session)
    {
        if (StringUtils.isNotEmpty(data.getId()))
        {
            model.addAttribute("data", service.get(data));
            List<Role> roles = service.get(data).getRoles();
            model.addAttribute("roleList", JsonUtils.toJson(roles).toString());
        }

        return "user/user_modify";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(AgencyAccount data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session, "/user/list.jsp");
    }

    @RequestMapping("/change.do")
    public String change(AgencyAccount data, ModelMap model, HttpSession session)
    {
        service.change(data);
        return redirectList(model, session, "/user/list.jsp");
    }

    @RequestMapping("/delete.jsp")
    public String delete(AgencyAccount data, ModelMap model, HttpSession session)
    {
        service.delete(data);
        return redirectList(model, session, "/user/list.jsp");
    }


    @RequestMapping("/username_unique.do")
    @ResponseBody
    public boolean validate(String username,String id)
    {
        return service.isNameUnique(id, username);
    }
}
