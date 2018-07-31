package com.todaysoft.ghealth.service;

import java.util.List;

public interface IAreaService
{
    List<com.todaysoft.ghealth.base.response.model.Area> findProvince();
    
    List<com.todaysoft.ghealth.base.response.model.Area> findByParentId(String parentId);
    
}
