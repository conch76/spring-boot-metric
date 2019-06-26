package com.conch.metrics.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;


@ComponentScan("com.riotgames.krtech")
@Configuration
public class MetricsAutoConfig implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String metricsEnabled = context.getEnvironment().getProperty("metrics.enabled");
        if (!StringUtils.isEmpty(metricsEnabled) && Boolean.valueOf(metricsEnabled)) {
            return true;
        }
        return false;
    }
}
