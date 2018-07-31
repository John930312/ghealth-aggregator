package com.todaysoft.ghealth.utils;

/**
 * Created by xjw on 2017/9/14.
 */
public class DataStatus
{
    //订单
    public static String ORDER_DRAFT = "0";//草稿
    
    public static String ORDER_PLACED = "1";//已下单
    
    public static String ORDER_COLLECTED = "2";//已签收
    
    public static String ORDER_EXPERIMENTING = "3";//实验中
    
    public static String ORDER_UPLOADED = "4";//已上傳
    
    public static String ORDER_FINISHED = "5";//已完成
    
    public static String ORDER_CANCELED = "6";//已取消
    
    //项目
    
    public static String ITEM_CATEGORY_ZL = "0";//肿瘤风险
    
    public static String ITEM_CATEGORY_MB = "1";//慢病
    
    public static String ITEM_CATEGORY_YJ = "2";//烟酒
    
    public static String ITEM_CATEGORY_ET = "3";//儿童
    
    public static String ITEM_CATEGORY_YY = "4";//用药
    
    public static String ITEM_CATEGORY_QT = "5";//其他
    
    //报告生成状态
    public static Integer REPORT_DOING = 0;
    
    public static Integer REPORT_SUCCESSED = 1;
    
    public static Integer REPORT_FAILED = 2;
    
    //账单分类
    public static String BILL_ADVANCE = "1";
    
    public static String BILL_PLACE_ORDER = "2";
    
    public static String BILL_REFUND = "3";
    
    //账单收入标示 0-余额减少 1-余额增加
    
    public static Boolean BALANCE_REDUCE = false;
    
    public static Boolean BALANCE_PLUS = true;
    
    //订单事件
    public static String ORDER_SAVE = "1";//订单暂存
    
    public static String ORDER_SAVE_TITLE = "创建订单";
    
    public static String ORDER_SUBMIT = "2";//订单提交
    
    public static String ORDER_SUBMIT_TITLE = "订单提交";
    
    public static String ORDER_SIGN = "3";//订单签收
    
    public static String ORDER_SIGN_TITLE = "订单签收";
    
    public static String ORDER_SEND = "4";//订单寄送
    
    public static String ORDER_SEND_TITLE = "订单寄送";
    
    public static String ORDER_UPLOAD = "5";//上传数据
    
    public static String ORDER_UPLOAD_TITLE = "上传数据";
    
    public static String ORDER_GENERATE = "6";//生成报告
    
    public static String ORDER_GENERATE_TITLE = "生成报告";
    
    public static String ORDER_CANCEL = "7";//订单取消
    
    public static String ORDER_CANCEL_TITLE = "订单取消";
    
    public static String ORDER_APPLICATION_REFUND = "8";//申请退款
    
    public static String ORDER_APPLICATION_REFUND_TITLE = "申请退款";
    
    public static String ORDER_CONFIRM_REFUND = "9";//确认退款
    
    public static String ORDER_CONFIRM_REFUND_TITLE = "确认退款";
    
    public static String REJECT_ORDER_APPLICATION_REFUND = "10";//驳回退款申请
    
    public static String REJECT_ORDER_APPLICATION_REFUND_TITLE = "驳回退款申请";
    
    public static String ORDER_DONE = "11";//订单完成
    
    public static String ORDER_DONE_TITLE = "订单完成";

    public static String ORDER_MODIFY = "12";//订单完成

    public static String ORDER_MODIFY_TITLE = "修改订单";
    
}
