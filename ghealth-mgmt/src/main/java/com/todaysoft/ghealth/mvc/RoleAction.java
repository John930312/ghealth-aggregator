package com.todaysoft.ghealth.mvc;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import com.todaysoft.ghealth.support.ModelResolver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.todaysoft.ghealth.base.response.model.AuthorityNode;
import com.todaysoft.ghealth.model.role.Role;
import com.todaysoft.ghealth.model.role.RoleSearcher;
import com.todaysoft.ghealth.model.user.User;
import com.todaysoft.ghealth.model.user.UserSearcher;
import com.todaysoft.ghealth.service.IRoleService;
import com.todaysoft.ghealth.service.IUserService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/role")
public class RoleAction extends BaseAction
{
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private IUserService userService;
    
    private String url = "/role/list.jsp";
    
    @RequestMapping("/list.jsp")
    public String paginations(RoleSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Role> pagination = roleService.pagination(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "role/role_list";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(Role data, ModelMap model)
    {
        if (StringUtils.isNotEmpty(data.getId()))
        {
            model.addAttribute("data", roleService.get(data));
        }
        List<AuthorityNode> authorityNodes = roleService.getAuthorityNodes();
        String jsonArray = JSON.toJSONString(authorityNodes);
        model.addAttribute("nodes", jsonArray);
        return "role/role_form";
    }

    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(Role data, ModelMap model)
    {
        if (StringUtils.isNotEmpty(data.getId()))
        {
            model.addAttribute("data", roleService.get(data));
        }
        List<AuthorityNode> authorityNodes = roleService.getAuthorityNodes();
        String jsonArray = JSON.toJSONString(authorityNodes);
        model.addAttribute("nodes", jsonArray);
        return "role/role_form";
    }

    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(Role data, ModelMap model, HttpSession session)
    {
        roleService.create(data);
        return redirectList(model, session, url);
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(Role data, ModelMap model, HttpSession session)
    {
        roleService.modify(data);
        return redirectList(model, session, url);
    }

    @RequestMapping("/delete.jsp")
    @ResponseBody
    public boolean delete(String id, ModelMap model, HttpSession session)
    {
        return roleService.delete(id);
    }
    
    @RequestMapping("/addUser_form.jsp")
    public String addUser(String roleId, ModelMap model)
    {
        Role data = new Role();
        data.setId(roleId);
        List<User> list = roleService.removeRepaeted(roleService.get(data).getUsers(), userService.list(new UserSearcher()));
        model.addAttribute("users", list);
        model.addAttribute("roleId", roleId);
        return "role/role_addUser";
    }
    
    @RequestMapping(value = "/addUser.do", method = RequestMethod.POST)
    @ResponseBody
    public void addUser(Role data, ModelMap model, HttpSession session)
    {
        roleService.addUser(data);
    }
    
    @RequestMapping("json_list.do")
    @ResponseBody
    public List<Role> jsonList(RoleSearcher searcher)
    {
        Pagination<Role> pagination = roleService.pagination(searcher, 1, 10);
        if (CollectionUtils.isEmpty(pagination.getRecords()))
        {
            return Collections.emptyList();
        }
        
        List<Role> roleList = pagination.getRecords();
        return roleList;
    }

    @RequestMapping("/isNameUnique.do")
    @ResponseBody
    public boolean isNameUnique(String id,String name)
    {
        return roleService.isNameUnique(id,name);
    }

    @RequestMapping(value = "/reload.do")
    public String reload(ModelMap model, HttpSession session)
    {
        return redirectList(model, session);
    }

    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/role/list.jsp";
    }
}
