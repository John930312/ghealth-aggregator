package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Area;

public interface IAreaService
{
    List<Area> findProvince();
    
    List<Area> findByParentId(String parentId);
    
    String getDistrictName(String id);
    
    String getDistrictFullName(String id);
}
