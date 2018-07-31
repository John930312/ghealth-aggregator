package com.todaysoft.ghealth.utils;

import java.util.Calendar;
import java.util.Date;

public class DateOperateUtils
{
    public static Date addOneSecond(Date date,Integer second)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }
}
