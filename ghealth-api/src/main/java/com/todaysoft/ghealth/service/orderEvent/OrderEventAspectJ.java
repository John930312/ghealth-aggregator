package com.todaysoft.ghealth.service.orderEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.model.OrderHistory;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.utils.DataStatus;

public abstract class OrderEventAspectJ
{
    public final String POINT_EVENTTYPE = "eventType";
    
    public final String POINT_HANDLER = "handler";
    
    public final String HANDLER_AGCY = "agcy";
    
    public final String HANDLER_MGMT = "mgmt";
    
    @Autowired
    private IAccountService accountService;
    
    @Pointcut("@annotation(com.todaysoft.ghealth.service.orderEvent.OrderEventLog)")
    public void pointcut()
    {
    }
    
    @AfterReturning(pointcut = "pointcut()", returning = "returnObj")
    public void after(JoinPoint point, Object returnObj)
    {
        try
        {
            Map<String, String> map = getServiceMthodEventType(point);
            switchOrderEvent(map, point, returnObj);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private List<String> getList()
    {
        List<String> list = new ArrayList<>();
        list.add(DataStatus.ORDER_SAVE);
        list.add(DataStatus.ORDER_SUBMIT);
        list.add(DataStatus.ORDER_CANCEL);
        list.add(DataStatus.ORDER_MODIFY);
        list.add(DataStatus.ORDER_GENERATE);
        list.add(DataStatus.ORDER_DONE);
        return list;
    }
    
    private void switchOrderEvent(Map<String, String> map, JoinPoint point, Object returnObj)
    {
        List<String> list = getList();
        String eventType = map.get(POINT_EVENTTYPE);
        if (list.contains(eventType))
        {
            //暂存 下单 取消
            setOrderStatusByMethodReturnString(point, map, returnObj);
        }
        else if (DataStatus.ORDER_SIGN.equals(eventType) || DataStatus.ORDER_SEND.equals(eventType) || DataStatus.ORDER_UPLOAD.equals(eventType))
        {
            //签收 寄送 上传
            setOrderStatusByMethodReturnMap(point, map, returnObj);
        }
        
    }
    
    protected void setOrderStatusByMethodReturnString(JoinPoint point, Map<String, String> map, Object returnObj)
    {
    }
    
    protected void setOrderStatusByMethodReturnMap(JoinPoint point, Map<String, String> map, Object returnObj)
    {
    }
    
    protected void setOrderEventStatus(String eventType, OrderHistory orderHistory)
    {
        switch (eventType)
        {
            case "1":
                orderHistory.setEventType(DataStatus.ORDER_SAVE);
                orderHistory.setTitle(DataStatus.ORDER_SAVE_TITLE);
                break;
            case "2":
                orderHistory.setEventType(DataStatus.ORDER_SUBMIT);
                orderHistory.setTitle(DataStatus.ORDER_SUBMIT_TITLE);
                break;
            case "3":
                orderHistory.setEventType(DataStatus.ORDER_SIGN);
                orderHistory.setTitle(DataStatus.ORDER_SIGN_TITLE);
                break;
            case "4":
                orderHistory.setEventType(DataStatus.ORDER_SEND);
                orderHistory.setTitle(DataStatus.ORDER_SEND_TITLE);
                break;
            case "5":
                orderHistory.setEventType(DataStatus.ORDER_UPLOAD);
                orderHistory.setTitle(DataStatus.ORDER_UPLOAD_TITLE);
                break;
            case "6":
                orderHistory.setEventType(DataStatus.ORDER_GENERATE);
                orderHistory.setTitle(DataStatus.ORDER_GENERATE_TITLE);
                break;
            case "7":
                orderHistory.setEventType(DataStatus.ORDER_CANCEL);
                orderHistory.setTitle(DataStatus.ORDER_CANCEL_TITLE);
                break;
            case "8":
                orderHistory.setEventType(DataStatus.ORDER_APPLICATION_REFUND);
                orderHistory.setTitle(DataStatus.ORDER_APPLICATION_REFUND_TITLE);
                break;
            case "9":
                orderHistory.setEventType(DataStatus.ORDER_CONFIRM_REFUND);
                orderHistory.setTitle(DataStatus.ORDER_CONFIRM_REFUND_TITLE);
                break;
            case "10":
                orderHistory.setEventType(DataStatus.REJECT_ORDER_APPLICATION_REFUND);
                orderHistory.setTitle(DataStatus.REJECT_ORDER_APPLICATION_REFUND_TITLE);
                break;
            case "12":
                orderHistory.setEventType(DataStatus.ORDER_MODIFY);
                orderHistory.setTitle(DataStatus.ORDER_MODIFY_TITLE);
                break;
            default:
                orderHistory.setEventType(DataStatus.ORDER_DONE);
                orderHistory.setTitle(DataStatus.ORDER_DONE_TITLE);
                break;
        }
    }
    
    protected String getManagemenOperatorName(JoinPoint point)
    {
        Object[] args = point.getArgs();
        for (Object arg : args)
        {
            if (arg instanceof SignatureTokenRequest)
            {
                SignatureTokenRequest data = (SignatureTokenRequest)arg;
                ManagementAccountDetails account = accountService.getManagementAccountDetails(data.getToken());
                return account.getName();
            }
        }
        return null;
    }
    
    protected String getAgencyOperatorName(JoinPoint point)
    {
        Object[] args = point.getArgs();
        for (Object arg : args)
        {
            if (arg instanceof SignatureTokenRequest)
            {
                SignatureTokenRequest data = (SignatureTokenRequest)arg;
                AgencyAccountDetails account = accountService.getAgencyAccountDetails(data.getToken());
                return account.getAccountName();
            }
        }
        return null;
    }
    
    protected Map<String, String> getServiceMthodEventType(JoinPoint joinPoint) throws Exception
    {
        Map<String, String> map = new HashMap<>();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String eventType = null;
        String handler = null;
        for (Method method : methods)
        {
            if (method.getName().equals(methodName))
            {
                Class<?>[] clazzs = method.getParameterTypes();
                
                if (clazzs.length == arguments.length)
                {
                    eventType = method.getAnnotation(OrderEventLog.class).eventType();
                    handler = method.getAnnotation(OrderEventLog.class).handler();
                    map.put(POINT_EVENTTYPE, eventType);
                    map.put(POINT_HANDLER, handler);
                    break;
                }
            }
        }
        return map;
    }
    
    protected String getOperatorName(Map<String, String> map, JoinPoint point)
    {
        String operatorName = null;
        if (map.get(POINT_HANDLER).equals(HANDLER_AGCY))
        {
            operatorName = getAgencyOperatorName(point);
        }
        else if (map.get(POINT_HANDLER).equals(HANDLER_MGMT))
        {
            operatorName = getManagemenOperatorName(point);
        }
        return operatorName;
    }
}
