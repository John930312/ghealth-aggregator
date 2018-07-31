package com.todaysoft.ghealth.mgmt.request;

import java.util.List;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;
import com.todaysoft.ghealth.base.response.dto.MenuDTO;

public class MenuImportRequest extends SignatureTokenRequest
{
    private List<MenuDTO> menus;
    
    public List<MenuDTO> getMenus()
    {
        return menus;
    }
    
    public void setMenus(List<MenuDTO> menus)
    {
        this.menus = menus;
    }
}
