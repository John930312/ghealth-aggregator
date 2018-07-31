package com.todaysoft.ghealth.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.todaysoft.ghealth.base.request.SignatureRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.dto.MenuDTO;
import com.todaysoft.ghealth.service.IMenuService;
import com.todaysoft.ghealth.service.security.ResourceAuthorizedDecision;
import com.todaysoft.ghealth.service.wrapper.MenuWrapper;
import com.todaysoft.ghealth.support.Menu;

@Service
public class MenuService implements IMenuService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private ResourceAuthorizedDecision decision;
    
    @Autowired
    private MenuWrapper menuWrapper;
    
    @Override
    public List<Menu> getAuthorizedHierarchyMenus()
    {
        ListResponse<MenuDTO> rsp = gateway.request("/mgmt/menus/list", new SignatureRequest()
        {
            @Override
            protected void setSignFields(Map<String, String> fields)
            {
                
            }
        }, new ParameterizedTypeReference<ListResponse<MenuDTO>>()
        {
        });
        
        List<MenuDTO> data = rsp.getData();
        List<MenuDTO> authorizedMenus = authorize(data);
        return menuWrapper.wrapVO(authorizedMenus);
    }
    
    private List<MenuDTO> authorize(List<MenuDTO> menus)
    {
        MenuDTO clone;
        List<MenuDTO> authorizedMenus = new ArrayList<MenuDTO>();
        
        for (MenuDTO menu : menus)
        {
            clone = cloneAsAuthorized(menu);
            
            if (isVisible(clone))
            {
                authorizedMenus.add(clone);
            }
        }
        
        return authorizedMenus;
    }
    
    private boolean isVisible(MenuDTO menu)
    {
        if (!CollectionUtils.isEmpty(menu.getSubmenus()))
        {
            return true;
        }
        
        if (!StringUtils.isEmpty(menu.getUri()) && decision.isAuthorized(menu.getUri()))
        {
            return true;
        }
        
        return false;
    }
    
    private MenuDTO cloneAsAuthorized(MenuDTO menu)
    {
        MenuDTO clone = new MenuDTO();
        clone.setIcon(menu.getIcon());
        clone.setName(menu.getName());
        clone.setUri(menu.getUri());
        clone.setSubmenus(new ArrayList<MenuDTO>());
        
        MenuDTO cloneSubmenu;
        
        if (!CollectionUtils.isEmpty(menu.getSubmenus()))
        {
            for (MenuDTO submenu : menu.getSubmenus())
            {
                cloneSubmenu = cloneAsAuthorized(submenu);
                
                if (isVisible(cloneSubmenu))
                {
                    clone.getSubmenus().add(cloneSubmenu);
                }
            }
        }
        
        return clone;
    }
    
    @Override
    public Menu getFirstMenu(List<Menu> menus)
    {
        if (CollectionUtils.isEmpty(menus))
        {
            return null;
        }
        
        Menu menu = menus.get(0);
        
        if (CollectionUtils.isEmpty(menu.getSubmenu()))
        {
            return menu;
        }
        else
        {
            return getFirstMenu(menu.getSubmenu());
        }
    }
}
