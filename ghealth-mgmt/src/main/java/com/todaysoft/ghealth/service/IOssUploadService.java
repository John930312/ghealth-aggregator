package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: xjw
 * @Date: 2018/8/1 9:54
 */
public interface IOssUploadService
{
    Map<String, String> getOssSign(Order order,String fileName);

    void callBack(HttpServletRequest request, HttpServletResponse response);
}
