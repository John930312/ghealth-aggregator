package com.todaysoft.ghealth.mvc;

import com.todaysoft.ghealth.service.IMenuService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

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
    public String index(ModelMap model, HttpSession session)
    {
        List<Menu> menus = menuService.getAuthorizedHierarchyMenus();
        model.addAttribute("menus", menus);
        model.addAttribute("defaultMenu", menuService.getFirstMenu(menus));
        Integer attribute = (Integer)session.getServletContext().getAttribute("LOGIN-STATUS");
        Integer loginStatus = Optional.ofNullable(attribute).orElse(0);
        loginStatus++;
        session.getServletContext().setAttribute("LOGIN-STATUS", loginStatus);
        model.addAttribute("loginStatus", loginStatus);
        return "layout";
    }
}
