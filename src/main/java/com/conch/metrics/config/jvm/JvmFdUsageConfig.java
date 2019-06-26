package com.conch.metrics.config.jvm;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.FileDescriptorRatioGauge;
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
@ConditionalOnProperty(prefix = "metrics.jvm", value = {"fd-usage-metrics-name"})
@Slf4j
public class JvmFdUsageConfig {

    @Value("${metrics.jvm.fd-usage-metrics-name}")
    private String fdUsageMetricsName;

    @Bean
    public FileDescriptorRatioGauge fileDescriptorRatioGauge(MetricRegistry metricRegistry) {
        log.info("JVM Metric Auto configuration for FileDescriptor enabled! with metric name {}", fdUsageMetricsName);
        FileDescriptorRatioGauge fileDescriptorRatioGauge = new FileDescriptorRatioGauge();
        metricRegistry.register(fdUsageMetricsName, fileDescriptorRatioGauge);
        return fileDescriptorRatioGauge;
    }
}
