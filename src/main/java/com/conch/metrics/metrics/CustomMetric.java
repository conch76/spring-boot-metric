package com.conch.metrics.metrics;

import com.codahale.metrics.MetricRegistry;
import org.aspectj.lang.ProceedingJoinPoint;

public interface CustomMetric {

    Object applyCustomMetric(ProceedingJoinPoint pjp);
    void registerMetric(MetricRegistry metricRegistry, String metricName);
}
