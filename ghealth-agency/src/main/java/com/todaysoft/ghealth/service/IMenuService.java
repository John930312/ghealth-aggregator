package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.support.Menu;

public interface IMenuService
{
    List<Menu> getAuthorizedHierarchyMenus();
    
    Menu getFirstMenu(List<Menu> menus);
}
