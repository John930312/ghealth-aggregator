package com.todaysoft.ghealth.open.api.service.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.hsgene.restful.request.Orderby;
import com.todaysoft.ghealth.open.api.mybatis.model.OrderQuery;
import com.todaysoft.ghealth.open.api.mybatis.model.OrderbyClause;
import com.todaysoft.ghealth.open.api.restful.request.OrderQueryRequest;

@Component
public class OrderQueryParser
{
    public OrderQuery parse(OrderQueryRequest request)
    {
        if (null == request)
        {
            throw new IllegalArgumentException();
        }

        OrderQuery query = new OrderQuery();
        BeanUtils.copyProperties(request, query, "reportGenerateTimeStart", "reportGenerateTimeEnd", "orderbys");

        if (!StringUtils.isEmpty(request.getReportGenerateTimeStart()))
        {
            try
            {
                Date reportGenerateTimeStart = DateUtils.parseDate(request.getReportGenerateTimeStart(), "yyyy-MM-dd HH:mm:ss");
                reportGenerateTimeStart = DateUtils.truncate(reportGenerateTimeStart, Calendar.DATE);
                query.setReportGenerateTimeStart(reportGenerateTimeStart);

            }
            catch (ParseException e)
            {
                //
            }
        }

        if (!StringUtils.isEmpty(request.getReportGenerateTimeEnd()))
        {
            try
            {
                Date reportGenerateTimeEnd = DateUtils.parseDate(request.getReportGenerateTimeEnd(), "yyyy-MM-dd HH:mm:ss");
                reportGenerateTimeEnd = DateUtils.addDays(DateUtils.truncate(reportGenerateTimeEnd, Calendar.DATE), 1);
                query.setReportGenerateTimeEnd(reportGenerateTimeEnd);
            }
            catch (ParseException e)
            {
                //
            }
        }

        if (!CollectionUtils.isEmpty(request.getOrderbys()))
        {
            OrderbyClause orderby;
            List<OrderbyClause> orderbys = new ArrayList<>();

            for (Orderby ob : request.getOrderbys())
            {
                orderby = new OrderbyClause();
                orderby.setField(ob.getField());
                orderby.setAsc(null == ob.getAsc() || ob.getAsc());
                orderbys.add(orderby);
            }

            query.setOrderbys(orderbys);
        }

        return query;
    }
}
