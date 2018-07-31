package com.todaysoft.ghealth.service.freemarkerModel;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public abstract class OutputDirectiveModel implements TemplateDirectiveModel
{
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
        throws TemplateException, IOException
    {
        Map<String, TemplateModel> args = new HashMap<String, TemplateModel>(params);
        String text = getOutputText(args, env);
        Writer writer = env.getOut();
        writer.append(text);
    }
    
    protected abstract String getOutputText(Map<String, TemplateModel> parameters, Environment env)
        throws TemplateException;
    
    protected Object getObjectParameter(String key, Map<String, TemplateModel> parameters)
    {
        if (!parameters.containsKey(key))
        {
            return null;
        }
        
        return parameters.get(key);
    }
    
    protected String getStringParameter(String key, Map<String, TemplateModel> parameters)
    {
        Object parameter = getObjectParameter(key, parameters);
        return null == parameter ? null : String.valueOf(parameter);
    }
    
    protected Integer getIntegerParameter(String key, Map<String, TemplateModel> parameters)
    {
        String parameter = getStringParameter(key, parameters);
        return null == parameter ? null : Integer.valueOf(parameter);
    }
}
