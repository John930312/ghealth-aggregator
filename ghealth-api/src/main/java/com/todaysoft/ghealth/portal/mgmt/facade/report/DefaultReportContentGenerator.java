package com.todaysoft.ghealth.portal.mgmt.facade.report;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.CollectionUtils;

import com.todaysoft.document.generate.sdk.request.TextBookmarkContent;
import com.todaysoft.ghealth.config.RootContext;
import com.todaysoft.ghealth.mybatis.model.Customer;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.model.OrderHistory;
import com.todaysoft.ghealth.mybatis.model.Product;
import com.todaysoft.ghealth.service.IDictService;
import com.todaysoft.ghealth.service.impl.AreaService;

public class DefaultReportContentGenerator extends AbstractReportContentsGenerator
{
    private IDictService dictService = RootContext.getBean(IDictService.class);
    
    private AreaService areaService = RootContext.getBean(AreaService.class);
    
    @Override
    protected List<TextBookmarkContent> generateTextBookmarkContents(ReportGenerateContext context)
    {
        if (Objects.isNull(context) || Objects.isNull(context.getOrder()))
        {
            return null;
        }
        
        List<TextBookmarkContent> contents = new ArrayList<TextBookmarkContent>();
        
        Order order = context.getOrder();
        contents.add(new TextBookmarkContent("ORDER_CODE", order.getCode()));
        contents.add(new TextBookmarkContent("SAMPLE_TYPE", dictService.getText("SAMPLE_TYPE", order.getSampleType())));
        contents.add(new TextBookmarkContent("REPORT_GENERATED_TIME", formatAsDate(new Date())));
        
        if (!CollectionUtils.isEmpty(context.getOrderHistories()))
        {
            Map<String, OrderHistory> events = asEvents(context.getOrderHistories());
            
            OrderHistory event = events.get(OrderHistory.EVENT_ORDER_SUBMIT);
            
            if (null != event)
            {
                contents.add(new TextBookmarkContent("ORDER_SUBMIT_TIME", formatAsDate(event.getEventTime())));
            }
            
            event = events.get(OrderHistory.EVENT_SAMPLE_SIGN);
            
            if (null != event)
            {
                contents.add(new TextBookmarkContent("SAMPLE_SIGN_TIME", formatAsDate(event.getEventTime())));
            }
            
            event = events.get(OrderHistory.EVENT_SAMPLE_DELIVERY);
            
            if (null != event)
            {
                contents.add(new TextBookmarkContent("SAMPLE_DELIVERY_TIME", formatAsDate(event.getEventTime())));
            }
        }
        
        if (!Objects.isNull(context.getProduct()))
        {
            Product product = context.getProduct();
            contents.add(new TextBookmarkContent("PRODUCT_CODE", product.getCode()));
            contents.add(new TextBookmarkContent("PRODUCT_NAME", product.getName()));
        }
        
        if (!Objects.isNull(context.getCustomer()))
        {
            Customer customer = context.getCustomer();
            contents.add(new TextBookmarkContent("CUSTOMER_NAME", customer.getName()));
            contents.add(new TextBookmarkContent("CUSTOMER_SEX", dictService.getText("GENDER", customer.getSex())));
            contents.add(new TextBookmarkContent("CUSTOMER_BIRTHDAY", customer.getBirthday()));
            contents.add(new TextBookmarkContent("CUSTOMER_AGE", getAgeByBirthday(customer.getBirthday())));
            contents.add(new TextBookmarkContent("CUSTOMER_PHONE", customer.getPhone()));
            contents.add(new TextBookmarkContent("CUSTOMER_EMAIL", customer.getEmail()));
            contents.add(new TextBookmarkContent("CUSTOMER_VOCATION", customer.getVocation()));
            contents.add(new TextBookmarkContent("CUSTOMER_COMPANY", customer.getCompany()));
            contents.add(new TextBookmarkContent("CUSTOMER_NATION", dictService.getText("BASE_NATION", customer.getNation())));
            contents.add(new TextBookmarkContent("CUSTOMER_MARITAL_STATUS", dictService.getText("MARITAL_STATUS", customer.getMaritalStatus())));
            contents.add(new TextBookmarkContent("CUSTOMER_DISTRICT", customer.getDistrict()));
            contents.add(new TextBookmarkContent("CUSTOMER_ADDRESS", customer.getAddress()));
        }
        
        Map<String, String> genetypes = context.getGenetypes();
        
        if (!CollectionUtils.isEmpty(genetypes))
        {
            genetypes.forEach((locus, genetype) -> {
                contents.add(new TextBookmarkContent(MessageFormat.format("LOCUS_{0}_GENETYPE", locus), StringUtils.isEmpty(genetype) ? "-" : genetype));
            });
        }
        
        return contents;
    }
    
    private String getDistrict(Customer customer)
    {
        if (!StringUtils.isEmpty(customer.getProvince()))
        {
            return areaService.getDistrictFullName(customer.getProvince());
        }
        
        if (!StringUtils.isEmpty(customer.getCity()))
        {
            return areaService.getDistrictFullName(customer.getCity());
        }
        
        if (!StringUtils.isEmpty(customer.getCounty()))
        {
            return areaService.getDistrictFullName(customer.getCounty());
        }
        
        return "";
    }
    
    private Map<String, OrderHistory> asEvents(List<OrderHistory> histories)
    {
        Map<String, OrderHistory> events = new HashMap<String, OrderHistory>();
        
        histories.forEach(history -> {
            events.put(history.getEventType(), history);
        });
        
        return events;
    }
    
    private String formatAsDate(Date date)
    {
        if (!Objects.isNull(date))
        {
            return DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        
        return "";
    }
    
    private String getAgeByBirthday(String birthday)
    {
        try
        {
            if (StringUtils.isEmpty(birthday))
            {
                return "";
            }
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            
            if (Objects.isNull(date))
            {
                return "";
            }
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = instant.atZone(zoneId).toLocalDate();
            Integer birthday_year = localDate.getYear();
            Integer now = LocalDate.now().getYear();
            return String.valueOf(now - birthday_year);
        }
        catch (ParseException e)
        {
            return "";
        }
    }
}
