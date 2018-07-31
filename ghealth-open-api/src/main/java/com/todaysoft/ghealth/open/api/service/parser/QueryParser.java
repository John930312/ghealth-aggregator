package com.todaysoft.ghealth.open.api.service.parser;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.hsgene.restful.request.Orderby;
import com.hsgene.restful.request.QueryRequest;
import com.todaysoft.ghealth.open.api.mybatis.model.OrderbyClause;
import com.todaysoft.ghealth.open.api.mybatis.model.Query;

public abstract class QueryParser<S extends QueryRequest, T extends Query>
{
    @SuppressWarnings("unchecked")
    public T parse(S source)
    {
        if (null == source)
        {
            return null;
        }

        Class<T> targetClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];

        try
        {
            T target = targetClass.newInstance();
            String[] ignoreProperties = getCopyIgnoreProperties();
            ignoreProperties = ArrayUtils.add(ignoreProperties, "orderbys");

            BeanUtils.copyProperties(source, target, ignoreProperties);
            setCopyIgnoreProperties(source, target);

            Query query = target;
            QueryRequest request = source;

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

            return target;
        }
        catch (InstantiationException e)
        {
            throw new IllegalStateException();
        }
        catch (IllegalAccessException e)
        {
            throw new IllegalStateException();
        }
    }

    protected String[] getCopyIgnoreProperties()
    {
        return null;
    }

    protected void setCopyIgnoreProperties(S source, T target)
    {
        //
    }
}
