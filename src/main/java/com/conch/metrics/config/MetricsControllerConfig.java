package com.conch.metrics.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

@Configuration
public class MetricsControllerConfig implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String controllerEnabled = conditionContext.getEnvironment().getProperty("metrics.controller.enabled");
        if (!StringUtils.isEmpty(controllerEnabled) && Boolean.valueOf(controllerEnabled)) {
            return true;
        }
        return false;
    }
}
