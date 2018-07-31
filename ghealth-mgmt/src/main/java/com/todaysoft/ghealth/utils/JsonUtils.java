package com.todaysoft.ghealth.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils
{
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    static
    {
        OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);
        OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }
    
    public static String toJson(Object object)
    {
        if (null == object)
        {
            return null;
        }
        
        try
        {
            return OBJECT_MAPPER.writeValueAsString(object);
        }
        catch (JsonProcessingException e)
        {
            return null;
        }
    }
    
    public static <T> T fromJson(String json, Class<T> type)
    {
        try
        {
            return OBJECT_MAPPER.readValue(json, type);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    public static <T> T fromJson(String json, TypeReference<T> type)
    {
        try
        {
            return OBJECT_MAPPER.readValue(json, type);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
