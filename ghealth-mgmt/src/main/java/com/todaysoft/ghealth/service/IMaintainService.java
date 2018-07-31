package com.todaysoft.ghealth.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.ghealth.support.Menu;

public interface IMaintainService
{
    List<Menu> parse(MultipartFile file);
    
    void importMenus(List<Menu> menus);
}
