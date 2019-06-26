package com.conch.metrics.processor.aop;

import com.conch.metrics.metrics.Metrics;
import com.conch.metrics.metrics.MetricsType;

public class TestConcreteWithMetric implements TestInterface {

    @Metrics(type = MetricsType.COUNT, reservoirConstrcutorParams = {"1", "DAYS"})
    @Override
    public void proxyThis() {
    }
}
