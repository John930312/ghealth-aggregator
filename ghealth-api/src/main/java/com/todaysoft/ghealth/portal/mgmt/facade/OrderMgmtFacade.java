package com.todaysoft.ghealth.portal.mgmt.facade;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.dto.OrderSimpleDTO;
import com.todaysoft.ghealth.base.response.model.Order;
import com.todaysoft.ghealth.base.response.model.OrderHistory;
import com.todaysoft.ghealth.config.AliyunOSSConfig;
import com.todaysoft.ghealth.config.RootContext;
import com.todaysoft.ghealth.mgmt.model.OrderReportStreamDTO;
import com.todaysoft.ghealth.mgmt.model.ReportGenerateTaskDTO;
import com.todaysoft.ghealth.mgmt.request.*;
import com.todaysoft.ghealth.mybatis.mapper.ReportGenerateTaskMapper;
import com.todaysoft.ghealth.mybatis.model.*;
import com.todaysoft.ghealth.mybatis.searcher.*;
import com.todaysoft.ghealth.portal.mgmt.MgmtErrorCode;
import com.todaysoft.ghealth.portal.mgmt.facade.report.ReportGenerateContext;
import com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm.AbstractTestingItemAlgorithm;
import com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm.TestingItemAlgorithmFactory;
import com.todaysoft.ghealth.portal.mgmt.facade.wrapper.OrderSimpleWrapper;
import com.todaysoft.ghealth.portal.mgmt.facade.wrapper.ReportGenerateTaskWrapper;
import com.todaysoft.ghealth.service.*;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.impl.core.ReportGenerator;
import com.todaysoft.ghealth.service.impl.core.TestingItemAlgorithmConfig;
import com.todaysoft.ghealth.service.model.AliyunStorageObject;
import com.todaysoft.ghealth.service.model.LocalStorageObject;
import com.todaysoft.ghealth.service.model.StorageObject;
import com.todaysoft.ghealth.service.sms.ReportGeneratedSend;
import com.todaysoft.ghealth.service.wrapper.OrderWrapper;
import com.todaysoft.ghealth.utils.DataStatus;
import com.todaysoft.ghealth.utils.IdGen;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class OrderMgmtFacade
{
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IOrderService service;
    
    @Autowired
    private OrderWrapper wrapper;
    
    @Autowired
    private ReportGenerateTaskWrapper reportGenerateTaskWrapper;
    
    @Autowired
    private OrderSimpleWrapper orderSimpleWrapper;
    
    @Autowired
    private IReportTemplateService reportTemplateService;
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private ITestingDataService testingDataService;
    
    @Autowired
    private ITestingItemService testingItemService;
    
    @Autowired
    private ITestingProductService testingProductService;
    
    @Autowired
    private IReportGenerateService reportGenerateService;
    
    @Autowired
    private IOrderHistoryService orderHistoryService;
    
    @Autowired
    private IAgencyService agencyService;
    
    @Autowired
    private AgencyBillFacade agencyBillFacade;
    
    @Autowired
    private IObjectStorageService objectStorageService;
    
    @Autowired
    private IItemLocusService testingItemLocusService;
    
    @Autowired
    private IAgencyBillService agencyBillService;
    
    @Autowired
    private ReportGeneratedSend reportGeneratedSend;
    
    @Autowired
    private AliyunOSSConfig config;

    @Autowired
    private ReportGenerateTaskMapper reportGenerateTaskMapper;

    public PagerResponse<Order> pager(QueryOrdersRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher, "startCreateTime", "endStartTime", "startReportTime", "endReportTime");
        wrapSearcher(request, searcher);
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Order> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Order>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.Order> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Order> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<Order>(result);
    }
    
    public ObjectResponse<Order> display(MaintainOrderRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderById(request.getId());
        return new ObjectResponse<Order>(wrapper.wrap(order));
    }
    
    public ListResponse<Order> list(QueryOrdersRequest request)
    {
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher, "startCreateTime", "endStartTime");
        wrapSearcher(request, searcher);
        return new ListResponse<Order>(wrapper.wrap(service.list(searcher)));
    }
    
    public ListResponse<OrderSimpleDTO> list(QueryOrderByCodesRequest request)
    {
        List<com.todaysoft.ghealth.mybatis.model.Order> orders = service.list(request.getCodes());
        return new ListResponse<OrderSimpleDTO>(orderSimpleWrapper.wrap(orders));
    }
    
    private String transferLongToDate(String dateFormat, Long millSec)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }
    
    private void wrapSearcher(QueryOrdersRequest request, OrderSearcher searcher)
    {
        if (StringUtils.isNotEmpty(request.getEndStartTime()))
        {
            searcher.setEndStartTime(transferLongToDate("yyyy-MM-dd 23:59:59", Long.valueOf(request.getEndStartTime())));
        }
        if (StringUtils.isNotEmpty(request.getStartCreateTime()))
        {
            searcher.setStartCreateTime(transferLongToDate("yyyy-MM-dd 00:00:00", Long.valueOf(request.getStartCreateTime())));
        }
        if (StringUtils.isNotEmpty(request.getEndReportTime()))
        {
            searcher.setEndReportTime(transferLongToDate("yyyy-MM-dd 23:59:59", Long.valueOf(request.getEndReportTime())));
        }
        if (StringUtils.isNotEmpty(request.getStartReportTime()))
        {
            searcher.setStartReportTime(transferLongToDate("yyyy-MM-dd 00:00:00", Long.valueOf(request.getStartReportTime())));
        }
    }
    
    public ListResponse<Order> getOrdersByIds(MaintainOrderRequest request)
    {
        List<Order> orders = new ArrayList<Order>();
        if (StringUtils.isNotEmpty(request.getId()))
        {
            String split = "-";
            for (String id : request.getId().split(split))
            {
                com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderById(id);
                orders.add(wrapper.wrap(order));
            }
            return new ListResponse<>(orders);
        }
        return new ListResponse<>(Collections.emptyList());
    }
    
    public ObjectResponse<String> generateReport(MaintainOrderRequest request)
    {
        ReportGenerateContext context = getReportGenerateContext(request);
        String taskId = reportGenerateService.generate(context);
        return new ObjectResponse<String>(taskId);
    }
    
    public ListResponse<String> generateReports(MaintainOrderRequest request)
    {
        List<String> taskIds = new ArrayList<>();
        String[] orderIds = request.getId().split("-");
        for (String orderId : orderIds)
        {
            if (StringUtils.isNotEmpty(orderId))
            {
                request.setId(orderId);
                ReportGenerateContext context = getReportGenerateContext(request);
                taskIds.add(reportGenerateService.generate(context));
            }
        }
        return new ListResponse<String>(taskIds);
    }
    
    public ObjectResponse<String> regenerateReport(MaintainOrderRequest request)
    {
        ReportGenerateContext context = getReportGenerateContext(request);
        String taskId = reportGenerateService.generate(context);
        return new ObjectResponse<String>(taskId);
    }
    
    public ObjectResponse<ReportGenerateTaskDTO> getReportGenerateTask(QueryDetailsRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new ServiceException(MgmtErrorCode.API_ILLEGAL_ARGUMENT);
        }
        
        ReportGenerateTask entity = reportGenerateService.getReportGenerateTask(request.getId());
        if (ReportGenerateTask.STATUS_SUCCESS.equals(entity.getStatus()))
        {
            com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderDataByTaskId(request.getId());
            
            boolean flag = false;
            if (order.getStatus().equals("4"))
            {
                flag = true;
            }
            if (DataStatus.ORDER_UPLOADED.equals(order.getStatus()))
            {
                service.setOrderStautsSuccessed(request);
                order.getReportGenerateTaskId();
                if (flag)
                {
                    reportGeneratedSend.sending(order);
                }
            }
        }
        return new ObjectResponse<ReportGenerateTaskDTO>(reportGenerateTaskWrapper.wrap(entity));
    }
    
    public ListResponse<ReportGenerateTaskDTO> getReportGenerateTasks(QueryDetailsRequest request)
    {
        if (StringUtils.isEmpty(request.getId()))
        {
            throw new ServiceException(MgmtErrorCode.API_ILLEGAL_ARGUMENT);
        }
        List<ReportGenerateTaskDTO> list = new ArrayList<>();
        String[] taskIds = request.getId().split("-");
        for (String taskId : taskIds)
        {
            if (StringUtils.isNotEmpty(taskId))
            {
                request.setId(taskId);
                com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderDataByTaskId(request.getId());
                ReportGenerateTask entity = reportGenerateService.getReportGenerateTask(request.getId());
                boolean flag = false;
                if (order.getStatus().equals("4"))
                {
                    flag = true;
                }
                
                if (ReportGenerateTask.STATUS_SUCCESS.equals(entity.getStatus()))
                {
                    service.setOrderStautsSuccessed(request);
                    if (flag)
                    {
                        reportGeneratedSend.sending(order);
                    }
                    
                }
                list.add(reportGenerateTaskWrapper.wrap(entity));
            }
            
        }
        return new ListResponse<ReportGenerateTaskDTO>(list);
    }
    
    public ObjectResponse<OrderReportStreamDTO> getReportStream(DownloadOrderReportRequest request)
    {
        if (StringUtils.isEmpty(request.getOrderId()))
        {
            throw new ServiceException(MgmtErrorCode.API_ILLEGAL_ARGUMENT);
        }
        
        com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderById(request.getOrderId());
        
        if (StringUtils.isEmpty(order.getReportGenerateTaskId()))
        {
            throw new ServiceException(MgmtErrorCode.REPORT_DOWNLOAD_UNGENERATED);
        }
        
        ReportGenerateTask task = reportGenerateService.getReportGenerateTask(order.getReportGenerateTaskId());
        
        if (null == task || !ReportGenerateTask.STATUS_SUCCESS.equals(task.getStatus()))
        {
            throw new ServiceException(MgmtErrorCode.REPORT_DOWNLOAD_UNGENERATED);
        }
        
        String objectStorageKey;
        
        if ("word".equals(request.getType()))
        {
            objectStorageKey = task.getWordFileUrl();
        }
        else
        {
            objectStorageKey = task.getPdfFileUrl();
        }
        
        if (StringUtils.isEmpty(objectStorageKey))
        {
            throw new ServiceException(MgmtErrorCode.REPORT_DOWNLOAD_UNGENERATED);
        }
        
        ObjectStorage objectStorage = objectStorageService.get(objectStorageKey);
        StorageObject object = getStorageObject(objectStorage);
        
        try
        {
            String suffix = object.getSuffix();
            byte[] bytes = object.getObjectContent();
            OrderReportStreamDTO stream = new OrderReportStreamDTO();
            stream.setSuffix(suffix);
            stream.setContent(bytes);
            if (request.getLoginType().equals("0"))
            {
                order.setReportDownloadCount(order.getReportDownloadCount() + 1);
                service.modify(order);
            }
            return new ObjectResponse<OrderReportStreamDTO>(stream);
        }
        catch (IOException e)
        {
            throw new ServiceException(MgmtErrorCode.REPORT_DOWNLOAD_IO_ERROR);
        }
    }
    
    private StorageObject getStorageObject(ObjectStorage entity)
    {
        if (null == entity)
        {
            return null;
        }
        
        Map<String, String> attributes = JsonUtils.fromJson(entity.getStorageDetails(), new TypeReference<Map<String, String>>()
        {
        });
        
        if (ObjectStorage.STORAGE_ALI_OSS.equals(entity.getStorageType()))
        {
            AliyunStorageObject object = new AliyunStorageObject(attributes.get("endpoint"), attributes.get("bucketName"), attributes.get("objectKey"));
            return object;
        }
        else if (ObjectStorage.STORAGE_LOCAL.equals(entity.getStorageType()))
        {
            String uri = attributes.get("uri");
            String path = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getServletContext().getRealPath(uri);
            LocalStorageObject object = new LocalStorageObject(uri, path);
            return object;
        }
        else
        {
            throw new IllegalStateException();
        }
    }
    
    private ReportGenerateContext getReportGenerateContext(MaintainOrderRequest request)
    {
        String id = request.getId();
        
        if (StringUtils.isEmpty(id))
        {
            throw new ServiceException(MgmtErrorCode.API_ILLEGAL_ARGUMENT);
        }
        
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderById(id);
        
        if (null == order)
        {
            throw new ServiceException(MgmtErrorCode.REPORT_GENERATE_ORDER_NOT_EXISTS);
        }
        
        List<LocusGenetype> genetypes = testingDataService.getOrderTestingData(order.getId());
        
        if (CollectionUtils.isEmpty(genetypes))
        {
            throw new ServiceException(MgmtErrorCode.REPORT_GENERATE_TESTING_DATA_NOT_EXISTS);
        }
        
        Customer customer = order.getCustomer();
        
        if (null == customer || StringUtils.isEmpty(customer.getId()))
        {
            throw new ServiceException(MgmtErrorCode.REPORT_GENERATE_CUSTOMER_NOT_EXISTS);
        }
        
        customer = customerService.get(customer.getId());
        
        if (null == customer)
        {
            throw new ServiceException(MgmtErrorCode.REPORT_GENERATE_CUSTOMER_NOT_EXISTS);
        }
        
        Product product = order.getProduct();
        
        if (null == product || StringUtils.isEmpty(product.getId()))
        {
            throw new ServiceException(MgmtErrorCode.REPORT_GENERATE_PRODUCT_NOT_EXISTS);
        }
        
        product = testingProductService.get(product.getId());
        
        if (null == product)
        {
            throw new ServiceException(MgmtErrorCode.REPORT_GENERATE_PRODUCT_NOT_EXISTS);
        }
        
        ReportTemplate template = reportTemplateService.getReportTemplate(product.getId(), null == order.getAgency() ? null : order.getAgency().getId());
        
        if (null == template || StringUtils.isEmpty(template.getTsdgKey()))
        {
            throw new ServiceException(MgmtErrorCode.REPORT_GENERATE_TEMPLATE_NOT_EXISTS);
        }
        
        List<com.todaysoft.ghealth.mybatis.model.TestingItem> testingItems = testingItemService.getItemsForProduct(product.getId());
        
        if (CollectionUtils.isEmpty(testingItems))
        {
            throw new ServiceException(MgmtErrorCode.REPORT_GENERATE_TESING_ITEMS_NOT_EXISTS);
        }
        
        if (StringUtils.isEmpty(customer.getSex()) && testingItemService.isRequiredSexForGenerate(testingItems))
        {
            throw new ServiceException(MgmtErrorCode.REPORT_GENERATE_CUSTOMER_SEX_UNDEFINED);
        }
        
        TestingItemAlgorithmConfig algorithmConfig;
        List<TestingItemAlgorithmConfig> testingItemAlgorithmConfigs = new ArrayList<>();
        for (com.todaysoft.ghealth.mybatis.model.TestingItem testingItem : testingItems)
        {
            AbstractTestingItemAlgorithm algorithm = TestingItemAlgorithmFactory.getAlgorithm(testingItem);
            
            algorithmConfig = algorithm.getTestingItemAlgorithmConfig(testingItem);
            ItemLocusSearcher searcher = new ItemLocusSearcher();
            searcher.setItemId(testingItem.getId());
            List<TestingItemLocus> records = testingItemLocusService.list(searcher);
            algorithmConfig.setAlgorithm(algorithm);
            algorithmConfig.setTestingItem(testingItem);
            algorithmConfig.setLocusConfig(algorithm.getLocusConfig(testingItem.getName(), records));
            
            testingItemAlgorithmConfigs.add(algorithmConfig);
        }
        
        Map<String, String> genetypeMappings = new HashMap<String, String>();
        genetypes.forEach(genetype -> genetypeMappings.put(genetype.getLocus(), genetype.getGenetype()));
        
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        String destUri = "/report/" + order.getCode();
        String destDirectory = ((ServletRequestAttributes)attributes).getRequest().getServletContext().getRealPath(destUri);
        
        ReportGenerateContext context = new ReportGenerateContext();
        context.setOrder(order);
        context.setProduct(product);
        context.setCustomer(customer);
        context.setGenetypes(genetypeMappings);
        //  context.setTestingItemEvaluateConfigs(testingItemEvaluateConfigs);
        context.setTestingItemAlgorithmConfigs(testingItemAlgorithmConfigs);
        context.setOperator(account);
        context.setTemplateKey(template.getTsdgKey());
        context.setDestUri(destUri);
        context.setDestDirectory(destDirectory);
        return context;
    }
    
    public ListResponse<OrderHistory> getOrderHistoriesByOrderId(MaintainOrderRequest request)
    {
        List<com.todaysoft.ghealth.mybatis.model.OrderHistory> list = orderHistoryService.getOrderHistoriesByOrderId(request.getId());
        return new ListResponse<>(wrapper.wrapOrderHistory(list));
    }
    
    @Transactional
    public String modify(MaintainOrderRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Order nowData = service.getOrderById(request.getId());
        if ("6".equals(nowData.getStatus()))
        {
            return request.getId();
        }
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        Agency agency = agencyService.get(request.getAgencyId());
        com.todaysoft.ghealth.mybatis.model.Order data = wrapOrder(request);
        if (service.canModify(data))
        {
            BigDecimal amountAfter = service.refundForOrderModify(agency, request, data, account.getName());
            service.placeForOrderModify(amountAfter, agency, request, data, account.getName());
        }
        data.setUpdateTime(new Date());
        data.setUpdatorName(account.getName());
        service.modify(data);
        return data.getId();
    }
    
    public ObjectResponse<Boolean> isUniqueCode(MaintainOrderRequest request)
    {
        Boolean isUniqueCode = service.isUniqueCode(request.getId(), request.getCode());
        return new ObjectResponse<>(isUniqueCode);
    }
    
    private com.todaysoft.ghealth.mybatis.model.Order wrapOrder(MaintainOrderRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Order data = new com.todaysoft.ghealth.mybatis.model.Order();
        Product product = new Product();
        product.setId(request.getProductId());
        data.setProduct(product);
        Customer customer = new Customer();
        customer.setId(request.getCustomerId());
        data.setCustomer(customer);
        data.setReportPrintRequired(request.getReportPrintRequired());
        data.setSampleType(request.getSampleType());
        data.setCode(request.getCode());
        data.setId(request.getId());
        data.setStatus(request.getStatus());
        data.setActualPrice(request.getActualPrice());
        return data;
    }
    
    public ListResponse<OrderHistory> getOrderHistories(MaintainOrderHistoryRequest request)
    {
        List<com.todaysoft.ghealth.mybatis.model.OrderHistory> list =
            orderHistoryService.getOrderHistories(request.getName(), request.getYear(), request.getMonth());
        return new ListResponse<>(wrapper.wrapOrderHistory(list));
    }
    
    public ListResponse<OrderHistory> getOrderHistoryLists(MaintainOrderHistoryRequest request)
    {
        List<com.todaysoft.ghealth.mybatis.model.OrderHistory> list =
            orderHistoryService.getOrderHistoryLists(request.getName(), request.getYear(), request.getMonth(), request.getDay());
        return new ListResponse<>(wrapper.wrapOrderHistory(list));
    }
    
    public ListResponse<OrderHistory> getOrderHistory(QueryOrderHistoryRequest request)
    {
        StatisticsSearcher searcher = new StatisticsSearcher();
        BeanUtils.copyProperties(request, searcher, "startTime", "endTime");
        wrapSearcherOrder(request, searcher);
        List<com.todaysoft.ghealth.mybatis.model.OrderHistory> list = orderHistoryService.getOrderHistory(searcher);
        return new ListResponse<>(wrapper.wrapOrderHistory(list));
    }
    
    private void wrapSearcherOrder(QueryOrderHistoryRequest request, StatisticsSearcher searcher)
    {
        if (StringUtils.isNotEmpty(request.getEndTime()))
        {
            searcher.setEndTime(transferLongToDate("yyyy-MM-dd 23:59:59", Long.valueOf(request.getEndTime())));
        }
        if (StringUtils.isNotEmpty(request.getStartTime()))
        {
            searcher.setStartTime(transferLongToDate("yyyy-MM-dd 00:00:00", Long.valueOf(request.getStartTime())));
        }
    }
    
    public ObjectResponse<String> getPath(MaintainOrderRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderById(request.getId());
        
        if (StringUtils.isEmpty(order.getReportGenerateTaskId()))
        {
            throw new ServiceException(MgmtErrorCode.REPORT_DOWNLOAD_UNGENERATED);
        }
        
        ReportGenerateTask task = reportGenerateService.getReportGenerateTask(order.getReportGenerateTaskId());
        
        if (null == task || !ReportGenerateTask.STATUS_SUCCESS.equals(task.getStatus()))
        {
            throw new ServiceException(MgmtErrorCode.REPORT_DOWNLOAD_UNGENERATED);
        }
        
        String uri = task.getPdfFileUrl();
        
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        String path = ((ServletRequestAttributes)attributes).getRequest().getServletContext().getRealPath(uri);
        return new ObjectResponse<String>(path);
    }
    
    @Transactional
    public String cancel(MaintainOrderRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        com.todaysoft.ghealth.mybatis.model.Order data = service.getOrderById(request.getId());
        
        if (canCancel(data))
        {
            Agency agency = agencyService.get(data.getAgency().getId());
            AgencyBill agencyBill = new AgencyBill();
            BigDecimal accountAmount = agency.getAccountAmount().add(data.getActualPrice());
            agencyBill.setAmountBefore(agency.getAccountAmount());
            
            agencyBill.setAgency(agency);
            agencyBill.setBillType(DataStatus.BILL_REFUND);
            agencyBill.setIncreased(DataStatus.BALANCE_PLUS);
            agencyBill.setAmountAfter(accountAmount);
            agencyBill.setEventDetails(data.getId());
            agencyBill.setTitle("订单取消返还" + data.getActualPrice() + "元");
            agencyBill.setBillTime(new Date());
            agencyBill.setOperateName(account.getName());
            agencyBillFacade.create(agencyBill);
            
            agency.setAccountAmount(accountAmount);
            agencyService.modify(agency, null);
        }
        data.setUpdateTime(new Date());
        data.setUpdatorName(account.getName());
        data.setStatus(DataStatus.ORDER_CANCELED);
        service.modify(data);
        return data.getId();
    }
    
    private boolean canCancel(com.todaysoft.ghealth.mybatis.model.Order data)
    {
        boolean flag = true;
        String status = data.getStatus();
        if (data.getActualPrice().compareTo(new BigDecimal(0)) == 0)
        {
            flag = false;
        }
        if (DataStatus.ORDER_DRAFT.equals(status))
        {
            flag = false;
        }
        return flag;
    }
    
    public ObjectResponse<Order> getByCode(MaintainOrderRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Order order = service.getByCode(request.getCode());
        return new ObjectResponse<Order>(wrapper.wrap(order));
    }
    
    public ObjectResponse<String> dataDetails(MaintainOrderRequest request)
    {
        String dataDetail = service.dataDetails(request.getId());
        return new ObjectResponse<String>(dataDetail);
    }
    
    public void upload(MaintainOrderRequest request) throws IOException
    {
        String suffix = request.getFileName().substring(request.getFileName().indexOf("."), request.getFileName().length());
        String destUri = "/report/" + request.getCode();
        RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
        String destDirectory = ((ServletRequestAttributes)attributes).getRequest().getServletContext().getRealPath(destUri);
        File f = new File(destDirectory);
        f.mkdirs();
        
        String fileName = new StringBuilder().append(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")).append(suffix).toString();
        File dest = new File(destDirectory, fileName);
        
        OutputStream out = null;
        try
        {
            out = new FileOutputStream(dest);
            out.write(request.getBytes());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
        handerOssUpload(request, destUri, dest);
    }

    private void handerOssUpload(MaintainOrderRequest request, String destUri, File dest)
    {
        ReportGenerator reportGenerator = RootContext.getBean(ReportGenerator.class);
        
        ReportGenerateContext context = new ReportGenerateContext();
        com.todaysoft.ghealth.mybatis.model.Order order = new com.todaysoft.ghealth.mybatis.model.Order();
        BeanUtils.copyProperties(request, order);
        order.setStatus(DataStatus.ORDER_FINISHED);
        context.setOrder(order);
        context.setDestUri(destUri);
        
        final List<File> files = Arrays.asList(dest);
        reportGenerator.generateSuccess(context, files, true);
        order = service.getOrderById(order.getId());
        reportGeneratedSend.sending(order);
    }
    
    public ListResponse<List<String>> getStatisticsExcels(QueryOrderHistoryRequest request)
    {
        StatisticsSearcher searcher = new StatisticsSearcher();
        BeanUtils.copyProperties(request, searcher, "startTime", "endTime");
        wrapSearcherOrder(request, searcher);
        
        Map<String, String> map = new HashMap<>();
        map.put("2", "订单提交");
        map.put("3", "订单签收");
        map.put("4", "订单寄送");
        map.put("6", "生成报告");
        
        List<String> agenciesIds = new ArrayList<String>();
        List<String> set = new ArrayList<>(map.keySet());
        for (int i = 0; i < set.size(); i++)
        {
            String status = set.get(i);
            List<List<String>> data = new ArrayList<List<String>>();
            searcher.setTitle(status);
            List<com.todaysoft.ghealth.mybatis.model.OrderHistory> orderHistories = orderHistoryService.getOrderHistory(searcher);
            if (!CollectionUtils.isEmpty(orderHistories))
            {
                for (com.todaysoft.ghealth.mybatis.model.OrderHistory orderHistory : orderHistories)
                {
                    com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderById(orderHistory.getOrderId());
                    if (!agenciesIds.contains(order.getAgency().getId()))
                    {
                        agenciesIds.add(order.getAgency().getId());
                    }
                    
                }
            }
        }
        
        List<List<String>> statisticsExcels = new ArrayList<>();
        Agency agency;
        CustomerSearcher customerSearcher = new CustomerSearcher();
        customerSearcher.setStartCreateTime(searcher.getStartTime());
        customerSearcher.setEndStartTime(searcher.getEndTime());
        int newCustomerNums = 0;
        
        Integer quantity = 0;
        Integer signNum = 0;
        Integer sendNum = 0;
        Integer reportedNum = 0;
        
        AgencyBillSearcher billSearcher = new AgencyBillSearcher();
        billSearcher.setStartTime(searcher.getStartTime());
        billSearcher.setEndTime(searcher.getEndTime());
        BigDecimal totalRecharge = BigDecimal.valueOf(0);
        BigDecimal totalConsume = BigDecimal.valueOf(0);
        
        for (String agencyId : agenciesIds)
        {
            List<String> list = new ArrayList<>();
            agency = agencyService.getReportData(agencyId);
            list.add(agency.getName());
            
            //新增客户数
            customerSearcher.setAgencyId(agencyId);
            int newCustomerNum = customerService.customerCount(customerSearcher);
            list.add(String.valueOf(newCustomerNum));
            newCustomerNums += newCustomerNum;
            
            //提交、签收、寄出、报告数量
            OrderSearcher orderSearcher = new OrderSearcher();
            orderSearcher.setAgencyId(agencyId);
            List<String> orderIds = service.orderIdList(orderSearcher);
            if (!CollectionUtils.isEmpty(orderIds))
            {
                for (int i = 0; i < set.size(); i++)
                {
                    String status = set.get(i);
                    searcher.setTitle(status);
                    Integer totalNum = statisticsNum(orderIds, searcher);
                    list.add(String.valueOf(totalNum));
                    switch (i)
                    {
                        case 0:
                            quantity += totalNum;
                            break;
                        case 1:
                            signNum += totalNum;
                            break;
                        case 2:
                            sendNum += totalNum;
                            break;
                        case 3:
                            reportedNum += totalNum;
                            break;
                    }
                }
            }
            
            //充值、消费金额
            billSearcher.setAgencyId(agencyId);
            List<AgencyBill> agencyBills = agencyBillService.list(billSearcher);
            BigDecimal recharge = BigDecimal.valueOf(0);
            BigDecimal consume = BigDecimal.valueOf(0);
            for (AgencyBill agencyBill : agencyBills)
            {
                String str = agencyBill.getBillType();
                BigDecimal calculate;
                if (agencyBill.getBillType().equals("1"))
                {
                    calculate = agencyBill.getAmountAfter().subtract(agencyBill.getAmountBefore());
                    recharge = recharge.add(calculate);
                }
                else
                {
                    calculate = agencyBill.getAmountBefore().subtract(agencyBill.getAmountAfter());
                    consume = consume.add(calculate);
                }
            }
            list.add(String.valueOf(recharge));
            list.add(String.valueOf(consume));
            totalRecharge = totalRecharge.add(recharge);
            totalConsume = totalConsume.add(consume);
            
            //账户余额
            list.add(agency.getAccountAmount().toString());
            statisticsExcels.add(list);
        }
        
        List<String> list = new ArrayList<>();
        list.add("总汇");
        list.add(String.valueOf(newCustomerNums));
        list.add(String.valueOf(quantity));
        list.add(String.valueOf(signNum));
        list.add(String.valueOf(sendNum));
        list.add(String.valueOf(reportedNum));
        list.add(String.valueOf(totalRecharge));
        list.add(String.valueOf(totalConsume));
        statisticsExcels.add(list);
        
        return new ListResponse<>(statisticsExcels);
    }
    
    private Integer statisticsNum(List<String> orderIds, StatisticsSearcher searcher)
    {
        Integer num = 0;
        List<String> orderHistories = orderHistoryService.getOrderIds(searcher);
        if (!CollectionUtils.isEmpty(orderHistories))
        {
            for (String orderId : orderHistories)
            {
                if (orderIds.contains(orderId))
                {
                    num++;
                }
            }
        }
        return num;
    }
    
    public void delete(MaintainOrderRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        Agency agency = agencyService.get(request.getAgencyId());
        com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderById(request.getId());
        order.setDeleted(true);
        order.setUpdateTime(new Date());
        order.setUpdatorName(account.getName());
        order.setDeleteTime(new Date());
        order.setDeletorName(account.getName());
        orderHistoryService.deleteByOrderId(request.getId());
        service.modify(order);
    }

    public PagerResponse<Order> specialPager(QueryOrdersRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher, "startCreateTime", "endStartTime", "startReportTime", "endReportTime");
        wrapSearcher(request, searcher);
        Pager<com.todaysoft.ghealth.mybatis.model.Order> pager = service.getSpecialPager(searcher, pageNo, pageSize);
        Pager<Order> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<Order>(result);
    }

    @Transactional
    public void uploadReport(MaintainOrderRequest request)
    {
        String objectKey = request.getObjectKey();
        ObjectStorage objectStorage = new ObjectStorage();
        objectStorage.setStorageType(ObjectStorage.STORAGE_ALI_OSS);
        Map<String, String> details = new HashMap<String, String>();
        details.put("endpoint", config.getEndpoint());
        details.put("bucketName", config.getBucketName());
        details.put("objectKey", objectKey);

        //ghealth_object_storage 插入数据
        String objectStorageId = IdGen.uuid();
        ObjectStorage entity = new ObjectStorage();
        entity.setId(objectStorageId);
        entity.setStorageType(ObjectStorage.STORAGE_ALI_OSS);
        entity.setStorageDetails(JsonUtils.toJson(details));
        reportGenerateTaskMapper.insertObjectStorageRecord(entity);

        //ghealth_order_report_generate_task 插入数据
        ReportGenerateTask task = new ReportGenerateTask();
        Date timestamp = new Date();
        task.setId(IdGen.uuid());
        task.setStatus(ReportGenerateTask.STATUS_SUCCESS);
        task.setCreateTime(timestamp);
        task.setCreatorName("管理员");
        task.setFinishTime(timestamp);

        if (objectKey.contains(".pdf"))
        {
            task.setPdfFileUrl(objectStorageId);
        }
        else
        {
            task.setWordFileUrl(objectStorageId);
        }
        reportGenerateTaskMapper.insert(task);

        com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderById(request.getId());
        order.setReportGenerateTaskId(task.getId());
        order.setReportGenerateTime(timestamp);
        order.setStatus(DataStatus.ORDER_FINISHED);
        service.modify(order);
    }
}
