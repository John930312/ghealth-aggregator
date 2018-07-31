package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Cancer;
import com.todaysoft.ghealth.mgmt.request.MaintainDrugRequest;
import com.todaysoft.ghealth.mgmt.request.QueryDrugsRequest;
import com.todaysoft.ghealth.model.drug.Drug;
import com.todaysoft.ghealth.model.drug.DrugSearcher;
import com.todaysoft.ghealth.service.IDrugService;
import com.todaysoft.ghealth.service.wrapper.DrugWrapper;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class DrugService implements IDrugService{
    @Autowired
    private Gateway gateway;

    @Autowired
    private DrugWrapper wrapper;

    @Override
    public Pagination<Drug> pagination(DrugSearcher searcher, int pageNo, int pageSize)
    {
        QueryDrugsRequest request = new QueryDrugsRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Drug> pagerResponse =
                gateway.request("/mgmt/drug/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Drug>>()
                {
                });
        Pager<com.todaysoft.ghealth.base.response.model.Drug> pager = pagerResponse.getData();
        Pagination<Drug> pagination = new Pagination<Drug>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(wrapper.wrap(pager.getRecords()));
        return pagination;
    }


    @Override
    public void create(Drug data)
    {
        MaintainDrugRequest request = new MaintainDrugRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/drug/create", request);
    }

    @Override
    public Drug get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.Drug> response =
                gateway.request("/mgmt/drug/details", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.Drug>>()
                {
                });
        return wrapper.wrap(response.getData());
    }


    @Override
    public void modify(Drug data)
    {
        MaintainDrugRequest request = new MaintainDrugRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/drug/modify", request);
    }

    @Override
    public void delete(String id)
    {
        MaintainDrugRequest request = new MaintainDrugRequest();
        request.setId(id);
        gateway.request("/mgmt/drug/delete", request);

    }

    @Override
    public boolean isIngredientCnUnique(String id, String ingredientCn)
    {
        MaintainDrugRequest request = new MaintainDrugRequest();
        request.setId(id);
        request.setIngredientCn(ingredientCn);

        ObjectResponse<Boolean> response = gateway.request("/mgmt/drug/unique/ingredientCn", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });

        if (null == response.getData())
        {
            return false;
        }

        return response.getData().booleanValue();
    }
}
