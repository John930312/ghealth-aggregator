package com.todaysoft.ghealth.utils;

import java.util.HashMap;
import java.util.Map;

public class DictUtils
{
    public static String getTuseLevelTextByValue(Integer level)
    {
        if (level.equals(1))
        {
            return "较低";
        }
        else if (level.equals(2))
        {
            return "一般";
        }
        else if (level.equals(3))
        {
            return "关注";
        }
        else
        {
            return "密切关注";
        }
    }
    
    public static String getNU002LevelTextByValue(Integer level)
    {
        if (level.equals(1))
        {
            return "未发现风险";
        }
        else if (level.equals(2))
        {
            return "低风险";
        }
        else if (level.equals(3))
        {
            return "中度风险";
        }
        else
        {
            return "高风险";
        }
    }
    
    public static String getNU001LevelTextByValue(Integer level)
    {
        if (level.equals(4))
        {
            return "您同型半胱氨酸代谢能力很差.";
        }
        else if (level.equals(3))
        {
            return "您同型半胱氨酸代谢能力差.";
        }
        else if (level.equals(2))
        {
            return "您同型半胱氨酸代谢能力较差.";
        }
        else
        {
            return "如果检测结果都是正常.";
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
}
