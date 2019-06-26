package com.conch.metrics.config.jvm;

import com.codahale.metrics.MetricRegistry;
import com.conch.metrics.metrics.CpuMetricSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(JvmMetricAutoConfig.class)
@ConditionalOnProperty(prefix = "metrics.jvm", value = {"cpu-usage-metrics-name"})
@Slf4j
public class JvmCpuConfig {

    @Value("${metrics.jvm.cpu-usage-metrics-name}")
    private String cpuUsageMetricsName;

    @Bean
    public CpuMetricSet cpuMetricSet(MetricRegistry metricRegistry) {
        log.info("JVM Metric Auto configuration for CPU enabled! with metric name {}", cpuUsageMetricsName);
        CpuMetricSet cpuGuage = new CpuMetricSet();
        metricRegistry.register(cpuUsageMetricsName, cpuGuage);
        return cpuGuage;
    }
}
