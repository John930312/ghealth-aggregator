package com.todaysoft.ghealth.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todaysoft.ghealth.service.IMenuService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.Menu;

@Controller
public class DispatcherAction extends BaseAction
{
    @Autowired
    private IMenuService menuService;
    
    @RequestMapping("/login.jsp")
    public String login()
    {
        return "login";
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model)
    {
        List<Menu> menus = menuService.getAuthorizedHierarchyMenus();
        model.addAttribute("menus", menus);
        model.addAttribute("defaultMenu", menuService.getFirstMenu(menus));
        return "layout";
    }
}
