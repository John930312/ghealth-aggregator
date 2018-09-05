package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.base.response.model.Statistics;
import com.todaysoft.ghealth.model.statistics.StatisticsExcel;
import com.todaysoft.ghealth.model.statistics.StatisticsSearcher;

import java.util.List;

public interface IStatisticsService {
    //获取X轴时间信息
    String getXAxis();

    String getXAxisDay();

    //获取样本个数基本信息按月
    String getSampleNumber();

    //获取样本个数基本信息按日
    String getSampleNumberByDay();

    Statistics get(StatisticsSearcher searcher);

    List<List<String>> list(StatisticsSearcher searcher);

    List<StatisticsExcel> getList(String name,String year,String month);

    List<StatisticsExcel> getLists(String name,String year,String month,String day);

    List<List<String>> reportList(StatisticsSearcher searcher);
}
