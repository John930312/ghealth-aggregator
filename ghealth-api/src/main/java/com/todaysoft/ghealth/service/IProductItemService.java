package com.todaysoft.ghealth.service;

import java.util.List;

public interface IProductItemService
{
    
    List<String> getItemsByProductId(String id);
    
    void deleteItemsByProductId(String id);

    List<String> getProductIdByItemId(String id);
    
}
