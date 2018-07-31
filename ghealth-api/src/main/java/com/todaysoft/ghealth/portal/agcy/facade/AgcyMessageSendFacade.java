package com.todaysoft.ghealth.portal.agcy.facade;

import com.aliyun.oss.OSSClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.agcy.request.MaintainAgcyMessageSendRequest;
import com.todaysoft.ghealth.agcy.request.QueryAgcyMessageSendRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.MessageSend;
import com.todaysoft.ghealth.config.AliyunOSSConfig;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.MessageSendSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.IMessageSendService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.MessageSendWrapper;
import com.todaysoft.ghealth.utils.DateOperateUtils;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class AgcyMessageSendFacade {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private MessageSendWrapper wrapper;

    @Autowired
    private IMessageSendService messageSendService;

    @Autowired
    private AliyunOSSConfig config;


    public PagerResponse<MessageSend> pager(QueryAgcyMessageSendRequest request)
    {

        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());

        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }

        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        MessageSendSearcher searcher = new MessageSendSearcher();
        BeanUtils.copyProperties(request, searcher, "startTime", "endTime");
        wrapperSearcher(request, searcher);
        searcher.setAgencyId(account.getAgencyId());
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.MessageSend> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.MessageSend>(messageSendService);
        Pager<com.todaysoft.ghealth.mybatis.model.MessageSend> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<MessageSend> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<MessageSend>(result);
    }


    private String transferLongToDate(String dateFormat, Long millSec)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    private void wrapperSearcher(QueryAgcyMessageSendRequest request, MessageSendSearcher searcher)
    {
        if (StringUtils.isNotEmpty(request.getEndTime()))
        {
            searcher.setEndTime(transferLongToDate("yyyy-MM-dd 23:59:59", Long.valueOf(request.getEndTime())));
        }
        if (StringUtils.isNotEmpty(request.getStartTime()))
        {
            searcher.setStartTime(transferLongToDate("yyyy-MM-dd 00:00:00", Long.valueOf(request.getStartTime())));
        }
    }

    public ObjectResponse<MessageSend> get(MaintainAgcyMessageSendRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.MessageSend messageSend = messageSendService.get(request.getId());

        if (StringUtils.isNotEmpty(messageSend.getAddress())){
            String storageDetails = messageSend.getAddress();
            Map<String, String> attributes = JsonUtils.fromJson(storageDetails, new TypeReference<Map<String, String>>()
            {
            });

            OSSClient client = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            URL url = client.generatePresignedUrl(attributes.get("bucketName"), attributes.get("objectKey"), DateOperateUtils.addOneSecond(new Date(), 60 * 3));
            String ur1 = url.toString();
            messageSend.setUrl(ur1);
        }

        return new ObjectResponse<MessageSend>(wrapper.wrap(messageSend));
    }
}
