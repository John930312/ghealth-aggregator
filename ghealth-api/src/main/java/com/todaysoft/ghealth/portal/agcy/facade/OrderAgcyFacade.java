package com.todaysoft.ghealth.portal.agcy.facade;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.todaysoft.ghealth.mybatis.mapper.AgencyAccountMapper;
import com.todaysoft.ghealth.mybatis.mapper.TestingEvaluateMapper;
import com.todaysoft.ghealth.mybatis.model.*;
import com.todaysoft.ghealth.service.*;
import com.todaysoft.ghealth.service.impl.ReportItemDescription;
import com.todaysoft.ghealth.service.impl.core.ItemLevelEvaluator;
import com.todaysoft.ghealth.service.impl.core.TestingItemEvaluateReferenceValue;
import com.todaysoft.ghealth.service.model.reportData.ItemData;
import com.todaysoft.ghealth.service.model.reportData.LocusData;
import com.todaysoft.ghealth.service.model.reportData.OrderData;
import com.todaysoft.ghealth.service.model.reportData.ReportData;
import com.todaysoft.ghealth.utils.DateOperateUtils;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.todaysoft.ghealth.agcy.request.MaintainOrderRequest;
import com.todaysoft.ghealth.agcy.request.QueryOrdersRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Order;
import com.todaysoft.ghealth.base.response.model.OrderUploadRequest;
import com.todaysoft.ghealth.mgmt.request.MaintainOrderUploadRequest;
import com.todaysoft.ghealth.mybatis.searcher.AgencyProductSearcher;
import com.todaysoft.ghealth.mybatis.searcher.CustomerSearcher;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import com.todaysoft.ghealth.portal.mgmt.facade.AgencyBillFacade;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.impl.ServiceException;
import com.todaysoft.ghealth.service.wrapper.OrderWrapper;
import com.todaysoft.ghealth.utils.DataStatus;
import com.todaysoft.ghealth.utils.IdGen;

@Component
public class OrderAgcyFacade
{
    @Autowired
    private IOrderService service;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private OrderWrapper orderWrapper;
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IAgencyProductService agencyProductService;
    
    @Autowired
    private IAgencyService agencyService;
    
    @Autowired
    private AgencyBillFacade agencyBillFacade;
    
    @Autowired
    private IOrderHistoryService orderHistoryService;
    
    @Autowired
    private AgencyAccountMapper agencyAccountMapper;
    
    @Autowired
    private ITestingProductService productService;
    
    @Autowired
    private TestingEvaluateMapper testingEvaluateMapper;
    
    @Autowired
    private ICancerService cancerService;
    
    @Autowired
    private IDictService dictService;
    
    public PagerResponse<Order> pager(QueryOrdersRequest request)
    {
        String agencyId;
        if (request.isLogin())
        {
            AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
            
            if (null == account || StringUtils.isEmpty(account.getAgencyId()))
            {
                throw new IllegalStateException();
            }
            agencyId = account.getAgencyId();
        }
        else
        {
            agencyId = request.getAgencyId();
        }
        
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        OrderSearcher searcher = searcherWarp(request);
        searcher.setAgencyId(agencyId);
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Order> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Order>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.Order> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Order> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), orderWrapper.wrap(pager.getRecords()));
        return new PagerResponse<Order>(result);
    }
    
    public String create(MaintainOrderRequest request)
    {
        if (service.isUniqueCode(request.getId(), request.getCode()))
        {
            AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
            
            if (null == account || StringUtils.isEmpty(account.getAgencyId()))
            {
                throw new IllegalStateException();
            }
            com.todaysoft.ghealth.mybatis.model.Order order = new com.todaysoft.ghealth.mybatis.model.Order();
            BeanUtils.copyProperties(request, order);
            order.setCreateTime(new Date());
            wrapOrder(order, account, request.getProduct().getId(), request.getCustomer().getId(), request.getStatus());
            service.create(order);
            return order.getId();
        }
        return null;
    }
    
    public ObjectResponse<Order> getById(MaintainOrderRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderById(request.getId());
        return new ObjectResponse<Order>(orderWrapper.wrap(order));
    }
    
    public void delete(MaintainOrderRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        com.todaysoft.ghealth.mybatis.model.Order data = service.getOrderById(request.getId());
        data.setUpdateTime(new Date());
        data.setUpdatorName(account.getAccountName());
        data.setDeleted(true);
        data.setDeleteTime(new Date());
        data.setDeletorName(account.getAccountName());
        orderHistoryService.deleteByOrderId(request.getId());
        service.modify(data);
    }
    
    @Transactional
    public String place(MaintainOrderRequest request)
    {
        
        if (StringUtils.isEmpty(request.getId()))
        {
            return create(request);
        }
        else
        {
            AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
            if (null == account || StringUtils.isEmpty(account.getAgencyId()))
            {
                throw new IllegalStateException();
            }
            Agency agency = agencyService.get(account.getAgencyId());
            com.todaysoft.ghealth.mybatis.model.Order data = service.getOrderById(request.getId());
            if (service.isUniqueCode(data.getId(), data.getCode()))
            {
                placeOrder(data, agency, account.getAccountName());
                data.setUpdateTime(new Date());
                data.setUpdatorName(account.getAccountName());
                return data.getId();
            }
            return null;
        }
    }
    
    @Transactional
    public String cancel(MaintainOrderRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Order data = service.getOrderById(request.getId());
        
        if (DataStatus.ORDER_PLACED.equals(data.getStatus()) && data.getActualPrice().compareTo(new BigDecimal(0)) != 0)
        {
            Agency agency = agencyService.get(account.getAgencyId());
            AgencyBill agencyBill = new AgencyBill();
            BigDecimal accountAmount = agency.getAccountAmount().add(data.getActualPrice());
            agencyBill.setAmountBefore(agency.getAccountAmount());
            
            agencyBill.setAgency(agency);
            agencyBill.setBillType(DataStatus.BILL_REFUND);
            agencyBill.setIncreased(DataStatus.BALANCE_PLUS);
            agencyBill.setAmountAfter(accountAmount);
            agencyBill.setEventDetails(MessageFormat.format("{0}-{1}", data.getId(), data.getProduct().getId()));
            agencyBill.setTitle("订单取消返还" + data.getActualPrice() + "元");
            agencyBill.setBillTime(new Date());
            agencyBill.setOperateName(account.getAccountName());
            agencyBillFacade.create(agencyBill);
            
            agency.setAccountAmount(accountAmount);
            agencyService.modify(agency, null);
        }
        data.setUpdateTime(new Date());
        data.setUpdatorName(account.getAccountName());
        data.setStatus(DataStatus.ORDER_CANCELED);
        service.modify(data);
        return data.getId();
    }
    
    public ListResponse<Order> list(QueryOrdersRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        request.setAgencyId(account.getAgencyId());
        return new ListResponse<Order>(orderWrapper.wrap(service.list(searcherWarp(request))));
    }
    
    public ObjectResponse<Boolean> isUniqueCode(@RequestBody QueryOrdersRequest request)
    {
        return new ObjectResponse<Boolean>(service.isUniqueCode(request.getId(), request.getOrderCode()));
    }
    
    private String transferLongToDate(String dateFormat, Long millSec)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }
    
    private OrderSearcher searcherWarp(QueryOrdersRequest request)
    {
        OrderSearcher searcher = new OrderSearcher();
        BeanUtils.copyProperties(request, searcher, "startCreateTime", "endStartTime", "startReportTime", "endReportTime");
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
        return searcher;
    }
    
    @Transactional
    public void createOrders(MaintainOrderUploadRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        
        if (!CollectionUtils.isEmpty(request.getList()))
        {
            for (int i = 0; i < request.getList().size(); i++)
            {
                OrderUploadRequest orderUpload = request.getList().get(i);
                List<String> codes = service.getCodes();
                if (!codes.contains(orderUpload.getId()))
                {
                    Customer temCostomer = new Customer(orderUpload.getCustomerName(), orderUpload.getPhone(), orderUpload.getEmail(),
                        String.valueOf(getText(orderUpload.getSex())), orderUpload.getBirthday());
                    Customer customer = findExistCustomer(account, temCostomer);
                    com.todaysoft.ghealth.mybatis.model.Order order = new com.todaysoft.ghealth.mybatis.model.Order();
                    order.setCode(orderUpload.getId());
                    Integer reportPrintRequired = getText(orderUpload.getWantPaper());
                    Integer sampleType = getText(orderUpload.getSampleType());
                    order.setReportPrintRequired(null == reportPrintRequired ? 0 : reportPrintRequired);
                    order.setSampleType(null == sampleType ? "1" : String.valueOf(sampleType));
                    String productCode = orderUpload.getProductId();
                    String realPid = agencyProductService.getIdByCode(productCode, account.getAgencyId());
                    order.setCreateTime(DateOperateUtils.addOneSecond(new Date(), i));
                    wrapOrder(order, account, realPid, customer.getId(), DataStatus.ORDER_DRAFT);
                    service.create(order);
                }
            }
        }
    }
    
    private void wrapOrder(com.todaysoft.ghealth.mybatis.model.Order order, AgencyAccountDetails account, String productId, String customerId, String status)
    {
        order.setId(IdGen.uuid());
        Agency agency = agencyService.get(account.getAgencyId());
        order.setAgency(agency);
        Product p = new Product();
        p.setId(productId);
        order.setProduct(p);
        Customer c = new Customer();
        c.setId(customerId);
        order.setCustomer(c);
        
        if (Objects.isNull(order.getActualPrice()))
        {
            AgencyProductSearcher searcher = new AgencyProductSearcher();
            searcher.setProductId(p.getId());
            searcher.setAgencyId(agency.getId());
            List<AgencyProduct> agencyProducts = agencyProductService.list(searcher);
            if (!CollectionUtils.isEmpty(agencyProducts))
            {
                order.setActualPrice(agencyProducts.get(0).getAgencyPrice());
            }
        }
        
        order.setCreatorName(account.getAccountName());
        order.setDeleted(false);
        order.setStatus(status);
        order.setReportDownloadCount(0);
        //下单
        if (DataStatus.ORDER_PLACED.equals(status))
        {
            placeOrder(order, agency, account.getAccountName());
        }
    }
    
    private com.todaysoft.ghealth.mybatis.model.Order wrapOrder(com.todaysoft.ghealth.mgmt.request.MaintainOrderRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Order data = new com.todaysoft.ghealth.mybatis.model.Order();
        BeanUtils.copyProperties(request, data, "request", "customer");
        Product product = new Product();
        product.setId(request.getProductId());
        data.setProduct(product);
        Customer customer = new Customer();
        customer.setId(request.getCustomerId());
        data.setCustomer(customer);
        return data;
    }
    
    private void setPlacedOrderValues(com.todaysoft.ghealth.mybatis.model.Order order, String accountName)
    {
        order.setSubmitTime(new Date());
        order.setSubmitorName(accountName);
        order.setStatus(DataStatus.ORDER_PLACED);
    }
    
    private void placeOrder(com.todaysoft.ghealth.mybatis.model.Order order, Agency agency, String accountName)
    {
        //代理商余额
        BigDecimal accountAmountBefore = agency.getAccountAmount();
        //代理商授权余额
        BigDecimal authorizationAmount = agency.getAuthorizationAmount();
        //订单价格
        BigDecimal orderPrice = order.getActualPrice();
        
        BigDecimal accountAmountAfter = accountAmountBefore.subtract(orderPrice);
        
        agency.setAccountAmount(accountAmountAfter);
        
        AgencyBill agencyBill = new AgencyBill();
        agencyBill.setAgency(agency);
        agencyBill.setBillType(DataStatus.BILL_PLACE_ORDER);
        agencyBill.setIncreased(DataStatus.BALANCE_REDUCE);
        agencyBill.setAmountAfter(accountAmountAfter);
        agencyBill.setAmountBefore(accountAmountBefore);
        agencyBill.setEventDetails(MessageFormat.format("{0}-{1}", order.getId(), order.getProduct().getId()));
        agencyBill.setTitle("下单扣除" + orderPrice + "元");
        agencyBill.setBillTime(new Date());
        agencyBill.setOperateName(accountName);
        agencyBillFacade.create(agencyBill);
        
        agencyService.modify(agency, null);
        setPlacedOrderValues(order, accountName);
        service.modify(order);
        
    }
    
    private Integer getText(String value)
    {
        if (value.equals("不需要"))
        {
            return 0;
        }
        else if (value.equals("需要"))
        {
            return 1;
        }
        else if (value.equals("口腔黏膜"))
        {
            return 1;
        }
        else if (value.equals("抗凝血"))
        {
            return 2;
        }
        else if (value.equals("其他"))
        {
            return 3;
        }
        else if (value.equals("男"))
        {
            return 1;
        }
        else if (value.equals("女"))
        {
            return 2;
        }
        return null;
    }
    
    public ListResponse<com.todaysoft.ghealth.base.response.model.OrderHistory> getOrderHistoriesByOrderId(com.todaysoft.ghealth.mgmt.request.MaintainOrderRequest request)
    {
        List<com.todaysoft.ghealth.mybatis.model.OrderHistory> list = orderHistoryService.getOrderHistoriesByOrderId(request.getId());
        return new ListResponse<>(orderWrapper.wrapOrderHistory(list));
    }
    
    public void createOrderAtMobile(MaintainOrderRequest request)
    {
        AgencyAccount account;
        if (StringUtils.isNotEmpty(request.getAgencyId()))
        {
            account = agencyAccountMapper.getAgencyPrimaryAccount(request.getAgencyId());
        }
        else
        {
            account = agencyAccountMapper.getAccountByAccountId(request.getAgencyAccountId());
        }
        
        AgencyAccountDetails details = new AgencyAccountDetails();
        details.setId(account.getId());
        details.setAgencyId(account.getAgencyId());
        details.setAccountName(account.getName());
        
        com.todaysoft.ghealth.mybatis.model.Order order = new com.todaysoft.ghealth.mybatis.model.Order();
        BeanUtils.copyProperties(request, order);
        Customer temCostomer = new Customer();
        BeanUtils.copyProperties(request.getCustomer(), temCostomer);
        Customer customer = findExistCustomer(details, temCostomer);
        order.setCreateTime(new Date());
        wrapOrder(order, details, request.getProduct().getId(), customer.getId(), DataStatus.ORDER_DRAFT);
        service.create(order);
    }
    
    private Customer findExistCustomer(AgencyAccountDetails account, Customer temCsutomer)
    {
        CustomerSearcher searcher = new CustomerSearcher(account.getAgencyId(), temCsutomer.getName(), temCsutomer.getPhone());
        List<Customer> customers = customerService.list(searcher);
        Customer customer = null;
        if (CollectionUtils.isEmpty(customers))
        {
            customer = new Customer();
            BeanUtils.copyProperties(temCsutomer, customer);
            customerService.create(customer, account);
        }
        else
        {
            customer = customers.get(0);
        }
        return customer;
    }
    
    public ObjectResponse<byte[]> getOrderReportDatas(MaintainOrderRequest request)
    {
        ReportData reportData = new ReportData();
        List<OrderData> datas = new ArrayList<>();
        if (StringUtils.isNotEmpty(request.getId()))
        {
            for (String orderId : request.getId().split("-"))
            {
                datas.add(getOrderReportDataByOrderId(orderId));
            }
        }
        if (CollectionUtils.isEmpty(datas))
        {
            return new ObjectResponse<byte[]>(null);
        }
        reportData.setCheckupList(datas);
        return new ObjectResponse<byte[]>(JsonUtils.toJson(reportData).getBytes());
    }
    
    private OrderData getOrderReportDataByOrderId(String orderId)
    {
        OrderData orderData = new OrderData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        
        com.todaysoft.ghealth.mybatis.model.Order order = service.getOrderById(orderId);
        if (null == order)
        {
            throw new ServiceException("can not find business");
        }
        
        Product product = order.getProduct();
        if (null == product)
        {
            throw new ServiceException("can not find product");
        }
        
        Customer customer = order.getCustomer();
        if (null == customer)
        {
            throw new ServiceException("can not find customer");
        }
        
        String sex = dictService.getText("GENDER", customer.getSex());
        String sampleType = dictService.getText("SAMPLE_TYPE", order.getSampleType());
        orderData.setExamineBarcode(order.getCode());
        orderData.setCheckupName(product.getName());
        orderData.setName(customer.getName());
        orderData.setSex(sex);
        orderData.setSampleType(sampleType);
        orderData.setSampleReceiveDate(sdf.format(order.getSubmitTime()));
        orderData.setCheckupGenDate(
            sdf.format(Optional.ofNullable(order.getReportGenerateTime()).orElse(DateOperateUtils.addOneSecond(order.getSubmitTime(), 500))));
        
        List<ItemData> itemDatas = testingEvaluateMapper.getItemDatas(orderId);
        if (CollectionUtils.isEmpty(itemDatas))
        {
            throw new ServiceException("can not find itemDatas");
        }
        
        for (ItemData data : itemDatas)
        {
            TestingItem testingItem = data.getTestingItem();
            
            switch (testingItem.getCategory())
            {
                case "1":
                {
                    setDiaseseValue(testingItem, data, customer.getSex());
                    break;
                }
                case "2":
                {
                    setYJvalue(testingItem, data);
                    break;
                }
                case "3":
                {
                    Integer level = ItemLevelEvaluator.getLevelInterval(testingItem, Double.valueOf(data.getDiseaseRisk()), 3);
                    data.setRelativeRisk(data.getDiseaseRisk());
                    data.setItemResult(data.getDiseaseRisk());
                    data.setDiseaseRisk(null);
                    String remark = ReportItemDescription.getChildDescription(testingItem.getCode(), level);
                    data.setItemResultDesc(remark);
                    break;
                }
                default:
                {
                    break;
                }
            }
            List<LocusData> locusDatas = testingEvaluateMapper.getLocusDatas(orderId, testingItem.getId());
            if (CollectionUtils.isEmpty(locusDatas))
            {
                throw new ServiceException("can not find locusDatas");
            }
            List<LocusData> datas = locusDatas.stream().filter(x -> StringUtils.isNotEmpty(x.getGeneResult())).collect(Collectors.toList());
            data.setGeneList(datas);
        }
        
        orderData.setItemList(itemDatas);
        return orderData;
    }
    
    private void setDiaseseValue(TestingItem testingItem, ItemData data, String sex)
    {
        Cancer cancer = cancerService.get(testingItem.getCategoryMapping());
        if (Objects.isNull(cancer))
        {
            throw new ServiceException("can not find cancer");
        }
        
        TestingItemEvaluateReferenceValue evaluateReferenceValue = new TestingItemEvaluateReferenceValue();
        evaluateReferenceValue.setMaleValue(cancer.getRiskmale());
        evaluateReferenceValue.setFemaleValue(cancer.getRiskfemale());
        Double avgRisk = evaluateReferenceValue.getValue(sex);
        Double relativeRisk = Double.valueOf(data.getDiseaseRisk()) / avgRisk;
        
        Integer level = ItemLevelEvaluator.getLevelInterval(testingItem, relativeRisk, 5);
        data.setAvgRisk(this.riskNumberFormat(avgRisk * 100, 2));
        data.setDiseaseRisk(this.riskNumberFormat(Double.valueOf(data.getDiseaseRisk()) * 100, 2));
        data.setRelativeRisk(this.riskNumberFormat(relativeRisk, 2));
        data.setItemResult(String.valueOf(level));
        String remark = ReportItemDescription.getDiseaseDescription(data.getItemName(), level, data.getDiseaseRisk(), String.valueOf(data.getAvgRisk()), sex);
        data.setItemResultDesc(remark);
    }
    
    private void setYJvalue(TestingItem testingItem, ItemData data)
    {
        int YJResult;
        if (TestingItem.JJDX.equals(testingItem.getCode()))
        {
            YJResult = ItemLevelEvaluator.getLevelInterval(testingItem, Double.valueOf(data.getDiseaseRisk()), 9);
        }
        else
        {
            YJResult = ItemLevelEvaluator.getLevelInterval(testingItem, Double.valueOf(data.getDiseaseRisk()), 3);
        }
        String remark = ReportItemDescription.getYjDescription(testingItem.getCode(), YJResult);
        data.setItemResult(data.getDiseaseRisk());
        data.setItemResultDesc(remark);
    }
    
    public String modify(MaintainOrderRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Order nowData = service.getOrderById(request.getId());
        if ("6".equals(nowData.getStatus()))
        {
            return request.getId();
        }
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mgmt.request.MaintainOrderRequest mgmtRequest = new com.todaysoft.ghealth.mgmt.request.MaintainOrderRequest();
        BeanUtils.copyProperties(request, mgmtRequest);
        mgmtRequest.setProductName(request.getProduct().getName());
        mgmtRequest.setProductId(request.getProduct().getId());
        mgmtRequest.setCustomerId(request.getCustomer().getId());
        
        Agency agency = agencyService.get(request.getAgencyId());
        com.todaysoft.ghealth.mybatis.model.Order data = wrapOrder(mgmtRequest);
        if (service.canModify(data))
        {
            BigDecimal amountAfter = service.refundForOrderModify(agency, mgmtRequest, data, account.getAccountName());
            service.placeForOrderModify(amountAfter, agency, mgmtRequest, data, account.getAccountName());
        }
        data.setUpdatorName(account.getAccountName());
        data.setUpdateTime(DateOperateUtils.addOneSecond(new Date(), 1));
        service.modify(data);
        return data.getId();
    }
    
    private String riskNumberFormat(Double risk, int length)
    {
        String pattern = StringUtils.rightPad("0.", length + 2, "0");
        DecimalFormat format = new DecimalFormat(pattern);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(risk);
    }
}
