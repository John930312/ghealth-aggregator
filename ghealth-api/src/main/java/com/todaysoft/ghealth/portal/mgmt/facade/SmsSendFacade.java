package com.todaysoft.ghealth.portal.mgmt.facade;

import com.todaysoft.ghealth.mgmt.request.MaintainOrderRequest;
import com.todaysoft.ghealth.mybatis.model.Agency;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.model.SmsSend;
import com.todaysoft.ghealth.service.IAgencyService;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.service.ISMSSendService;
import com.todaysoft.ghealth.service.sms.SampleDeliveredSend;
import com.todaysoft.ghealth.service.sms.SampleSignedSend;
import com.todaysoft.ghealth.utils.IdGen;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @Author: xjw
 * @Date: 2018/7/17 14:41
 */
@Component
public class SmsSendFacade
{
    @Autowired
    private SampleSignedSend sampleSignedSend;
    
    @Autowired
    private SampleDeliveredSend sampleDeliveredSend;
    
    @Autowired
    private IAgencyService agencyService;
    
    @Autowired
    private ISMSSendService sendService;
    
    @Autowired
    private IOrderService orderService;
    
    private final String AGENCY_TEMPLATE_ID = "237038";
    
    public void reportGeneratedSendToAgcy(MaintainOrderRequest request)
    {
        Agency targetAgency = agencyService.get(request.getAgencyId());
        
        SmsSend data = new SmsSend();
        
        data.setSended(false);
        data.setStatus("3");
        data.setId(IdGen.uuid());
        data.setCreateTime(new Date());
        data.setTemplateId(AGENCY_TEMPLATE_ID);
        data.setAgencyId(request.getAgencyId());
        data.setPhone(targetAgency.getContactPhone());
        
        sendService.create(data);
    }
    
    public void sampleSignedSend(MaintainOrderRequest request)
    {
        List<Order> orders = wrap(request);
        if (CollectionUtils.isEmpty(orders))
        {
            return;
        }
        orders.forEach(x -> sampleSignedSend.sending(x));
    }
    
    public void sampleDeliveredSend(MaintainOrderRequest request)
    {
        List<Order> orders = wrap(request);
        if (CollectionUtils.isEmpty(orders))
        {
            return;
        }
        orders.forEach(x -> sampleDeliveredSend.sending(x));
        
    }
    
    private List<Order> wrap(MaintainOrderRequest request)
    {
        
        String codes = request.getOrderIds();
        
        if (StringUtils.isEmpty(codes))
        {
            return Collections.emptyList();
        }
        
        return Arrays.asList(codes.split("-")).stream().map(x -> orderService.getByCode(x)).collect(toList());
    }
    
}
