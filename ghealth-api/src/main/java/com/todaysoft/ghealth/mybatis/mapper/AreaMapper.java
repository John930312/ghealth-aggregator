package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Area;

public interface AreaMapper
{
    List<Area> findProvince();
    
    List<Area> findByParentId(String parentId);
    
    Area get(String id);
}
