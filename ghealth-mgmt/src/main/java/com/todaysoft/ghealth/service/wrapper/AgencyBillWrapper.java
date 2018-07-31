package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.mgmt.request.QueryAgencyBillRequest;
import com.todaysoft.ghealth.model.agency.Agency;
import com.todaysoft.ghealth.model.agency.AgencyBill;
import com.todaysoft.ghealth.model.agency.AgencyBillSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class AgencyBillWrapper
{
    @Autowired
    private AgencyWrapper wrapper;
    
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
            wrapper.wrapRecord(source.getAgency(), data);
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
}
