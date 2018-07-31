package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.model.agency.Agency;
import com.todaysoft.ghealth.model.product.AgencyProductOldPrice;
import com.todaysoft.ghealth.model.product.ProductAgent;
import com.todaysoft.ghealth.model.product.ProductAgentSearcher;
import com.todaysoft.ghealth.support.Pagination;

/**
 * Created by xjw on 2017/9/14.
 */
public interface IProductAgentService
{
    Pagination<ProductAgent> search(ProductAgentSearcher searcher, int pageNo, int pageSize);
    
    void allocateAgent(ProductAgent productAgent);
    
    List<ProductAgent> getProductAgentsByProductId(String productId);

    List<Agency> getAgencyList(String id);

    void modify(ProductAgent data);

    void delete(ProductAgent data);

    void addPro(ProductAgent data);

    void deletePro(ProductAgent data);

    List<AgencyProductOldPrice> getOldPriceDetails(ProductAgent data);
}
