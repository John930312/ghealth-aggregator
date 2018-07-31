package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.product.AgencyProduct;
import com.todaysoft.ghealth.model.product.AgencyProductDetails;
import com.todaysoft.ghealth.model.product.ProductSearcher;
import com.todaysoft.ghealth.support.Pagination;

import java.util.List;
import java.util.Map;

public interface IAgencyProductService
{
    Pagination<AgencyProduct> searcher(ProductSearcher searcher, int pageNo, int pageSize);
    
    AgencyProductDetails getAgencyProductDetails(String id);
    
    List<AgencyProduct> list(ProductSearcher searcher);

    List<Map> getSimpleAgencyProducts(List<AgencyProduct> datas);

    List<Map> getSimAgencyProducts(List<AgencyProduct> datas);

    boolean isUniqueCode(String code);
}
