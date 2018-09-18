package com.todaysoft.ghealth.service.job;

import com.todaysoft.ghealth.mybatis.model.SmsSend;
import com.todaysoft.ghealth.mybatis.searcher.SmsSendSearcher;
import com.todaysoft.ghealth.service.ISMSSendService;
import com.todaysoft.ghealth.service.sms.CcpRestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @Author: xjw
 * @Date: 2018/7/16 10:53
 */
@Component
@EnableScheduling
public class TimedMessage
{
    private static Logger logger = LoggerFactory.getLogger(TimedMessage.class);
    
    @Autowired
    private CcpRestApi ccpRestApi;
    
    @Autowired
    private ISMSSendService sendService;
    
    private final String MESSAGE_SEND_REPORT = "3";
    
    private Predicate<SmsSend> predicate = x -> MESSAGE_SEND_REPORT.equals(x.getStatus());
    
    /**
     * 定时任务，每天8:30
     */
    @Scheduled(cron = "0 30 8 ? * *")
    public void sendInMorning()
    {
        //报告
        messageSend(getWantedTime(12).stream().filter(predicate).collect(toList()));
    }
    
    /**
     * 定时任务，每天11:30
     */
    @Scheduled(cron = "0 30 11 ? * *")
    public void sendINoon()
    {
        //报告
        messageSend(getWantedTime(3).stream().filter(predicate).collect(toList()));
        //样本签收  寄送
        messageSend(getWantedTime(15).stream().filter(predicate.negate()).collect(toList()));
    }
    
    /**
     * 定时任务，每天17:30
     */
    @Scheduled(cron = "0 30 17 ? * *")
    public void sendInAfternoon()
    {
        messageSend(getWantedTime(6));
    }
    
    /**
     * 定时任务，每天20:30
     */
    @Scheduled(cron = "0 30 20 ? * *")
    public void sendInNight()
    {
        messageSend(getWantedTime(3));
    }
    
    /**
     * 定时任务，每天9:30
     */
//    @Scheduled(cron = "0 30 9 ? * *")
    @Scheduled(cron = "0 0/2 * ? * *")
    public void sendForFestival()
    {
        //节日问候
        List<SmsSend> smsSendList = getWantedFestivalTime(18);

        if (!CollectionUtils.isEmpty(smsSendList))
        {
            //根据模板分组
            Map<String, List<SmsSend>> agencyMap = smsSendList.stream().collect(groupingBy(SmsSend::getTemplateId));

            agencyMap.forEach((k, v) ->
            {
                //同模板去重复手机
                List<String> phones =  new ArrayList<String>();
                v.forEach(t -> {
                    if (!phones.contains(t.getPhone()))
                    {
                        ccpRestApi.messageSend(t.getPhone(), k, new String[] {String.valueOf(v.size())});
                        if (logger.isDebugEnabled())
                        {
                            logger.debug("messageSend: phone->" + t.getPhone() + " templateId->" + t + " agrs->" + v.size());
                        }
                        sendService.modify(t);
                        phones.add(t.getPhone());
                    }
                });

            });
        }
    }
    
    private List<SmsSend> getWantedTime(Integer minusHour)
    {
        LocalDateTime beforeNow = LocalDateTime.now().minus(minusHour, ChronoUnit.HOURS).plus(1, ChronoUnit.SECONDS);
        
        SmsSendSearcher searcher = new SmsSendSearcher();
        searcher.setBefore(Date.from(beforeNow.atZone(ZoneId.systemDefault()).toInstant()));
        searcher.setAfter(new Date());
        
        return sendService.getDatasInTime(searcher);
    }
    
    private List<SmsSend> getWantedFestivalTime(Integer minusHour)
    {
        LocalDateTime beforeNow = LocalDateTime.now().minus(minusHour, ChronoUnit.HOURS).plus(1, ChronoUnit.SECONDS);
        
        SmsSendSearcher searcher = new SmsSendSearcher();
        searcher.setBefore(Date.from(beforeNow.atZone(ZoneId.systemDefault()).toInstant()));
        searcher.setAfter(new Date());
        
        return sendService.getFestivalDatasInTime(searcher);
    }
    
    private void messageSend(List<SmsSend> datas)
    {
        //根据代理商分组
        Map<String, List<SmsSend>> agencyMap = datas.stream().collect(groupingBy(SmsSend::getAgencyId));
        
        agencyMap.forEach((k, v) -> {
            //根据短信事件分组
            Map<String, List<SmsSend>> statusMap = v.stream().collect(groupingBy(SmsSend::getStatus));
            statusMap.forEach((k1, v1) -> {
                ccpRestApi.messageSend(v.get(0).getPhone(), v1.get(0).getTemplateId(), new String[] {String.valueOf(v1.size())});
                if (logger.isDebugEnabled())
                {
                    logger.debug("messageSend: phone->" + v.get(0).getPhone() + " templateId->" + v1.get(0).getTemplateId() + " agrs->" + v1.size());
                }
            });
        });
        
        datas.forEach(x -> sendService.modify(x));
    }
}
