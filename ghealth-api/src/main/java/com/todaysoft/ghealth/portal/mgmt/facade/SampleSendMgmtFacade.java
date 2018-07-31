package com.todaysoft.ghealth.portal.mgmt.facade;

import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Order;
import com.todaysoft.ghealth.mgmt.request.MaintainSimpleRequest;
import com.todaysoft.ghealth.mgmt.request.QueryOrdersRequest;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.ISampleSendService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.OrderWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Component
public class SampleSendMgmtFacade {
    @Autowired
    private OrderWrapper wrapper;

    @Autowired
    private ISampleSendService service;

    @Autowired
    private IAccountService accountService;

    public PagerResponse<Order> pager(QueryOrdersRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher, "startCreateTime", "endStartTime");
        wrapSearcher(request, searcher);
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Order> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Order>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.Order> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Order> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<Order>(result);
    }

    private String transferLongToDate(String dateFormat, Long millSec)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    private void wrapSearcher(QueryOrdersRequest request, OrderSearcher searcher)
    {
        if (StringUtils.isNotEmpty(request.getEndStartTime()))
        {
            searcher.setEndStartTime(transferLongToDate("yyyy-MM-dd 23:59:59", Long.valueOf(request.getEndStartTime())));
        }
        if (StringUtils.isNotEmpty(request.getStartCreateTime()))
        {
            searcher.setStartCreateTime(transferLongToDate("yyyy-MM-dd 00:00:00", Long.valueOf(request.getStartCreateTime())));
        }
    }



    public Map<String,String> modifySendStatus(MaintainSimpleRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());

        if (null == account || StringUtils.isEmpty(account.getId()))
        {
            throw new IllegalStateException();
        }



        if (StringUtils.isNotEmpty(request.getOrderCodes()))
        {
           return service.sampleSendOperation(request.getOrderCodes(),request.getStatus(),account.getName());
        }
        return Collections.emptyMap();
    }

}
