package com.todaysoft.ghealth.portal.wechat.facade;

import com.hsgene.restful.response.DataResponse;
import com.todaysoft.ghealth.base.response.model.Order;
import com.todaysoft.ghealth.mybatis.model.AccountData;
import com.todaysoft.ghealth.portal.wechat.WechatErrorCode;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.service.IWechatAccountService;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.wrapper.mobileWrapper.MobileOrderWrapper;
import com.todaysoft.ghealth.utils.DataStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class WechatOrderFacade
{
    @Autowired
    private MobileOrderWrapper mobileOrderWrapper;
    
    @Autowired
    private IOrderService service;
    
    @Autowired
    private IWechatAccountService accountService;
    
    public DataResponse<List<Order>> getOrders(String token)
    {
        String accountId = accountService.getAccountIdByToken(token);
        
        if (StringUtils.isEmpty(accountId))
        {
            throw new ServiceException(WechatErrorCode.INVALID_TOKEN);
        }
        AccountData accountData = accountService.getAccountDataByAccountId(accountId);

        if(!accountData.getEnabled())
        {
            throw new ServiceException(WechatErrorCode.INVALID_ACCOUNT);
        }
        
        List<com.todaysoft.ghealth.mybatis.model.Order> orders = service.getOrdersByCustomerPhone(accountData.getPhone());
        if (CollectionUtils.isEmpty(orders))
        {
            return new DataResponse<>();
        }
        return new DataResponse<>(mobileOrderWrapper.wrap(orders));
    }
    
    public DataResponse<Order> getOrderById(String id) throws IOException
    {
        com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderById(id);
        if (Objects.isNull(order))
        {
            return new DataResponse<>();
        }
        Order mobileOrder = mobileOrderWrapper.wrap(order);
        if (DataStatus.ORDER_FINISHED.equals(mobileOrder.getStatus()))
        {
            mobileOrder.setReportContents(service.getPdfReportContents(mobileOrder.getId()));
        }
        return new DataResponse<>(mobileOrder);
    }
    
    public DataResponse<Boolean> existDatas(String phone)
    {
        Boolean exist = CollectionUtils.isEmpty(service.getOrdersByCustomerPhone(phone)) ? false : true;
        return new DataResponse<>(exist);
    }
}
