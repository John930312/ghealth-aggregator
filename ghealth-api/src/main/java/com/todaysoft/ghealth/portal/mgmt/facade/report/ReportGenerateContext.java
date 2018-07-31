package com.todaysoft.ghealth.portal.mgmt.facade.report;

import java.util.List;
import java.util.Map;

import com.todaysoft.ghealth.mybatis.model.Customer;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.model.OrderHistory;
import com.todaysoft.ghealth.mybatis.model.Product;
import com.todaysoft.ghealth.service.impl.core.TestingItemAlgorithmConfig;
import com.todaysoft.ghealth.service.impl.core.TestingItemEvaluateConfig;
import com.todaysoft.ghealth.service.impl.core.TestingItemEvaluateResult;

public class ReportGenerateContext
{
    private String destUri;
    
    private String destDirectory;
    
    private String generateKey;
    
    private String templateKey;
    
    private Order order;
    
    private Product product;
    
    private Customer customer;
    
    private List<TestingItemEvaluateConfig> testingItemEvaluateConfigs;
    
    private List<TestingItemAlgorithmConfig> testingItemAlgorithmConfigs;
    
    private Map<String, String> genetypes;
    
    private ManagementAccountDetails operator;
    
    private List<OrderHistory> orderHistories;
    
    private List<TestingItemEvaluateResult> testingItemEvaluateResults;
    
    public String getDestUri()
    {
        return destUri;
    }
    
    public void setDestUri(String destUri)
    {
        this.destUri = destUri;
    }
    
    public String getDestDirectory()
    {
        return destDirectory;
    }
    
    public void setDestDirectory(String destDirectory)
    {
        this.destDirectory = destDirectory;
    }
    
    public String getGenerateKey()
    {
        return generateKey;
    }
    
    public void setGenerateKey(String generateKey)
    {
        this.generateKey = generateKey;
    }
    
    public String getTemplateKey()
    {
        return templateKey;
    }
    
    public void setTemplateKey(String templateKey)
    {
        this.templateKey = templateKey;
    }
    
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    public Customer getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    
    public List<TestingItemEvaluateConfig> getTestingItemEvaluateConfigs()
    {
        return testingItemEvaluateConfigs;
    }
    
    public void setTestingItemEvaluateConfigs(List<TestingItemEvaluateConfig> testingItemEvaluateConfigs)
    {
        this.testingItemEvaluateConfigs = testingItemEvaluateConfigs;
    }
    
    public Map<String, String> getGenetypes()
    {
        return genetypes;
    }
    
    public void setGenetypes(Map<String, String> genetypes)
    {
        this.genetypes = genetypes;
    }
    
    public ManagementAccountDetails getOperator()
    {
        return operator;
    }
    
    public void setOperator(ManagementAccountDetails operator)
    {
        this.operator = operator;
    }
    
    public List<OrderHistory> getOrderHistories()
    {
        return orderHistories;
    }
    
    public void setOrderHistories(List<OrderHistory> orderHistories)
    {
        this.orderHistories = orderHistories;
    }
    
    public List<TestingItemEvaluateResult> getTestingItemEvaluateResults()
    {
        return testingItemEvaluateResults;
    }
    
    public void setTestingItemEvaluateResults(List<TestingItemEvaluateResult> testingItemEvaluateResults)
    {
        this.testingItemEvaluateResults = testingItemEvaluateResults;
    }
    
    public List<TestingItemAlgorithmConfig> getTestingItemAlgorithmConfigs()
    {
        return testingItemAlgorithmConfigs;
    }
    
    public void setTestingItemAlgorithmConfigs(List<TestingItemAlgorithmConfig> testingItemAlgorithmConfigs)
    {
        this.testingItemAlgorithmConfigs = testingItemAlgorithmConfigs;
    }
}
