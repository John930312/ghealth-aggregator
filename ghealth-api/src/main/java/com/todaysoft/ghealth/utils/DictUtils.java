package com.todaysoft.ghealth.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DictUtils
{
    public static String getTuseLevelTextByValue(Integer level)
    {
        if (level.equals(1))
        {
            return "低风险";
        }
        else if (level.equals(2))
        {
            return "一般风险";
        }
        else if (level.equals(3))
        {
            return "稍高风险";
        }
        else if (level.equals(4))
        {
            return "较高风险";
        }
        else
        {
            return "高风险";
        }
    }
    
    public static String getNU002LevelTextByValue(Integer level)
    {
        if (level.equals(1))
        {
            return "吸收能力正常，建议多食用富含叶酸食物";
        }
        else if (level.equals(2))
        {
            return "吸收能力较正常，建议多食用富含叶酸食物";
        }
        else if (level.equals(3))
        {
            return "吸收能力较差，可遵医嘱，服用叶酸片";
        }
        else
        {
            return "吸收能力差，可遵医嘱，服用叶酸片";
        }
    }
    
    public static String getNU001LevelTextByValue(Integer level)
    {
        if (level.equals(4))
        {
            return "代谢能力很差，可遵医嘱，补充维生素B6/9/12";
        }
        else if (level.equals(3))
        {
            return "代谢能力差，可遵医嘱，补充维生素B6/9/12";
        }
        else if (level.equals(2))
        {
            return "代谢能力较差，多食用富含维生素B6/9/12食物";
        }
        else
        {
            return "代谢能力正常，多食用维生素B6/9/12食物";
        }
    }
    
    public static Map<String, String> getTuseCancerMap()
    {
        Map<String, String> map = new HashMap<>();
        map.put("GACA", "胃癌");
        map.put("LICA", "肝癌");
        map.put("LCCA", "肺癌");
        map.put("ESCA", "食管癌");
        map.put("CRC", "结直肠癌");
        map.put("NPC", "鼻咽癌");
        map.put("LEUK", "白血病");
        map.put("PACA", "胰腺癌");
        map.put("PRAD", "前列腺癌");
        map.put("BRCA", "乳腺癌");
        map.put("OVCA", "卵巢癌");
        map.put("CESC", "宫颈癌");
        map.put("UCEC", "子宫内膜癌");
        return map;
    }
    
    public static Map<Integer, String> getLocusResult()
    {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "低");
        map.put(2, "较低");
        map.put(3, "一般");
        map.put(4, "较高");
        map.put(5, "高");
        return map;
    }
    
    public static Map<Integer, String> getResultMap()
    {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "不良型代谢");
        map.put(2, "中间型代谢");
        map.put(3, "广泛型代谢");
        map.put(4, "超速型代谢");
        return map;
    }
    
    public static Map<Integer, String> getDoseMap()
    {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "减少剂量");
        map.put(2, "略减少剂量");
        map.put(3, "正常剂量");
        map.put(4, "略增加剂量");
        map.put(5, "增加剂量");
        return map;
    }
    
    public static Map<Integer, String> getDidResultMap()
    {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "正常");
        map.put(2, "杂合突变");
        map.put(3, "纯合突变");
        return map;
    }
    
    public static Map<Integer, String> getDidIllustrateMap()
    {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "可用，致聋风险低");
        map.put(2, "慎用，有致聋风险");
        map.put(3, "慎用，有致聋风险");
        return map;
    }
    
    public static String getAgeByBirthday(String birthday)
    {
        try
        {
            if (StringUtils.isEmpty(birthday))
            {
                return "";
            }
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            
            if (Objects.isNull(date))
            {
                return "";
            }
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = instant.atZone(zoneId).toLocalDate();
            Integer birthday_year = localDate.getYear();
            Integer now = LocalDate.now().getYear();
            return String.valueOf(now - birthday_year);
        }
        catch (ParseException e)
        {
            return "";
        }
    }
    
    public static Double getByAge(Integer age)
    {
        if (null == age)
        {
            return 1.0;
        }
        
        if (age <= 20)
        {
            return 0.86;
        }
        else if (age > 20 && age <= 30)
        {
            return 0.93;
        }
        else if (age > 30 && age <= 32)
        {
            return 0.93;
        }
        else if (age > 32 && age <= 34)
        {
            return 0.96;
        }
        else if (age > 34 && age <= 36)
        {
            return 0.97;
        }
        else if (age > 36 && age <= 38)
        {
            return 0.98;
        }
        else if (age > 38 && age <= 40)
        {
            return 0.99;
        }
        else if (age > 40 && age <= 42)
        {
            return 1.0;
        }
        else if (age > 42 && age <= 44)
        {
            return 1.01;
        }
        else if (age > 44 && age <= 46)
        {
            return 1.02;
        }
        else if (age > 46 && age <= 48)
        {
            return 1.03;
        }
        else if (age > 48 && age <= 50)
        {
            return 1.04;
        }
        else if (age > 50 && age <= 52)
        {
            return 1.05;
        }
        else if (age > 52 && age <= 54)
        {
            return 1.06;
        }
        else if (age > 54 && age <= 56)
        {
            return 1.07;
        }
        else if (age > 56 && age <= 58)
        {
            return 1.08;
        }
        else if (age > 58 && age <= 60)
        {
            return 1.09;
        }
        else if (age > 60 && age <= 62)
        {
            return 1.1;
        }
        else if (age > 62 && age <= 64)
        {
            return 1.11;
        }
        else if (age > 64 && age <= 66)
        {
            return 1.12;
        }
        else if (age > 66 && age <= 68)
        {
            return 1.13;
        }
        else if (age > 68 && age <= 70)
        {
            return 1.14;
        }
        else if (age > 70 && age <= 72)
        {
            return 1.15;
        }
        else if (age > 72 && age <= 74)
        {
            return 1.16;
        }
        else if (age > 74 && age <= 76)
        {
            return 1.17;
        }
        else if (age > 76 && age <= 78)
        {
            return 1.18;
        }
        else if (age > 78 && age <= 80)
        {
            return 1.19;
        }
        else
        {
            return 1.21;
        }
        
    }
}
