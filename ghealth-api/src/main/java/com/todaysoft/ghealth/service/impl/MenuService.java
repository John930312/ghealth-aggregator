package com.todaysoft.ghealth.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.mybatis.mapper.MenuMapper;
import com.todaysoft.ghealth.mybatis.model.Menu;
import com.todaysoft.ghealth.service.IMenuService;
import com.todaysoft.ghealth.utils.IdGen;

@Service
public class MenuService implements IMenuService
{
    @Autowired
    private MenuMapper menuMapper;
    
    @Override
    public List<Menu> getRootMenus()
    {
        List<Menu> records = menuMapper.getMenus();
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Map<String, Menu> context = new HashMap<String, Menu>();
        List<Menu> rootMenus = new ArrayList<Menu>();
        
        for (Menu record : records)
        {
            if (StringUtils.isEmpty(record.getParentId()))
            {
                rootMenus.add(record);
            }
            
            context.put(record.getId(), record);
        }
        
        Menu parentMenu;
        
        for (Menu record : records)
        {
            if (StringUtils.isEmpty(record.getParentId()))
            {
                continue;
            }
            
            parentMenu = context.get(record.getParentId());
            
            if (null == parentMenu)
            {
                continue;
            }
            
            if (null == parentMenu.getSubmenus())
            {
                parentMenu.setSubmenus(new ArrayList<Menu>());
            }
            
            parentMenu.getSubmenus().add(record);
        }
        
        context.clear();
        sortMenus(rootMenus);
        return rootMenus;
    }
    
    private void sortMenus(List<Menu> menus)
    {
        if (CollectionUtils.isEmpty(menus))
        {
            return;
        }
        
        Collections.sort(menus, new Comparator<Menu>()
        {
            @Override
            public int compare(Menu o1, Menu o2)
            {
                int o1s = o1.getSort() == null ? 999 : o1.getSort().intValue();
                int o2s = o2.getSort() == null ? 999 : o2.getSort().intValue();
                
                if (o1s == o2s)
                {
                    return 0;
                }
                
                return o1s > o2s ? 1 : -1;
            }
        });
        
        for (Menu menu : menus)
        {
            sortMenus(menu.getSubmenus());
        }
    }
    
    @Override
    @Transactional
    public void importMenus(List<Menu> menus)
    {
        menuMapper.delete();
        
        if (CollectionUtils.isEmpty(menus))
        {
            return;
        }
        
        int sort = 1;
        
        for (Menu menu : menus)
        {
            menu.setSort(sort);
            insert(menu);
            sort++;
        }
    }
    
    private void insert(Menu menu)
    {
        String id = IdGen.uuid();
        menu.setId(id);
        menuMapper.insert(menu);
        
        if (!CollectionUtils.isEmpty(menu.getSubmenus()))
        {
            int sort = 1;
            
            for (Menu submenu : menu.getSubmenus())
            {
                submenu.setParentId(id);
                submenu.setSort(sort);
                insert(submenu);
                sort++;
            }
        }
    }
}
