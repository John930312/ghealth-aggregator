package com.todaysoft.ghealth.portal.mgmt.facade.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.dto.MenuDTO;
import com.todaysoft.ghealth.mybatis.model.Menu;

@Component
public class MenuWrapper
{
    public List<Menu> wrap(List<MenuDTO> records)
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
            menu.setId(record.getId());
            menu.setName(record.getName());
            menu.setUri(record.getUri());
            menu.setIcon(record.getIcon());
            
            if (null != record.getSubmenus())
            {
                menu.setSubmenus(wrap(record.getSubmenus()));
            }
            
            menus.add(menu);
        }
        
        return menus;
    }
    
    public List<MenuDTO> wrapDTO(List<Menu> records)
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
            menu.setId(record.getId());
            menu.setName(record.getName());
            menu.setUri(record.getUri());
            menu.setIcon(record.getIcon());
            
            if (null != record.getSubmenus())
            {
                menu.setSubmenus(wrapDTO(record.getSubmenus()));
            }
            
            menus.add(menu);
        }
        
        return menus;
    }
}
