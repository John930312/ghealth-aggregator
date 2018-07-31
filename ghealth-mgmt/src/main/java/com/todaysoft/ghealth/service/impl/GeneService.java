package com.todaysoft.ghealth.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.GeneDetails;
import com.todaysoft.ghealth.mgmt.request.MaintainGeneRequest;
import com.todaysoft.ghealth.mgmt.request.QueryGenesRequest;
import com.todaysoft.ghealth.model.gene.Gene;
import com.todaysoft.ghealth.model.gene.GeneForm;
import com.todaysoft.ghealth.model.gene.GeneSearcher;
import com.todaysoft.ghealth.service.IGeneService;
import com.todaysoft.ghealth.service.wrapper.GeneWrapper;
import com.todaysoft.ghealth.support.Pagination;

@Service
public class GeneService implements IGeneService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private GeneWrapper geneWrapper;
    
    @Override
    public Pagination<Gene> searcher(GeneSearcher searcher, int pageNo, int pageSize)
    {
        QueryGenesRequest request = new QueryGenesRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Gene> pagerResponse =
            gateway.request("/mgmt/gene/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Gene>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.Gene> pager = pagerResponse.getData();
        Pagination<Gene> pagination = new Pagination<Gene>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(geneWrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public GeneDetails get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.GeneDetails> response =
            gateway.request("/mgmt/gene/details", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.GeneDetails>>()
            {
            });
        return response.getData();
    }
    
    @Override
    public void create(GeneForm data)
    {
        MaintainGeneRequest request = new MaintainGeneRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/gene/create", request);
    }
    
    @Override
    public void modify(GeneForm data)
    {
        MaintainGeneRequest request = new MaintainGeneRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/gene/modify", request);
    }
    
    @Override
    public void delete(String id)
    {
        MaintainGeneRequest request = new MaintainGeneRequest();
        request.setId(id);
        gateway.request("/mgmt/gene/delete", request);
        
    }

    @Override
    public boolean isNameUnique(String id, String name)
    {
        MaintainGeneRequest request = new MaintainGeneRequest();
        request.setId(id);
        request.setName(name);

        ObjectResponse<Boolean> response = gateway.request("/mgmt/gene/unique/name", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });

        if (null == response.getData())
        {
            return false;
        }

        return response.getData().booleanValue();
    }

    @Override
    public boolean isSymbolUnique(String id, String symbol)
    {
        MaintainGeneRequest request = new MaintainGeneRequest();
        request.setId(id);
        request.setSymbol(symbol);

        ObjectResponse<Boolean> response = gateway.request("/mgmt/gene/unique/symbol", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });

        if (null == response.getData())
        {
            return false;
        }

        return response.getData().booleanValue();
    }
    
}
