package com.todaysoft.ghealth.mvc;

import com.todaysoft.ghealth.base.response.model.Statistics;
import com.todaysoft.ghealth.model.statistics.StatisticsExcel;
import com.todaysoft.ghealth.model.statistics.StatisticsSearcher;
import com.todaysoft.ghealth.service.IStatisticsService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.utils.ExportExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/statistics")
public class StatisticsAction extends BaseAction {
    @Autowired
    private IStatisticsService service;

    @RequestMapping("/list.jsp")
    public String paginations(StatisticsSearcher searcher, ModelMap model, HttpSession session)
    {
        //X轴时间信息
        String nowX = service.getXAxis();
        String nowDayX = service.getXAxisDay();
        //Y轴数据
        //按月
        String salepriceSeries = service.getSampleNumber();
        //按日
        String dayStatistics = service.getSampleNumberByDay();

        if(searcher.getHid()==null){
            if(searcher.getEndTime()==null&&searcher.getEndTime()==null){
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MONTH, 0);
                c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
                searcher.setStartTime(c.getTime());
                searcher.setEndTime(new Date());
            }
        }




        Statistics statistics = service.get(searcher);
        model.addAttribute("xAxis",nowX);
        model.addAttribute("xAxisA",nowDayX);
        model.addAttribute("salepriceSeries",salepriceSeries);
        model.addAttribute("dayStatistics",dayStatistics);
        model.addAttribute("searcher", searcher);
        model.addAttribute("statistics",statistics);
        return "statistics/statistics_list";
    }

    @RequestMapping("/statisticsDownload.jsp")
    public void statisticsDownload(StatisticsSearcher searcher, HttpServletResponse response, ModelMap model, HttpSession session) throws FileNotFoundException {
        try {
            String excelName = "统计列表"+"_"+new SimpleDateFormat("yyyyMMdd").format(
                    new Date()).toString()+"_"+new SimpleDateFormat("hhmm").format(
                    new Date()).toString();
            response.reset();//设置为没有缓存
            response.setContentType("application/x-download;charset=GBK");
            response.setHeader("Content-disposition", "attachment; filename="
                    +new String(excelName.getBytes("gb2312"), "ISO-8859-1")  + ".xls");
            OutputStream fos=null;
            fos=response.getOutputStream();
            String[] headers = {"订单编号", "客户姓名","检测项目","纸质报告","下单机构","价格","提交时间","订单状态"};
            ExportExcelUtils eeu = new ExportExcelUtils();
            HSSFWorkbook workbook = new HSSFWorkbook();
            List<String> list = new ArrayList<>();
            list.add("2");
            list.add("3");
            list.add("4");
            list.add("6");
            Map<String,String> map = new HashMap<>();
            map.put("2","订单提交");
            map.put("3","订单签收");
            map.put("4","订单寄送");
            map.put("6","生成报告");
            List<String> set = new ArrayList<>(map.keySet());
            for(int i = 0;i<set.size();i++) {
                String status = set.get(i);
                List<List<String>> data = new ArrayList<List<String>>();
                searcher.setTitle(status);
                data=service.list(searcher);

                eeu.exportExcel(workbook, i, map.get(status), headers, data, fos);
            }

            //原理就是将所有的数据一起写入，然后再关闭输入流。
            workbook.write(fos);
            fos.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @RequestMapping("/order_statistics.do")
    public String priceOrderList(String status, String date, HttpServletRequest request, ModelMap model)
    {
        String year = date.split("-")[0];
        String month= date.split("-")[1];
        List<StatisticsExcel> list = service.getList(status,year,month);
        model.addAttribute("list",list);

        return "statistics/statistics_order_list";
    }

    @RequestMapping("/order_statistics_day.do")
    public String orderList(String status, String date, HttpServletRequest request, ModelMap model)
    {
        String year = date.split("-")[0];
        String month= date.split("-")[1];
        String day= date.split("-")[2];


        List<StatisticsExcel> list = service.getLists(status,year,month,day);
        model.addAttribute("list",list);

        return "statistics/statistics_order_list";
    }

    @RequestMapping("/statisticsReportDownload.jsp")
    public void statisticsReportDownload(StatisticsSearcher searcher, HttpServletResponse response, ModelMap model, HttpSession session) throws FileNotFoundException
    {

        try {
            String excelName = "报表统计"+"_"+new SimpleDateFormat("yyyyMMdd").format(
                    new Date()).toString()+"_"+new SimpleDateFormat("hhmm").format(
                    new Date()).toString();
            response.reset();//设置为没有缓存
            response.setContentType("application/x-download;charset=GBK");
            response.setHeader("Content-disposition", "attachment; filename="
                    +new String(excelName.getBytes("gb2312"), "ISO-8859-1")  + ".xls");
            OutputStream fos=null;
            fos=response.getOutputStream();
            String[] headers = {"代理商", "新客户增加数量","下单数量","签收数量","寄送数量","出报告数量","充值金额","消费金额","当前余额"};
            ExportExcelUtils eeu = new ExportExcelUtils();
            HSSFWorkbook workbook = new HSSFWorkbook();

            List<List<String>> data = new ArrayList<List<String>>();
            data = service.reportList(searcher);
            eeu.exportExcel(workbook, 0, "报表统计", headers, data, fos);

            //原理就是将所有的数据一起写入，然后再关闭输入流。
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
