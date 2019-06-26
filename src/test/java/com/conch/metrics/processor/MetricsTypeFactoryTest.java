package com.conch.metrics.processor;

import com.codahale.metrics.*;
import com.conch.metrics.metrics.MetricsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

class MetricsTypeFactoryTest {

    MetricsTypeFactory metricsTypeFactory;

    @BeforeEach
    public void setUp() {
        metricsTypeFactory = new MetricsTypeFactory();
    }

    @Test
    public void getMetric_should_resolve_correct_metric() {
        Metric metric = metricsTypeFactory.getMetric(MetricsType.COUNT)
                .apply(new UniformReservoir());
        assertThat(metric).isInstanceOf(Counter.class);

        metric = metricsTypeFactory.getMetric(MetricsType.TIMER)
                .apply(new UniformReservoir());
        assertThat(metric).isInstanceOf(Timer.class);

        metric = metricsTypeFactory.getMetric(MetricsType.HISTOGRAM)
                .apply(new UniformReservoir());
        assertThat(metric).isInstanceOf(Histogram.class);

        metric = metricsTypeFactory.getMetric(MetricsType.METER)
                .apply(new UniformReservoir());
        assertThat(metric).isInstanceOf(Meter.class);
    }
}