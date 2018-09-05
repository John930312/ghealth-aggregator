package com.todaysoft.ghealth.base.response.model;

/**
 * @Author: ljl
 * @Date: 2018/9/5 0005 9:52
 */
public class Statistics {
    private Integer orderForm;

    private Integer orderSignIn;

    private Integer orderDelivery;

    private Integer report;

    public Integer getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(Integer orderForm) {
        this.orderForm = orderForm;
    }

    public Integer getOrderSignIn() {
        return orderSignIn;
    }

    public void setOrderSignIn(Integer orderSignIn) {
        this.orderSignIn = orderSignIn;
    }

    public Integer getOrderDelivery() {
        return orderDelivery;
    }

    public void setOrderDelivery(Integer orderDelivery) {
        this.orderDelivery = orderDelivery;
    }

    public Integer getReport() {
        return report;
    }

    public void setReport(Integer report) {
        this.report = report;
    }
}
