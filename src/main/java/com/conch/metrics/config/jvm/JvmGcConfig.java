package com.conch.metrics.config.jvm;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Created by iye on 2018. 2. 20..
 */
@Configuration
@Conditional(JvmMetricAutoConfig.class)
@ConditionalOnProperty(prefix = "metrics.jvm", value = {"gc-metrics-name"})
@Slf4j
public class JvmGcConfig {

    @Value("${metrics.jvm.gc-metrics-name}")
    private String gcMetricsName;

    @Bean
    public GarbageCollectorMetricSet garbageCollectorMetricSet(MetricRegistry metricRegistry) {
        log.info("JVM Metric Auto configuration for GC enabled! with metric name {}", gcMetricsName);
        GarbageCollectorMetricSet garbageCollectorMetricSet = new GarbageCollectorMetricSet();
        metricRegistry.register(gcMetricsName, garbageCollectorMetricSet);
        return garbageCollectorMetricSet;
    }
}
