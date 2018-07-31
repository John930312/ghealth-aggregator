package com.todaysoft.ghealth.mvc;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.ghealth.service.IMaintainService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.Menu;

@Controller
@RequestMapping("/maintain")
public class MaintainAction extends BaseAction
{
    @Autowired
    private IMaintainService service;
    
    @RequestMapping("/index.jsp")
    public String index(ModelMap model)
    {
        return "maintain/index";
    }
    
    @RequestMapping(value = "/menus/import.jsp", method = RequestMethod.GET)
    public String importMenus(ModelMap model)
    {
        return "maintain/menu_import";
    }
    
    @RequestMapping(value = "/menus/import/parse.jsp", method = RequestMethod.POST)
    public String parseImportMenus(MultipartFile file, HttpSession session, ModelMap model)
    {
        List<Menu> records = service.parse(file);
        String token = UUID.randomUUID().toString();
        session.setAttribute("MENU_IMPORT_TOKEN", token);
        session.setAttribute("MENU_IMPORT_RECORDS", records);
        model.addAttribute("token", token);
        model.addAttribute("records", records);
        return "maintain/menu_import_records";
    }
    
    @RequestMapping(value = "/menus/import.jsp", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public String importMenus(String token, ModelMap model, HttpSession session)
    {
        String stoken = (String)session.getAttribute("MENU_IMPORT_TOKEN");
        
        if (!StringUtils.isEmpty(stoken) && stoken.equals(token))
        {
            List<Menu> records = (List<Menu>)session.getAttribute("MENU_IMPORT_RECORDS");
            session.removeAttribute("MENU_IMPORT_TOKEN");
            session.removeAttribute("MENU_IMPORT_RECORDS");
            service.importMenus(records);
        }
        
        model.clear();
        return "redirect:/maintain/index.jsp";
    }
}
