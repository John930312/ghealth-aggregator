package com.todaysoft.ghealth.service.impl.core;

import java.util.Map;

public interface TestingItemEvaluator
{
    TestingItemEvaluateResult evaluate(TestingItemEvaluateConfig config, Map<String, String> genetypes, String sex);
}
