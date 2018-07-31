package com.todaysoft.ghealth.mvc;

import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.base.response.model.Area;
import com.todaysoft.ghealth.service.IAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todaysoft.ghealth.model.agency.Agency;
import com.todaysoft.ghealth.service.IAgencyService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.ModelResolver;

import java.util.List;

@Controller
@RequestMapping("/agency")
public class AgencyAction extends BaseAction
{
    @Autowired
    private IAgencyService agencyService;

    @Autowired
    private IAreaService areaService;
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    public String midify(String agencyId,boolean primaryAccount, ModelMap model)
    {

        Agency agency =agencyService.get(agencyId);
        Area provinceArea = new Area();
        if (!StringUtils.isEmpty(agency.getProvince()))
        {
            provinceArea.setId(agency.getProvince());
        }

        if (!StringUtils.isEmpty(agency.getProvince()))
        {
            List<Area> province = areaService.findByParentId(agency.getProvince());
            model.addAttribute("province", province);
        }
        model.addAttribute("data", agency);
        List<Area> list = areaService.findProvince();

        model.addAttribute("list", list);
        model.addAttribute("primaryAccount",primaryAccount);
        return "agency/agency_form";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    public String midify(Agency data, ModelMap model, HttpSession session)
    {
        agencyService.modify(data);
        return redirectList(model, session);
    }
    
    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/";
    }


    @RequestMapping(value = "/modifyPassword.jsp", method = RequestMethod.POST)
    public String changePassword(Agency data, ModelMap model, HttpSession session)
    {
        agencyService.modifyPassword(data);
        return "login";
    }

}
