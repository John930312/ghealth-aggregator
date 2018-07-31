package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.dto.MenuDTO;
import com.todaysoft.ghealth.support.Menu;

@Component
public class MenuWrapper
{
    public List<MenuDTO> wrap(List<Menu> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        MenuDTO menu;
        List<MenuDTO> menus = new ArrayList<MenuDTO>();
        
        for (Menu record : records)
        {
            menu = new MenuDTO();
            menu.setName(record.getName());
            menu.setIcon(record.getIcon());
            menu.setUri(record.getUri());
            
            if (null != record.getSubmenu())
            {
                menu.setSubmenus(wrap(record.getSubmenu()));
            }
            
            menus.add(menu);
        }
        
        return menus;
    }
    
    public List<Menu> wrapVO(List<MenuDTO> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Menu menu;
        List<Menu> menus = new ArrayList<Menu>();
        
        for (MenuDTO record : records)
        {
            menu = new Menu();
            menu.setName(record.getName());
            menu.setIcon(record.getIcon());
            menu.setUri(record.getUri());
            
            if (null != record.getSubmenus())
            {
                menu.setSubmenu(wrapVO(record.getSubmenus()));
            }
            
            menus.add(menu);
        }
        
        return menus;
    }
}
