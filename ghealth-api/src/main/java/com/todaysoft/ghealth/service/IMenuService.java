package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Menu;

public interface IMenuService
{
    List<Menu> getRootMenus();
    
    void importMenus(List<Menu> menus);
}
