package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.agcy.request.QueryAgencyBillRequest;
import com.todaysoft.ghealth.model.agency.Agency;
import com.todaysoft.ghealth.model.agency.AgencyBill;
import com.todaysoft.ghealth.model.agency.AgencyBillSearcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class AgencyBillWrapper
{
    @Autowired
    private OrderWrapper orderWrapper;
    
    public List<AgencyBill> wrap(List<com.todaysoft.ghealth.base.response.model.AgencyBill> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        AgencyBill AgencyBill;
        List<AgencyBill> AgencyBills = new ArrayList<AgencyBill>();
        for (com.todaysoft.ghealth.base.response.model.AgencyBill record : records)
        {
            AgencyBill = new AgencyBill();
            wrapRecord(record, AgencyBill);
            AgencyBills.add(AgencyBill);
        }
        return AgencyBills;
    }
    
    public AgencyBill wrap(com.todaysoft.ghealth.base.response.model.AgencyBill record)
    {
        if (null == record)
        {
            return null;
        }
        
        AgencyBill AgencyBill = new AgencyBill();
        wrapRecord(record, AgencyBill);
        return AgencyBill;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.AgencyBill source, AgencyBill target)
    {
        BeanUtils.copyProperties(source, target, "billTime");
        if (null != source.getAgency())
        {
            Agency data = new Agency();
            data.setName(source.getAgency().getName());
            data.setAccountAmount(source.getAgency().getAccountAmount());
            target.setAgency(data);
        }
        if (null != source.getOrder())
        {
            target.setOrder(orderWrapper.wrap(source.getOrder()));
        }
        target.setBillTime(null == source.getBillTime() ? null : new Date(source.getBillTime()));
    }
    
    public QueryAgencyBillRequest wrapperSearcher(AgencyBillSearcher searcher)
    {
        QueryAgencyBillRequest request = new QueryAgencyBillRequest();
        BeanUtils.copyProperties(searcher, request, "startTime", "endtime");
        if (null != searcher.getStartTime())
        {
            request.setStartTime(String.valueOf(searcher.getStartTime().getTime()));
        }
        if (null != searcher.getEndTime())
        {
            request.setEndTime(String.valueOf(searcher.getEndTime().getTime()));
        }
        return request;
    }

    public List<AgencyBill> downloadWrap(List<com.todaysoft.ghealth.base.response.model.AgencyBill> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }

        return records.stream().map(x -> {
            AgencyBill agencyBill = new AgencyBill();
            BeanUtils.copyProperties(x, agencyBill);
            String flag = "-";
            if (agencyBill.getIncreased())
            {
                flag = "+";
            }
            agencyBill.setIncomeExpenses(flag + x.getIncomeExpenses());
            agencyBill.setAmountAfter(x.getAmountAfter());
            if (StringUtils.isEmpty(agencyBill.getDealOrder()))
            {
                agencyBill.setDealOrder("充值");
            }
            agencyBill.setBillTime(null == x.getBillTime() ? null : new Date(x.getBillTime()));
            return agencyBill;
        }).collect(toList());
    }
}
