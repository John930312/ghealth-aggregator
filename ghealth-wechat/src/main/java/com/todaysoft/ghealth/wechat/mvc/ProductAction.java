package com.todaysoft.ghealth.wechat.mvc;

import com.todaysoft.ghealth.wechat.mvc.base.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductAction extends BaseAction
{
    @RequestMapping("/list.jsp")
    public String list()
    {
        return "product/products";
    }
    @RequestMapping("/tumor.jsp")
    public String getTumor()
    {
        return "product/tumor";
    }
    
    @RequestMapping("/child.jsp")
    public String getChild()
    {
        return "product/child";
    }
    
    @RequestMapping("/pharmacy.jsp")
    public String getPharmacy()
    {
        return "product/pharmacy";
    }
    
    @RequestMapping("/chronic.jsp")
    public String getChronic()
    {
        return "product/chronic";
    }
    
    @RequestMapping("/nutrientAbsorption.jsp")
    public String getNutrientAbsorption()
    {
        return "product/nutrientAbsorption";
    }
    
}
