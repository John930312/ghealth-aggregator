package com.todaysoft.ghealth.wechat.mvc;

import com.todaysoft.ghealth.wechat.model.MobileUser;
import com.todaysoft.ghealth.wechat.model.Order;
import com.todaysoft.ghealth.wechat.model.ResponseMessage;
import com.todaysoft.ghealth.wechat.model.Token;
import com.todaysoft.ghealth.wechat.mvc.base.BaseAction;
import com.todaysoft.ghealth.wechat.service.IOrderService;
import com.todaysoft.ghealth.wechat.service.ITokenService;
import com.todaysoft.ghealth.wechat.service.core.Account;
import com.todaysoft.ghealth.wechat.service.core.AccountContextHolder;
import com.todaysoft.ghealth.wechat.service.sms.CaptchaMessager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class IndexController extends BaseAction
{
    private static Logger log = LoggerFactory.getLogger(IndexController.class);
    
    @Autowired
    private AccountContextHolder accountContextHolder;
    
    @Autowired
    private ITokenService tokenService;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private CaptchaMessager captchaMessager;
    
    @GetMapping("/")
    public String index()
    {
        Account account = accountContextHolder.getAccount();
        
        if (log.isDebugEnabled())
        {
            log.debug("Account openid {}, name {}, token {}.", account.getOpenid(), account.getName(), account.getToken());
        }
        
        if (StringUtils.isEmpty(account.getToken()))
        {
            return "login";
        }
        else
        {
            return "redirect:/home.jsp";
        }
    }
    
    @RequestMapping("/loginout.jsp/{phone}")
    public String loginout(@PathVariable String phone)
    {
        tokenService.freeze(phone);
        return "login";
    }
    
    @RequestMapping("/unbind.jsp/{phone}")
    public String unbind(@PathVariable String phone)
    {
        tokenService.unbind(phone);
        return "login";
    }
    
    @RequestMapping("/sendCaptcha.jsp")
    @ResponseBody
    public Map<Boolean, Map<String, String>> sendCaptcha(String phone)
    {
        Map<Boolean, Map<String, String>> map = new HashMap<>();
        Map<String, String> reasonMap = new HashMap<>();
        //号码已绑定数据
        if (tokenService.existPhone(phone))
        {
            reasonMap.put("mes", "当前号码已被绑定，请解除绑定后再试！");
            map.put(false,reasonMap);
            return map;
        }
        
        if (StringUtils.isNotEmpty(phone) && orderService.existDatas(phone))
        {
            captchaMessager.message(phone);
            
            reasonMap.put("mes", "success");
            map.put(true,reasonMap);
            new Timer().schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    captchaMessager.destoryCaptcha(phone);
                }
            }, 1000 * 60 * 30);
        }
        else
        {
            reasonMap.put("mes", "没有查到与号码相关的订单！");
            map.put(false,reasonMap);
        }

        return map;
    }
    
    @RequestMapping("/validateCaptcha.jsp")
    @ResponseBody
    public Map<String, ResponseMessage> validateCaptcha(MobileUser mobileUser, ModelMap model)
    {
        Map<String, ResponseMessage> mes = new HashMap<>();
        Map<String, String> map = captchaMessager.getCaptchaMap();
        ResponseMessage rm = new ResponseMessage();
        String captcha = map.get(mobileUser.getPhone());
        if (StringUtils.isEmpty(captcha))
        {
            rm.setCanUse(false);
            rm.setMessage("验证码已过期");
        }
        else
        {
            if (captcha.equals(mobileUser.getCaptcha()))
            {
                rm.setCanUse(true);
            }
            else
            {
                rm.setCanUse(false);
                rm.setMessage("验证码不正确");
            }
        }
        mes.put("message", rm);
        return mes;
    }
    
    // 定义激活页面
    @RequestMapping("/active.jsp")
    public String active(MobileUser data)
    {
        Token token = tokenService.active(data);
        
        Account account = accountContextHolder.getAccount();
        BeanUtils.copyProperties(token, account);
        accountContextHolder.setAccount(account);
        
        return "redirect:/home.jsp";
    }
    
    @RequestMapping("/home.jsp")
    public String home(ModelMap model, HttpSession session)
    {
        List<Order> orders = orderService.getOrders();
        
        List<Order> sortedOrders = orders.stream().sorted(Comparator.comparing(Order::getCreateTime).reversed()).collect(Collectors.toList());
        model.addAttribute("data", sortedOrders.get(0));
        if (!Objects.isNull(session.getAttribute("orders")))
        {
            session.removeAttribute("orders");
        }
        
        session.setAttribute("orders", sortedOrders);
        return "business/home";
    }
}
