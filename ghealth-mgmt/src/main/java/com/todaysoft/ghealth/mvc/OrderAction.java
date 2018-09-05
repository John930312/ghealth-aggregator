package com.todaysoft.ghealth.mvc;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import com.todaysoft.ghealth.mgmt.model.OrderReportStreamDTO;
import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.model.OrderSearcher;
import com.todaysoft.ghealth.model.email.Email;
import com.todaysoft.ghealth.model.order.ReportGenerateTask;
import com.todaysoft.ghealth.mvc.view.DownloadFileView;
import com.todaysoft.ghealth.service.IEmailService;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.IMessageService;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import com.todaysoft.ghealth.support.ServiceException;
import com.todaysoft.ghealth.utils.ExportExcel;
import com.todaysoft.ghealth.utils.SendEmailUtil;

@Controller
@RequestMapping("/order")
public class OrderAction extends BaseAction
{
    @Autowired
    private IOrderService service;
    
    @Autowired
    private IMessageService messageService;
    
    @Autowired
    private DownloadFileView downloadFileView;
    
    @Autowired
    private IEmailService emailService;
    
    private static Logger logger = LoggerFactory.getLogger(OrderAction.class);
    
    @RequestMapping("/list.jsp")
    public String list(OrderSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Order> pagination = service.search(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-searcher", searcher);
        return "order/order_list";
    }
    
    @RequestMapping("/specialList.jsp")
    public String specialList(OrderSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Order> pagination = service.searchSpecialList(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-searcher", searcher);
        return "order/order_special_list";
    }
    
    @RequestMapping(value = "/display.jsp", method = RequestMethod.GET)
    public String display(String id, ModelMap model)
    {
        model.addAttribute("data", service.get(id));
        model.addAttribute("orderHistories", service.getOrderHistoriesByOrderId(id));
        String testingDataDetails = service.dataDetails(id);
        model.addAttribute("list", StringUtils.isEmpty(testingDataDetails) ? "[]" : testingDataDetails);
        return "order/order_details";
    }
    
    @RequestMapping(value = "/showCanReportOrders.jsp", method = RequestMethod.GET)
    public String showCanReportOrders(Order data, ModelMap model)
    {
        model.addAttribute("datas", service.getOrdersByIds(data.getId()));
        return "order/order_reports";
    }
    
    @RequestMapping(value = "/downloadReport.jsp", method = RequestMethod.GET)
    public String downloadReports(OrderSearcher searcher, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        List<Order> orders = service.list(searcher);
        String fileName = "订单列表" + "_" + new SimpleDateFormat("yyyyMMdd").format(new Date()).toString() + "_"
            + new SimpleDateFormat("HHmm").format(new Date()).toString() + ".xlsx";
        new ExportExcel(null, Order.class).setDataList(service.setOtherVal(orders)).write(response, fileName).dispose();
        return "redirect:/order/list.jsp";
    }
    
    @RequestMapping(value = "/report_dialog.jsp", method = RequestMethod.GET)
    public String report(String id, ModelMap model)
    {
        Order order = service.get(id);
        model.addAttribute("data", order);
        return "order/order_report_dialog";
    }
    
    @RequestMapping("/report/details.do")
    @ResponseBody
    public Map<String, Object> reportDetails(String reportGenerateTaskId)
    {
        Map<String, Object> rsp = new HashMap<String, Object>();
        
        try
        {
            ReportGenerateTask task = service.getReportGenerateTask(reportGenerateTaskId);
            rsp.put("success", true);
            rsp.put("task", task);
        }
        catch (ServiceException e)
        {
            rsp.put("success", false);
            rsp.put("message", messageService.getMessage(e.getErrorCode()));
        }
        catch (Exception e)
        {
            rsp.put("success", false);
            rsp.put("message", e.getMessage());
        }
        
        return rsp;
    }
    
    @RequestMapping("/report/generate.do")
    @ResponseBody
    public Map<String, Object> generateReport(String id, ModelMap model)
    {
        Map<String, Object> rsp = new HashMap<String, Object>();
        
        try
        {
            String taskId = service.generateReport(id);
            rsp.put("success", true);
            rsp.put("taskId", taskId);
        }
        catch (ServiceException e)
        {
            rsp.put("success", false);
            rsp.put("message", messageService.getMessage(e.getErrorCode()));
        }
        catch (Exception e)
        {
            rsp.put("success", false);
            rsp.put("message", e.getMessage());
        }
        
        return rsp;
    }
    
    @RequestMapping("/report/regenerate.do")
    @ResponseBody
    public Map<String, Object> generateRegeport(String id, ModelMap model)
    {
        Map<String, Object> rsp = new HashMap<String, Object>();
        
        try
        {
            String taskId = service.regenerateReport(id);
            rsp.put("success", true);
            rsp.put("taskId", taskId);
        }
        catch (ServiceException e)
        {
            rsp.put("success", false);
            rsp.put("message", messageService.getMessage(e.getErrorCode()));
        }
        catch (Exception e)
        {
            rsp.put("success", false);
            rsp.put("message", e.getMessage());
        }
        
        return rsp;
    }


    @RequestMapping("/report/downloadForCode.do")
    public View downloadForCode(String id, String type, ModelMap model)
    {
        Order order = service.get(id);
        OrderReportStreamDTO report = service.getReport(id, type);
        model.put("name", order.getCode() + report.getSuffix());
        model.addAttribute("inputStream", new ByteArrayInputStream(report.getContent()));
        return downloadFileView;
    }

    @RequestMapping("/report/download.do")
    @ResponseBody
    public String download(String id, String type, ModelMap model)
    {
        return service.getReportUrl(id, type);
    }
    
    @RequestMapping("/redirect.do")
    public String redirectBack(ModelMap model, HttpSession session)
    {
        return redirectList(model, session, "/order/list.jsp");
    }
    
    @RequestMapping("/report/generates.jsp")
    @ResponseBody
    public Map<String, Object> generateRegeports(String ids, ModelMap model)
    {
        Map<String, Object> rsp = new HashMap<String, Object>();
        
        try
        {
            String taskIds = service.generateReports(ids);
            rsp.put("success", true);
            rsp.put("taskIds", taskIds);
        }
        catch (ServiceException e)
        {
            rsp.put("success", false);
            rsp.put("message", messageService.getMessage(e.getErrorCode()));
        }
        catch (Exception e)
        {
            rsp.put("success", false);
            rsp.put("message", e.getMessage());
        }
        
        return rsp;
    }
    
    @RequestMapping("/report/reportsDetails.do")
    @ResponseBody
    public Map<String, Object> reportsDetails(String reportGenerateTaskIds)
    {
        Map<String, Object> rsp = new HashMap<String, Object>();
        
        try
        {
            Map<String, Object> map = service.getReportGenerateTasks(reportGenerateTaskIds);
            rsp.put("success", true);
            rsp.putAll(map);
        }
        catch (ServiceException e)
        {
            rsp.put("success", false);
            rsp.put("message", messageService.getMessage(e.getErrorCode()));
        }
        catch (Exception e)
        {
            rsp.put("success", false);
            rsp.put("message", e.getMessage());
        }
        
        return rsp;
    }
    
    @RequestMapping(value = "/reports_dialog.jsp", method = RequestMethod.GET)
    public String reports(String ids, ModelMap model)
    {
        List<Order> orders = service.getOrdersByIds(ids);
        model.addAttribute("orders", orders);
        return "order/order_reports_dialog";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String midify(String id, ModelMap model)
    {
        Order order = service.get(id);
        model.addAttribute("data", order);
        return "order/order_form";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String midify(Order data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session, "/order/list.jsp");
    }
    
    @ResponseBody
    @RequestMapping(value = "/code_unique.do")
    public boolean isUniqueCode(String id, String code)
    {
        return service.isUniqueCode(id, code);
    }
    
    @ResponseBody
    @RequestMapping(value = "/sendEmail.do", method = RequestMethod.POST)
    public Boolean sendEmail(String id, ModelMap model, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException
    {
        logger.error("开始发送邮件");
        Boolean data = true;
        Order order = service.get(id);
        if (StringUtils.isNotEmpty(order.getCustomer().getEmail()))
        {
            String toEmail = order.getCustomer().getEmail();
            String name = order.getCustomer().getName();
            String pageName = order.getProduct().getName();
            OrderReportStreamDTO report = service.getReport(id, "pdf");
            
            byte[] bfile = report.getContent();
            
            String filePath = OrderAction.class.getResource("/").getPath();
            String fileName = order.getCustomer().getName();
            
            logger.error("开始生成文件");
            
            BufferedOutputStream bos = null;
            FileOutputStream fos = null;
            File file = null;
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String str = sdf.format(date);
            try
            {
                File dir = new File(filePath);
                if (!dir.exists() && dir.isDirectory())
                {//判断文件目录是否存在
                    dir.mkdirs();
                }
                
                file = new File(filePath + File.separator + str + ".pdf");
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                bos.write(bfile);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (bos != null)
                {
                    try
                    {
                        bos.close();
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                }
                if (fos != null)
                {
                    try
                    {
                        fos.close();
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
            
            List<Email> emails = emailService.getList();
            int rando = emails.size();
            if (rando != 0)
            {
                Random random = new Random();
                int s = random.nextInt(rando) % (rando + 1) + rando;
                Email email = emails.get(s - 1);
                try
                {
                    SendEmailUtil.sendEmail(email, toEmail, name, filePath + str + ".pdf", pageName);
                    logger.error("发送");
                }
                catch (MessagingException e)
                {
                    e.printStackTrace();
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                logger.error("邮件发送");
                file.delete();
            }
            return data;
        }
        else
        {
            data = false;
            return data;
        }
        
    }
    
    @RequestMapping(value = "/cancel.jsp", method = RequestMethod.POST)
    public String cancel(Order order, ModelMap model, HttpSession session)
    {
        service.cancel(order);
        return redirectList(model, session, "/order/list.jsp");
    }
    
    @RequestMapping(value = "/getOrder", method = RequestMethod.POST)
    @ResponseBody
    public Order getOrder(String id)
    {
        Order order = service.get(id);
        return order;
    }
    
    @ResponseBody
    @RequestMapping(value = "/sendMessage.do", method = RequestMethod.POST)
    public Boolean sendMessage(String id, ModelMap model, HttpServletRequest request)
    {
        Boolean data = true;
        Order order = service.get(id);
        if (StringUtils.isNotEmpty(order.getAgency().getContactPhone()))
        {
            service.sendMessageToAgency(order);
            return data;
        }
        else
        {
            data = false;
            return data;
        }
    }
    
    @RequestMapping("/upload_report.do")
    @ResponseBody
    public Map<String, Object> uploadReport(Order order, MultipartFile uploadData)
    {
        Map<String, Object> mesMap = new HashMap<>();
        try
        {
            service.uploadReport(order, uploadData);
            mesMap.put("mess", "success");
        }
        catch (ServiceException e)
        {
            mesMap.put("mess", e.getErrorCode());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            mesMap.put("mess", e.getMessage());
            e.printStackTrace();
        }
        return mesMap;
    }
    
    @RequestMapping(value = "/delete.jsp", method = RequestMethod.POST)
    public String delete(String id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return redirectList(model, session, "/order/list.jsp");
    }
    
    @RequestMapping("getOrderById.do")
    @ResponseBody
    public Order getOrderById(String id)
    {
        return service.get(id);
    }

    @RequestMapping("/reload.do")
    public String reload(ModelMap model, HttpSession session)
    {
        return redirectList(model, session, "/order/specialList.jsp");
    }

    @RequestMapping("/reload_list.do")
    public String reload_list(ModelMap model, HttpSession session)
    {
        return redirectList(model, session, "/order/list.jsp");
    }

    @RequestMapping("/setVigilance.do")
    @ResponseBody
    public boolean setVigilance(Order request)
    {
        return service.setVigilance(request);
    }

}
