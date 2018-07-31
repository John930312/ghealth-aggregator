package com.todaysoft.ghealth.service.freemarker;

import com.todaysoft.ghealth.model.dict.Dict;
import com.todaysoft.ghealth.service.IDictService;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FindDictByCategoryAndValue extends OutputDirectiveModel
{
    @Autowired
    private IDictService dictService;
    
    @Override
    protected String getOutputText(Map<String, TemplateModel> parameters, Environment env) throws TemplateException
    {
        String category = getStringParameter("category", parameters);
        String value = getStringParameter("value", parameters);
        if (StringUtils.isEmpty(category))
        {
            throw new TemplateException("Categogy Can not be empty.", env);
        }
        if (StringUtils.isEmpty(value))
        {
            return "";
        }
        try
        {
            Dict entry = dictService.getDictByCategoryAndValue(category, value);
            return null == entry ? (StringUtils.isEmpty(value) ? "" : value) : entry.getDictText();
        }
        catch (Exception e)
        {
            return StringUtils.isEmpty(value) ? "" : value;
        }
    }
}
