package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.MaintainAgencyBillRequest;
import com.todaysoft.ghealth.mgmt.request.QueryAgencyBillRequest;
import com.todaysoft.ghealth.model.agency.AgencyBill;
import com.todaysoft.ghealth.model.agency.AgencyBillSearcher;
import com.todaysoft.ghealth.service.IAgencyBillService;
import com.todaysoft.ghealth.service.wrapper.AgencyBillWrapper;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AgencyBillService implements IAgencyBillService
{
    @Autowired
    private Gateway gateway;

    @Autowired
    private AgencyBillWrapper wrapper;

    @Override
    public Pagination<AgencyBill> searcher(AgencyBillSearcher searcher, int pageNo, int pageSize)
    {
        QueryAgencyBillRequest request = wrapper.wrapperSearcher(searcher);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.AgencyBill> response = gateway
            .request("/mgmt/agencyBill/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.AgencyBill>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.AgencyBill> pager = response.getData();
        Pagination<AgencyBill> pagination = new Pagination<AgencyBill>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        if (CollectionUtils.isEmpty(pager.getRecords()))
        {
            return pagination;
        }
        pagination.setRecords(wrapper.wrap(pager.getRecords()));
        return pagination;
    }

    @Override
    public List<AgencyBill> list(AgencyBillSearcher searcher)
    {
        QueryAgencyBillRequest request = wrapper.wrapperSearcher(searcher);
        ListResponse<com.todaysoft.ghealth.base.response.model.AgencyBill> response = gateway
            .request("/mgmt/agencyBill/list", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.AgencyBill>>()
            {
            });
        if (CollectionUtils.isEmpty(response.getData()))
        {
            return Collections.emptyList();
        }
        List<AgencyBill> list = wrapper.wrap(response.getData());
        return setDownloadValue(list);
    }

    @Override
    public void create(AgencyBill data)
    {
        MaintainAgencyBillRequest request = new MaintainAgencyBillRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("mgmt/agencyBill/create", request);
    }

    private List<AgencyBill> setDownloadValue(List<AgencyBill> agencyBills)
    {
        List<AgencyBill> list = new ArrayList<>();
        agencyBills.forEach(agencyBill -> {
            AgencyBill data = new AgencyBill();
            if(null != agencyBill.getOrder())
            {
                data.setAgencyName(agencyBill.getAgency().getName());
                if (null != agencyBill.getOrder().getProduct())
                {
                    data.setProductName(agencyBill.getOrder().getProduct().getName());
                }
                data.setDealOrder(agencyBill.getOrder().getCode());
            }
            else
            {
                data.setDealOrder("充值");
            }
            data.setBillTime(agencyBill.getBillTime());
            String flag = "-";
            if(agencyBill.getIncreased())
            {
                flag = "+";
            }
            data.setIncomeExpenses(flag+agencyBill.getIncomeExpenses());
            data.setAmountAfter(agencyBill.getAmountAfter());

            list.add(data);
        });
        return list;
    }
}
