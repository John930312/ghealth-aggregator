package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.ObjectStorage;

public interface IObjectStorageService
{
    ObjectStorage get(String id);
}
