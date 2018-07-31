package com.todaysoft.ghealth.service.freemarker;

import com.todaysoft.ghealth.model.dict.Dict;
import com.todaysoft.ghealth.service.IDictService;
import freemarker.core.Environment;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.Version;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class FindDictsByCategory extends VariablesDirectiveModel
{
    @Autowired
    private IDictService dictService;
    
    @Override
    public Map<String, TemplateModel> getVariables(Map<String, TemplateModel> parameters, Environment env) throws TemplateException
    {
        Map<String, TemplateModel> variables = super.getVariables(parameters, env);
        String category = getStringParameter("category", variables);
        if (StringUtils.isNotEmpty(category))
        {
            try
            {
                List<Dict> dicts = dictService.getDictsByCategory(category);
                variables.put("entries", new DefaultObjectWrapperBuilder(new Version("2.3.23")).build().wrap(dicts));
            }
            catch (Exception e)
            {
                variables.put("entries", new DefaultObjectWrapperBuilder(new Version("2.3.23")).build().wrap(Collections.emptyList()));
            }
        }
        return variables;
    }
}
