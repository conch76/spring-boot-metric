package com.conch.metrics.config.jvm;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(JvmMetricAutoConfig.class)
@ConditionalOnProperty(prefix = "metrics.jvm", value = {"memory-metrics-name"})
@Slf4j
public class JvmMemoryConfig {

    @Value("${metrics.jvm.memory-metrics-name}")
    private String memoryMetricsName;

    @Bean
    public MemoryUsageGaugeSet memoryUsageGaugeSet(MetricRegistry metricRegistry) {
        log.info("JVM Metric Auto configuration for MEMORY enabled! with metric name {}", memoryMetricsName);
        MemoryUsageGaugeSet memoryUsageGaugeSet = new MemoryUsageGaugeSet();
        metricRegistry.register(memoryMetricsName, memoryUsageGaugeSet);
        return memoryUsageGaugeSet;
    }
}
