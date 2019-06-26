package com.conch.metrics.metric.custom;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.conch.metrics.metrics.CustomMetric;
import org.aspectj.lang.ProceedingJoinPoint;

public class TestCustomCounter implements CustomMetric {

    private Counter counterMetric;

    @Override
    public Object applyCustomMetric(ProceedingJoinPoint pjp) {
        Object returnValue;
        try {
             returnValue = pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            counterMetric.inc();
        }
        return returnValue;
    }

    @Override
    public void registerMetric(MetricRegistry metricRegistry, String metricName) {
        counterMetric = new Counter();
        metricRegistry.register(metricName, counterMetric);
    }
}
