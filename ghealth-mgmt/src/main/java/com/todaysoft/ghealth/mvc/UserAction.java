package com.todaysoft.ghealth.mvc;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.todaysoft.ghealth.model.role.Role;
import com.todaysoft.ghealth.model.user.User;
import com.todaysoft.ghealth.model.user.UserForm;
import com.todaysoft.ghealth.model.user.UserSearcher;
import com.todaysoft.ghealth.service.IUserService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction
{
    @Autowired
    private IUserService service;
    
    @RequestMapping("/list.jsp")
    public String list(UserSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<User> pagination = service.searcher(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "user/user_list";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        return "user/user_create";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(UserForm data, ModelMap model, HttpSession session)
    {
        service.create(data);
        return redirectList(model, session, "/user/list.jsp");
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String form(User data, ModelMap model, HttpSession session)
    {
        if (StringUtils.isNotEmpty(data.getId()))
        {
            model.addAttribute("data", service.get(data));
            List<Role> roles = service.get(data).getRoles();
            model.addAttribute("roleList", JSON.toJSON(roles).toString());
        }
        
        return "user/user_modify";
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(UserForm data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session, "/user/list.jsp");
    }
    
    @RequestMapping("/delete.jsp")
    public String delete(User data, ModelMap model, HttpSession session)
    {
        service.delete(data);
        return redirectList(model, session, "/user/list.jsp");
    }
    
    @RequestMapping("/change.do")
    @FormSubmitHandler
    public String change(User data, ModelMap model, HttpSession session)
    {
        service.change(data);
        return redirectList(model, session, "/user/list.jsp");
    }
    
    @RequestMapping("/validate.do")
    @ResponseBody
    public boolean validate(String username,String id)
    {
        boolean a = service.isUsernameUnique(username,id);
        return a;
    }

}
