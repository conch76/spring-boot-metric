package com.conch.metrics.config.jvm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

@Configuration
@ConditionalOnProperty(prefix = "metrics.jvm", value = {"enabled"}, havingValue ="true")
public class JvmMetricAutoConfig implements Condition {

    @Value("${metrics.jvm.enabled}")
    private String enabled;

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String jvmEnabled = context.getEnvironment().getProperty("metrics.jvm.enabled");  // @Value not available yet
        return !StringUtils.isEmpty(jvmEnabled) && Boolean.valueOf(jvmEnabled);
    }
}
