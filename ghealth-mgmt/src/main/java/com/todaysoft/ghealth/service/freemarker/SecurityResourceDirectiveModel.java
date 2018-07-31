package com.todaysoft.ghealth.service.freemarker;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.service.security.ResourceAuthorizedDecision;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class SecurityResourceDirectiveModel implements TemplateDirectiveModel
{
    @Autowired
    private ResourceAuthorizedDecision decision;
    
    @Override
    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map parameters, TemplateModel[] loopVars, TemplateDirectiveBody body)
        throws TemplateException, IOException
    {
        String resource = ((TemplateModel)parameters.get("resource")).toString();
        boolean authorized = decision.isAuthorized(resource);
        
        if (authorized)
        {
            body.render(env.getOut());
        }
    }
}
