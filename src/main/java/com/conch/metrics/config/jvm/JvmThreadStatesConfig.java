package com.conch.metrics.config.jvm;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
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
@ConditionalOnProperty(prefix = "metrics.jvm", value = {"thread-states-metrics-name"})
@Slf4j
public class JvmThreadStatesConfig {

    @Value("${metrics.jvm.thread-states-metrics-name}")
    private String threadStatesMetricsName;

    @Bean
    public ThreadStatesGaugeSet threadStatesGaugeSet(MetricRegistry metricRegistry) {
        log.info("JVM Metric Auto configuration for THREAD enabled! with metric name {}", threadStatesMetricsName);
        ThreadStatesGaugeSet threadStatesGaugeSet = new ThreadStatesGaugeSet();
        metricRegistry.register(threadStatesMetricsName, threadStatesGaugeSet);
        return threadStatesGaugeSet;
    }
}
