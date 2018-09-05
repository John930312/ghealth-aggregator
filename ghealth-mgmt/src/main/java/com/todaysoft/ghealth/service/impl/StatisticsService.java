package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.Statistics;
import com.todaysoft.ghealth.mgmt.request.QueryOrderHistoryRequest;
import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.model.OrderHistory;
import com.todaysoft.ghealth.model.statistics.StatisticsExcel;
import com.todaysoft.ghealth.model.statistics.StatisticsModel;
import com.todaysoft.ghealth.model.statistics.StatisticsSearcher;
import com.todaysoft.ghealth.service.*;
import com.todaysoft.ghealth.service.wrapper.OrderWrapper;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StatisticsService implements IStatisticsService {
    @Autowired
    private IOrderService service;

    @Autowired
    private IDictService dictService;

    @Autowired
    private OrderWrapper orderWrapper;

    @Autowired
    private Gateway gateway;

    @Override
    public String getXAxis()
    {
        Calendar currenttime = Calendar.getInstance();
        currenttime.add(Calendar.MONTH, -5);
        //X轴
        String nowX = currenttime.get(Calendar.YEAR) + "-" + (currenttime.get(Calendar.MONTH) + 1);
        nowX = "'" + nowX + "'";
        for (int i = 0; i < 5; i++)
        {
            currenttime.add(Calendar.MONTH, 1);
            nowX =
                    nowX + "," + "'" + currenttime.get(Calendar.YEAR) + "-" + (currenttime.get(Calendar.MONTH) + 1) + "'";

        }
        nowX = "[" + nowX + "]";
        return nowX;
    }

    @Override
    public String getXAxisDay()
    {
        Calendar currenttime = Calendar.getInstance();
        currenttime.add(Calendar.DATE, -5);
        //X轴
        String nowX = currenttime.get(Calendar.YEAR) + "-" + (currenttime.get(Calendar.MONTH) + 1) + "-" + currenttime.get(Calendar.DATE);
        nowX = "'" + nowX + "'";
        for (int i = 0; i < 5; i++)
        {
            currenttime.add(Calendar.DATE, 1);
            nowX =
                    nowX + "," + "'" + currenttime.get(Calendar.YEAR) + "-" + (currenttime.get(Calendar.MONTH) + 1)+ "-" + currenttime.get(Calendar.DATE) + "'";

        }
        nowX = "[" + nowX + "]";
        return nowX;
    }

    @Override
    public String getSampleNumber()
    {
        List<StatisticsModel> models = new ArrayList<StatisticsModel>();


        Map<String,String> map = new HashMap<>();
        map.put("2","订单提交");
        map.put("3","订单签收");
        map.put("4","订单寄送");
        map.put("6","生成报告");

        for(String state : map.keySet())
        {
            StatisticsModel model = new StatisticsModel();
            model.setStatus(Integer.valueOf(state));
            model.setName(map.get(state));
            ArrayList<Double> saledata = new ArrayList<Double>();//Y轴data数据
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MONTH, -5);
            for (int i = 0; i < 6; i++) {
                List<OrderHistory> orderHistories =
                        service.getOrderHistories(state,
                                String.valueOf(now.get(Calendar.YEAR)),
                                String.valueOf(now.get(Calendar.MONTH) + 1));
                Integer number = 0;
                if (orderHistories != null) {
                    number = orderHistories.size();
                }
                saledata.add(number.doubleValue());
                now.add(Calendar.MONTH, 1);
            }
            model.setData(saledata);
            models.add(model);
        }

        return JsonUtils.toJson(models);
    }

    @Override
    public String getSampleNumberByDay(){
        List<StatisticsModel> models = new ArrayList<StatisticsModel>();
        Map<String,String> map = new HashMap<>();
        map.put("2","订单提交");
        map.put("3","订单签收");
        map.put("4","订单寄送");
        map.put("6","生成报告");
        for(String status :  map.keySet()) {
            StatisticsModel model = new StatisticsModel();
            model.setName(map.get(status));
            model.setStatus(Integer.valueOf(status));
            ArrayList<Double> saledata = new ArrayList<Double>();//Y轴data数据
            Calendar now = Calendar.getInstance();
            now.add(Calendar.DATE, -5);
            for (int i = 0; i < 6; i++) {
                List<OrderHistory> orderHistories =
                        service.getOrderHistoryLists(status,
                                String.valueOf(now.get(Calendar.YEAR)),
                                String.valueOf(now.get(Calendar.MONTH) + 1),
                                String.valueOf(now.get(Calendar.DATE)));
                Integer number = 0;
                if (orderHistories != null) {
                    number = orderHistories.size();
                }
                saledata.add(number.doubleValue());
                now.add(Calendar.DATE, 1);
            }
            model.setData(saledata);
            models.add(model);
        }
        return JsonUtils.toJson(models);
    }

    @Override
    public Statistics get(StatisticsSearcher searcher)
    {
        QueryOrderHistoryRequest request = orderWrapper.searcherOrderHistoryWarp(searcher);
        ObjectResponse<Statistics> response =
                gateway.request("/mgmt/order/getStatisticsDetails", request, new ParameterizedTypeReference<ObjectResponse<Statistics>>()
                {
                });

        return response.getData();
    }

    @Override
    public List<List<String>> list(StatisticsSearcher searcher)
    {
        QueryOrderHistoryRequest request = orderWrapper.searcherOrderHistoryWarp(searcher);
        ListResponse<List<String>> response =
                gateway.request("/mgmt/order/getExcels", request, new ParameterizedTypeReference<ListResponse<List<String>>>()
                {
                });
        if (null == response.getData())
        {
            return Collections.emptyList();
        }
        return response.getData();
    }



    @Override
    public List<StatisticsExcel> getList(String name,String year, String month){


        List<OrderHistory> orderHistories = service.getOrderHistories(name, year, month);
        List<StatisticsExcel> statisticsExcels = new ArrayList<>();
        for(OrderHistory orderHistory:orderHistories){
            Order order = service.get(orderHistory.getOrderId());
            StatisticsExcel statisticsExcel = new StatisticsExcel();
            statisticsExcel.setOrderId(order.getCode());
            statisticsExcel.setCustomerName(order.getCustomer().getName());
            statisticsExcel.setAgencyName(order.getAgency().getName());
            statisticsExcel.setProductName(order.getProduct().getName());
            statisticsExcel.setActualPrice(order.getActualPrice().toString());
            statisticsExcels.add(statisticsExcel);
        }
        return statisticsExcels;
    }

    @Override
    public List<StatisticsExcel> getLists(String name,String year, String month,String day){
        List<OrderHistory> orderHistories = service.getOrderHistoryLists(name, year, month,day);
        List<StatisticsExcel> statisticsExcels = new ArrayList<>();
        for(OrderHistory orderHistory:orderHistories){
            Order order = service.get(orderHistory.getOrderId());
            StatisticsExcel statisticsExcel = new StatisticsExcel();
            statisticsExcel.setOrderId(order.getCode());
            statisticsExcel.setCustomerName(order.getCustomer().getName());
            statisticsExcel.setAgencyName(order.getAgency().getName());
            statisticsExcel.setProductName(order.getProduct().getName());
            statisticsExcel.setActualPrice(order.getActualPrice().toString());
            statisticsExcels.add(statisticsExcel);
        }
        return statisticsExcels;
    }

    @Override
    public List<List<String>> reportList(StatisticsSearcher searcher)
    {
        QueryOrderHistoryRequest request = orderWrapper.searcherOrderHistoryWarp(searcher);
        ListResponse<List<String>> response =
                gateway.request("/mgmt/order/getStatisticsExcels", request, new ParameterizedTypeReference<ListResponse<List<String>>>()
                {
                });
        if (null == response.getData())
        {
            return Collections.emptyList();
        }
        return response.getData();
    }
}
