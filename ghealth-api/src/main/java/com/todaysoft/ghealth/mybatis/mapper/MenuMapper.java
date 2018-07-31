package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Menu;

public interface MenuMapper
{
    void delete();
    
    void insert(Menu menu);
    
    List<Menu> getMenus();
}
