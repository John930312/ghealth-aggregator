package com.todaysoft.ghealth.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.ghealth.mgmt.request.MenuImportRequest;
import com.todaysoft.ghealth.service.IMaintainService;
import com.todaysoft.ghealth.service.excel.MenuResolveRecord;
import com.todaysoft.ghealth.service.excel.MenuResolver;
import com.todaysoft.ghealth.service.wrapper.MenuWrapper;
import com.todaysoft.ghealth.support.Menu;
import com.todaysoft.ghealth.support.ServiceException;

@Service
public class MaintainService implements IMaintainService
{
    @Autowired
    private MenuWrapper menuWrapper;
    
    @Autowired
    private Gateway gateway;
    
    @Override
    public List<Menu> parse(MultipartFile file)
    {
        MenuResolver resolver = new MenuResolver(file);
        List<MenuResolveRecord> records = resolver.resolve();
        
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Menu menu;
        Menu lastFirstLevelMenu = null;
        Menu lastSecondLevelMenu = null;
        List<Menu> menus = new ArrayList<Menu>();
        
        for (MenuResolveRecord record : records)
        {
            if (!record.isValid())
            {
                throw new ServiceException("导入数据格式错误");
            }
            
            if (!record.isEnabled())
            {
                continue;
            }
            
            menu = new Menu();
            menu.setIcon(record.getIcon());
            menu.setUri(record.getUri());
            
            if (!StringUtils.isEmpty(record.getFirstLevelName()))
            {
                menu.setName(record.getFirstLevelName());
                
                lastFirstLevelMenu = menu;
                lastSecondLevelMenu = null;
                menus.add(menu);
            }
            else if (!StringUtils.isEmpty(record.getSecondLevelName()))
            {
                if (lastFirstLevelMenu == null)
                {
                    throw new ServiceException("二级菜单前未设置一级菜单");
                }
                
                if (null == lastFirstLevelMenu.getSubmenu())
                {
                    lastFirstLevelMenu.setSubmenu(new ArrayList<Menu>());
                }
                
                lastFirstLevelMenu.getSubmenu().add(menu);
                
                menu.setName(record.getSecondLevelName());
                
                lastSecondLevelMenu = menu;
            }
            else if (!StringUtils.isEmpty(record.getThirdLevelName()))
            {
                if (lastSecondLevelMenu == null)
                {
                    throw new ServiceException("三级菜单前未设置二级菜单");
                }
                
                if (null == lastSecondLevelMenu.getSubmenu())
                {
                    lastSecondLevelMenu.setSubmenu(new ArrayList<Menu>());
                }
                
                lastSecondLevelMenu.getSubmenu().add(menu);
                
                menu.setName(record.getThirdLevelName());
            }
        }
        
        return menus;
    }
    
    @Override
    public void importMenus(List<Menu> menus)
    {
        if (CollectionUtils.isEmpty(menus))
        {
            return;
        }
        
        MenuImportRequest request = new MenuImportRequest();
        request.setMenus(menuWrapper.wrap(menus));
        gateway.request("/mgmt/menus/import", request);
    }
}
