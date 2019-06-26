package com.conch.metrics.service;

import com.conch.metrics.metric.custom.TestCustomCounter;
import com.conch.metrics.metrics.Metrics;
import com.conch.metrics.metrics.MetricsType;
import com.conch.metrics.metrics.ReservoirType;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public static final String TEST_CUSTOM_COUNTER_NAME = "testCounter";

    @Metrics(type = MetricsType.METER, reservoir = ReservoirType.UNIFORM)
    public void metricMeterTest() {
    }

    @Metrics(type = MetricsType.COUNT, reservoir = ReservoirType.UNIFORM)
    public void metricCounterTest() {
    }

    @Metrics(type = MetricsType.HISTOGRAM, reservoir = ReservoirType.UNIFORM)
    public void metricHitogramTest() {
    }

    @Metrics(type = MetricsType.TIMER, reservoir = ReservoirType.UNIFORM)
    public void metricTimerTest() {
    }

    @Metrics(type = MetricsType.CUSTOM, customAspect = TestCustomCounter.class, reservoir = ReservoirType.UNIFORM, metricsName = TEST_CUSTOM_COUNTER_NAME)
    public void customMetricTest() {
    }
}
