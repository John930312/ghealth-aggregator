package com.todaysoft.ghealth.mybatis.searcher;

public class AgencyProductOldPriceSearcher
{
    private String agencyProductId;

    private String agencyId;

    private String productId;

    private Integer offset;

    private Integer limit;

    public String getAgencyProductId() {
        return agencyProductId;
    }

    public void setAgencyProductId(String agencyProductId) {
        this.agencyProductId = agencyProductId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
