package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.dto.MenuDTO;
import com.todaysoft.ghealth.mgmt.request.MenuImportRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.MenuFacade;

@RestController
@RequestMapping("/mgmt/menus")
public class MgmtMenuController
{
    @Autowired
    private MenuFacade facade;
    
    @RequestMapping("/list")
    public ListResponse<MenuDTO> list()
    {
        return facade.list();
    }
    
    @RequestMapping("/import")
    public void importMenus(@RequestBody MenuImportRequest request)
    {
        facade.importMenus(request);
    }
}
