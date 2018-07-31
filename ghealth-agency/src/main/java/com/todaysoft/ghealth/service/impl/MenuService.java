package com.todaysoft.ghealth.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.ghealth.service.security.ResourceAuthorizedDecision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.service.IMenuService;
import com.todaysoft.ghealth.support.Menu;
import org.springframework.util.StringUtils;

@Service
public class MenuService implements IMenuService
{
    @Autowired
    private ResourceAuthorizedDecision decision;

    @Override
    public List<Menu> getAuthorizedHierarchyMenus()
    {
        Menu orders = new Menu();
        orders.setIcon("fa-reorder");
        orders.setName("订单管理");
        orders.setUri("/order/list.jsp");
        
        Menu members = new Menu();
        members.setIcon("fa-user");
        members.setName("客户管理");
        members.setUri("/customer/list.jsp");
        
        Menu products = new Menu();
        products.setIcon("fa-tasks");
        products.setName("代理产品");
        products.setUri("/product/list.jsp");

        Menu agencyBills = new Menu();
        agencyBills.setIcon("fa-cny");
        agencyBills.setName("账单明细");
        agencyBills.setUri("/agencyBill/list.jsp");

        Menu messageSends = new Menu();
        messageSends.setIcon("fa-commenting");
        messageSends.setName("消息推送");
        messageSends.setUri("/messageSend/list.jsp");


        Menu users = new Menu();
        users.setIcon("fa-user");
        users.setName("用户管理");
        users.setUri("/user/list.jsp");

        Menu roles = new Menu();
        roles.setIcon("fa-adjust");
        roles.setName("角色管理");
        roles.setUri("/role/list.jsp");

        List<Menu> menus = new ArrayList<Menu>();

        menus.add(members);
        menus.add(orders);

        menus.add(products);
        menus.add(agencyBills);
        menus.add(messageSends);
        menus.add(users);
        menus.add(roles);


        List<Menu> menuList = authorize(menus);



        return menuList;
    }


    private List<Menu> authorize(List<Menu> menus)
    {
        List<Menu> authorizedMenus = new ArrayList<Menu>();

        for (Menu menu : menus)
        {
            if (isVisible(menu))
            {
                authorizedMenus.add(menu);
            }
        }

        return authorizedMenus;
    }

    private boolean isVisible(Menu menu)
    {
        if (decision.isAuthorized(menu.getUri()))
        {
            return true;
        }
        return false;
    }


    
    @Override
    public Menu getFirstMenu(List<Menu> menus)
    {
        if (CollectionUtils.isEmpty(menus))
        {
            return null;
        }
        
        Menu menu = menus.get(0);
        
        if (CollectionUtils.isEmpty(menu.getSubmenus()))
        {
            return menu;
        }
        else
        {
            return getFirstMenu(menu.getSubmenus());
        }
    }
}
