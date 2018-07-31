package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.dto.MenuDTO;
import com.todaysoft.ghealth.mgmt.request.MenuImportRequest;
import com.todaysoft.ghealth.mybatis.model.Menu;
import com.todaysoft.ghealth.portal.mgmt.facade.wrapper.MenuWrapper;
import com.todaysoft.ghealth.service.IMenuService;

@Component
public class MenuFacade
{
    @Autowired
    private MenuWrapper menuWrapper;
    
    @Autowired
    private IMenuService menuService;
    
    public ListResponse<MenuDTO> list()
    {
        List<Menu> records = menuService.getRootMenus();
        return new ListResponse<>(menuWrapper.wrapDTO(records));
    }
    
    public void importMenus(MenuImportRequest request)
    {
        List<MenuDTO> records = request.getMenus();
        
        if (CollectionUtils.isEmpty(records))
        {
            return;
        }
        
        List<Menu> menus = menuWrapper.wrap(records);
        menuService.importMenus(menus);
    }
}
