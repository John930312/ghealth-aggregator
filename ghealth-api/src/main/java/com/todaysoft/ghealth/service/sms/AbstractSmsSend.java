package com.todaysoft.ghealth.service.sms;

import com.todaysoft.ghealth.config.RootContext;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.model.ShortMessage;
import com.todaysoft.ghealth.mybatis.model.ShortMessageCon;
import com.todaysoft.ghealth.mybatis.model.SmsSend;
import com.todaysoft.ghealth.service.ISMSSendService;
import com.todaysoft.ghealth.service.IShortMessageService;
import com.todaysoft.ghealth.utils.IdGen;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * @Author: xjw
 * @Date: 2018/7/16 14:33
 */
public abstract class AbstractSmsSend implements SMSSend
{
    @Autowired
    private ISMSSendService sendService;
    
    /**
     * 可以发送短信标示
     */
    protected final String NOTIFY_ENABLED = "1";
    
    /**
     * 短信发送事件：签收
     */
    protected final String SAMPLE_SIGNED_SEND = "1";
    
    /**
     * 短信发送事件：送检
     */
    protected final String SAMPLE_DELIVERED_SEND = "2";
    
    /**
     * 短信发送事件：报告
     */
    protected final String REPORT_GENERATED_SEND = "3";
    
    /**
     * 全局短信配置key
     */
    private final String AGENCY_GLOBAL = "global";
    
    /**
     * 代理定制短信配置key
     */
    private final String AGENCY_CUSTOMIZED = "customized";
    
    /**
     * 第三方短信API
     */
    private CcpRestApi ccpRestApi = RootContext.getBean(CcpRestApi.class);
    
    private IShortMessageService shortMessageService = RootContext.getBean(IShortMessageService.class);
    
    protected ShortMessageCon shortMessageCon;
    
    private Order order;
    
    public void sending(Order order)
    {
        this.order = order;
        
        String agencyId = order.getAgency().getId();
        
        Map<String, Object> customizedMap = getAgencyIdsOnHaveCustomSMS(true);
        
        List<String> agencyIds = (List<String>)customizedMap.get(AGENCY_CUSTOMIZED);
        
        //代理商定制
        if (!CollectionUtils.isEmpty(agencyIds) && agencyIds.contains(agencyId))
        {
            ShortMessage shortMessage = shortMessageService.getByAgencyId(agencyId);
            shortMessageCon = JsonUtils.fromJson(shortMessage.getConfigDetails(), ShortMessageCon.class);
            send();
        }
        else
        {
            //全局配置
            Map<String, Object> globalMap = getAgencyIdsOnHaveCustomSMS(false);
            shortMessageCon = (ShortMessageCon)globalMap.get(AGENCY_GLOBAL);
            send();
        }
    }
    
    /**
     * 发送短信
     * @param notifyTargets
     */
    protected void sendTo(List<String> notifyTargets, String customerTemplateId, String agencyTemplateId, String status)
    {
        notifyTargets.forEach(x -> {
            switch (x)
            {
                case "1":
                {
                    SmsSend data = new SmsSend();
                    
                    data.setSended(false);
                    data.setStatus(status);
                    data.setId(IdGen.uuid());
                    data.setCreateTime(new Date());
                    data.setTemplateId(agencyTemplateId);
                    data.setAgencyId(order.getAgency().getId());
                    data.setPhone(order.getAgency().getContactPhone());
                    
                    sendService.create(data);
                    break;
                }
                case "2":
                {
                    Optional.ofNullable(order.getCustomer().getPhone()).ifPresent(a -> ccpRestApi.messageSend(a, customerTemplateId, new String[] {}));
                    break;
                }
                default:
                {
                    break;
                }
            }
        });
        
    }
    
    /**
     * ture 开通短信定制
     * @param flag
     * @return
     */
    private Map<String, Object> getAgencyIdsOnHaveCustomSMS(boolean flag)
    {
        Map<String, Object> map = new HashMap<>();
        List<ShortMessage> lists = shortMessageService.getShortMessage(flag);
        
        if (CollectionUtils.isEmpty(lists))
        {
            return Collections.EMPTY_MAP;
        }
        
        if (flag)
        {
            map.put(AGENCY_CUSTOMIZED, lists.stream().map(x -> x.getAgencyId()).collect(toList()));
        }
        else
        {
            map.put(AGENCY_GLOBAL, JsonUtils.fromJson(lists.get(0).getConfigDetails(), ShortMessageCon.class));
        }
        
        return map;
    }
    
}
