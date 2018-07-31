package com.todaysoft.ghealth.model.product;

import java.math.BigDecimal;
import java.util.Date;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

/**
 * Created by admin on 2017/9/5.
 * 代理产品
 */
public class ProductAgent extends SignatureTokenListRequest
{
    private String id;
    
    private String testingProductId;
    
    private String agencyId;
    
    private String productCode;
    
    private String productName;
    
    private String productSexRestraint;
    
    private String productSexRestraintText;
    
    private BigDecimal guidingPrice;
    
    private BigDecimal agencyPrice;
    
    private boolean productEnabled;
    
    private Date productCreateTime;
    
    private Date productUpdateTime;
    
    private String agencyName;

    private String agencyIds;

    private String discountPrice;

    private Date startTime;

    private Date endTime;

    private boolean discount;

    private String testingProductIds;

    private String detail;//代理价格调整备注

    private String changeType;//1:代理机构-修改价格;2：代理商品-优惠价格;3：代理商品-批量修改价格;4:取消优惠;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTestingProductId()
    {
        return testingProductId;
    }
    
    public void setTestingProductId(String testingProductId)
    {
        this.testingProductId = testingProductId;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getProductSexRestraint()
    {
        return productSexRestraint;
    }
    
    public void setProductSexRestraint(String productSexRestraint)
    {
        this.productSexRestraint = productSexRestraint;
    }
    
    public String getProductSexRestraintText()
    {
        return productSexRestraintText;
    }
    
    public void setProductSexRestraintText(String productSexRestraintText)
    {
        this.productSexRestraintText = productSexRestraintText;
    }
    
    public BigDecimal getGuidingPrice()
    {
        return guidingPrice;
    }
    
    public void setGuidingPrice(BigDecimal guidingPrice)
    {
        this.guidingPrice = guidingPrice;
    }
    
    public BigDecimal getAgencyPrice()
    {
        return agencyPrice;
    }
    
    public void setAgencyPrice(BigDecimal agencyPrice)
    {
        this.agencyPrice = agencyPrice;
    }
    
    public boolean isProductEnabled()
    {
        return productEnabled;
    }
    
    public void setProductEnabled(boolean productEnabled)
    {
        this.productEnabled = productEnabled;
    }
    
    public Date getProductCreateTime()
    {
        return productCreateTime;
    }
    
    public void setProductCreateTime(Date productCreateTime)
    {
        this.productCreateTime = productCreateTime;
    }
    
    public Date getProductUpdateTime()
    {
        return productUpdateTime;
    }
    
    public void setProductUpdateTime(Date productUpdateTime)
    {
        this.productUpdateTime = productUpdateTime;
    }
    
    public String getAgencyName()
    {
        return agencyName;
    }
    
    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }

    public String getAgencyIds() {
        return agencyIds;
    }

    public void setAgencyIds(String agencyIds) {
        this.agencyIds = agencyIds;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }


    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public String getTestingProductIds() {
        return testingProductIds;
    }

    public void setTestingProductIds(String testingProductIds) {
        this.testingProductIds = testingProductIds;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }
}