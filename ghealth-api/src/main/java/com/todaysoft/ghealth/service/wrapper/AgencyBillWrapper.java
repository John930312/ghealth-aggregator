package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.AgencyBill;
import com.todaysoft.ghealth.mybatis.model.AgencyPrepay;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.service.IAgencyPrepayService;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.service.ITestingProductService;
import com.todaysoft.ghealth.utils.DataStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class AgencyBillWrapper
{
    @Autowired
    private AgencyWrapper wrapper;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private OrderWrapper orderWrapper;
    
    @Autowired
    private IAgencyPrepayService agencyPrepayService;
    
    @Autowired
    private ITestingProductService testingProductService;
    
    public List<AgencyBill> wrap(List<com.todaysoft.ghealth.mybatis.model.AgencyBill> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        AgencyBill AgencyBill;
        List<AgencyBill> AgencyBills = new ArrayList<AgencyBill>();
        for (com.todaysoft.ghealth.mybatis.model.AgencyBill record : records)
        {
            AgencyBill = new AgencyBill();
            wrap(record, AgencyBill);
            AgencyBills.add(AgencyBill);
        }
        
        return AgencyBills;
    }
    
    public AgencyBill wrap(com.todaysoft.ghealth.mybatis.model.AgencyBill record)
    {
        AgencyBill AgencyBill = new AgencyBill();
        wrap(record, AgencyBill);
        return AgencyBill;
    }
    
    private void wrap(com.todaysoft.ghealth.mybatis.model.AgencyBill source, AgencyBill target)
    {
        BeanUtils.copyProperties(source, target, "billTime");
        String eventDetails = source.getEventDetails();
        String operateName = target.getOperateName();
        if (DataStatus.BILL_ADVANCE.equals(source.getBillType()))
        {
            AgencyPrepay agencyPrepay = agencyPrepayService.get(eventDetails);
            if (null != agencyPrepay)
            {
                target.setIncomeExpenses(String.valueOf(agencyPrepay.getAmount()));
            }
            operateName = StringUtils.isEmpty(operateName) ? agencyPrepay.getCreatorName() : operateName;
        }
        else
        {
            Order order;
            if (eventDetails.indexOf("-") != -1)
            {
                String orderId = eventDetails.substring(0, eventDetails.indexOf("-"));
                String productId = eventDetails.substring(eventDetails.indexOf("-") + 1, eventDetails.length());
                order = orderService.getOrderById(orderId);
                Optional.ofNullable(order).ifPresent(x -> x.setProduct(testingProductService.get(productId)));
            }
            else
            {
                order = orderService.getOrderById(eventDetails);
            }
            if (null != order)
            {
                target.setOrder(orderWrapper.wrap(order));
                target.setIncomeExpenses(String.valueOf(order.getActualPrice()));
                if (DataStatus.BILL_PLACE_ORDER.equals(source.getBillType()))
                {
                    operateName = StringUtils.isEmpty(operateName) ? order.getSubmitorName() : operateName;
                }
                else
                {
                    operateName = StringUtils.isEmpty(operateName) ? order.getUpdatorName() : operateName;
                }
            }
            target.setOperateName(operateName);
        }
        
        if (null != source.getAgency())
        {
            target.setAgency(wrapper.wrap(source.getAgency()));
        }
        target.setBillTime(null == source.getBillTime() ? null : source.getBillTime().getTime());
    }
    
}
