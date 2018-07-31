package com.todaysoft.ghealth.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.todaysoft.ghealth.model.agency.Agency;
import com.todaysoft.ghealth.model.agency.AgencySearcher;
import com.todaysoft.ghealth.model.product.AgencyProductOldPrice;
import com.todaysoft.ghealth.service.IAgencyService;
import com.todaysoft.ghealth.service.wrapper.AgencyProductOldPriceWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyProduct;
import com.todaysoft.ghealth.mgmt.request.MaintainProductAgencyRequest;
import com.todaysoft.ghealth.mgmt.request.QueryProductAgencyRequest;
import com.todaysoft.ghealth.model.product.ProductAgent;
import com.todaysoft.ghealth.model.product.ProductAgentSearcher;
import com.todaysoft.ghealth.service.IProductAgentService;
import com.todaysoft.ghealth.service.wrapper.ProductAgentWrapper;
import com.todaysoft.ghealth.support.Pagination;

/**
 * Created by xjw on 2017/9/14.
 */
@Service
public class ProductAgentService implements IProductAgentService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private ProductAgentWrapper wrapper;

    @Autowired
    private IAgencyService agencyService;

    @Autowired
    private AgencyProductOldPriceWrapper agencyProductOldPriceWrapper;

    @Override
    public Pagination<ProductAgent> search(ProductAgentSearcher searcher, int pageNo, int pageSize)
    {
        QueryProductAgencyRequest request = new QueryProductAgencyRequest();
        request.setProductName(searcher.getProductName());
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);

        PagerResponse<com.todaysoft.ghealth.base.response.model.AgencyProduct> response =
            gateway.request("/mgmt/products-agency/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.AgencyProduct>>()
            {
            });
        Pager<AgencyProduct> pager = response.getData();
        Pagination<ProductAgent> pagination = new Pagination<ProductAgent>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        if (CollectionUtils.isEmpty(pager.getRecords()))
        {
            return pagination;
        }
        
        pagination.setRecords(wrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public void allocateAgent(ProductAgent data)
    {
        MaintainProductAgencyRequest request = new MaintainProductAgencyRequest();
        request.setAgencyId(data.getAgencyId());
        request.setAgencyPrice(data.getAgencyPrice());
        request.setTestingProductId(data.getTestingProductId());
        gateway.request("/mgmt/products-agency/allocate", request);
    }


    @Override
    public void modify(ProductAgent data)
    {
        MaintainProductAgencyRequest request = new MaintainProductAgencyRequest();
        BeanUtils.copyProperties(data, request,"startTime","endTime");
        if (null != data.getStartTime())
        {
            request.setStartTime(data.getStartTime().getTime());
        }
        if (null != data.getEndTime())
        {
            request.setEndTime(data.getEndTime().getTime());
        }
        gateway.request("/mgmt/products-agency/modify", request);
    }
    @Override
    public List<ProductAgent> getProductAgentsByProductId(String productId)
    {
        QueryProductAgencyRequest request = new QueryProductAgencyRequest();
        request.setProductId(productId);
        ListResponse<AgencyProduct> response = gateway.request("/mgmt/products-agency/getProductAgentsByProductId",
            request,
            new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.AgencyProduct>>()
            {
            });
        if (CollectionUtils.isEmpty(response.getData()))
        {
            return Collections.emptyList();
        }
        return wrapper.wrap(response.getData());
    }

    @Override
    public List<Agency> getAgencyList(String id)
    {
        List<Agency> list = agencyService.list(new AgencySearcher());
        List<ProductAgent> productAgents = getProductAgentsByProductId(id);
        List<String> agentIds = new ArrayList<>();
        productAgents.forEach(productAgent -> {
            agentIds.add(productAgent.getAgencyId());
        });
        for (Agency agency : list)
        {
            if (agentIds.contains(agency.getId()))
            {
                agency.setExist(true);
            }
            else
            {
                agency.setExist(false);
            }
        }
        return list;
    }

    @Override
    public void delete(ProductAgent data)
    {
        MaintainProductAgencyRequest request = new MaintainProductAgencyRequest();
        BeanUtils.copyProperties(data, request);

        gateway.request("/mgmt/products-agency/delete", request);
    }


    @Override
    public void addPro(ProductAgent data)
    {
        MaintainProductAgencyRequest request = new MaintainProductAgencyRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/products-agency/addPro", request);
    }

    @Override
    public void deletePro(ProductAgent data)
    {
        MaintainProductAgencyRequest request = new MaintainProductAgencyRequest();
        BeanUtils.copyProperties(data, request);

        gateway.request("/mgmt/products-agency/deletePro", request);
    }

    @Override
    public List<AgencyProductOldPrice> getOldPriceDetails(ProductAgent data)
    {
        QueryProductAgencyRequest request = new QueryProductAgencyRequest();
        request.setProductId(data.getTestingProductIds());
        request.setAgencyId(data.getAgencyId());
        ListResponse<com.todaysoft.ghealth.base.response.model.AgencyProductOldPrice> response = gateway.request("/mgmt/products-agency/getOldPriceDetails",
                request,
                new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.AgencyProductOldPrice>>()
                {
                });
        if (CollectionUtils.isEmpty(response.getData()))
        {
            return Collections.emptyList();
        }
        return agencyProductOldPriceWrapper.wrap(response.getData());
    }

}
