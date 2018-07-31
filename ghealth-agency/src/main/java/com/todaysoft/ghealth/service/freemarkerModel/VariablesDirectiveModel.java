package com.todaysoft.ghealth.service.freemarkerModel;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class VariablesDirectiveModel implements TemplateDirectiveModel
{
    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
        throws TemplateException, IOException
    {
        Map<String, TemplateModel> args = new HashMap<String, TemplateModel>(params);
        Map<String, TemplateModel> variables = getVariables(args, env);
        
        Map<String, TemplateModel> overrides = setEnvironmentVariable(env, variables);
        
        if (null != body && null != env.getOut())
        {
            body.render(env.getOut());
        }
        
        if (!overrides.isEmpty())
        {
            for (Map.Entry<String, TemplateModel> variable : overrides.entrySet())
            {
                env.setVariable(variable.getKey(), variable.getValue());
            }
        }
    }
    
    protected Map<String, TemplateModel> getVariables(Map<String, TemplateModel> parameters, Environment env)
        throws TemplateException
    {
        Map<String, TemplateModel> variables = new HashMap<String, TemplateModel>(parameters);
        return variables;
    }
    
    protected Map<String, TemplateModel> setEnvironmentVariable(Environment env, Map<String, TemplateModel> variables)
        throws TemplateException
    {
        if (null == variables || variables.isEmpty())
        {
            return Collections.emptyMap();
        }
        
        String key;
        TemplateModel value;
        TemplateModel existValue;
        Map<String, TemplateModel> overrides = new HashMap<String, TemplateModel>();
        
        for (Map.Entry<String, TemplateModel> variable : variables.entrySet())
        {
            key = variable.getKey();
            value = variable.getValue();
            existValue = env.getVariable(key);
            
            if (null != existValue)
            {
                overrides.put(key, existValue);
            }
            
            env.setVariable(key, value);
        }
        
        return overrides;
    }
    
    protected void removeParamsFromVariable(Environment env, Map<String, TemplateModel> params, Map<String, TemplateModel> origMap)
        throws TemplateException
    {
        if (params.size() <= 0)
        {
            return;
        }
        for (String key : params.keySet())
        {
            env.setVariable(key, origMap.get(key));
        }
    }
    
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
    
    protected Boolean getBooleanParameter(String key, Map<String, TemplateModel> parameters)
    {
        Object parameter = getObjectParameter(key, parameters);
        
        if (null == parameter)
        {
            return null;
        }
        
        if (parameter instanceof TemplateBooleanModel)
        {
            try
            {
                return ((TemplateBooleanModel)parameter).getAsBoolean();
            }
            catch (TemplateModelException e)
            {
                return false;
            }
        }
        else if (parameter instanceof SimpleNumber)
        {
            int value = ((SimpleNumber)parameter).getAsNumber().intValue();
            return 1 == value;
        }
        else if (parameter instanceof SimpleScalar)
        {
            String value = ((SimpleScalar)parameter).getAsString();
            return "1".equals(value);
        }
        
        return false;
    }
}
