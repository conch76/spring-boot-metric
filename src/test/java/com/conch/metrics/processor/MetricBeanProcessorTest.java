package com.conch.metrics.processor;

import com.codahale.metrics.MetricRegistry;
import com.conch.metrics.aop.GenericMetricsAspect;
import com.conch.metrics.processor.aop.TestConcrete;
import com.conch.metrics.processor.aop.TestConcreteWithMetric;
import com.conch.metrics.processor.aop.TestInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.assertj.core.api.Assertions.assertThat;


class MetricBeanProcessorTest {

    private MetricBeanProcessor metricBeanProcessor;
    private MetricRegistry metricRegistry;

    @BeforeEach
    public void setUp() {
        metricRegistry = new MetricRegistry();
        metricBeanProcessor = new MetricBeanProcessor(metricRegistry, new ReservoirFactory(), new MetricsTypeFactory(), new CustomMetricProcessor());
    }

    @Test
    public void postProcessAfterInitialization_no_aop_added_when_no_metrics_annotation() {

        // given
        TestInterface target = new TestConcrete();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        GenericMetricsAspect genericMetricsAspect = new GenericMetricsAspect(null, null);
        factory.addAspect(genericMetricsAspect);
        TestInterface proxy = factory.getProxy();

        metricBeanProcessor.postProcessAfterInitialization(proxy, proxy.getClass().getSimpleName());

        assertThat(metricRegistry.getMetrics().size()).isEqualTo(0);
    }

    @Test
    public void postProcessAfterInitialization_should_process_metrics_when_metrics_annotation_exsits() {

        // given
        TestInterface target = new TestConcreteWithMetric(); // test class with 1 metric
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        GenericMetricsAspect genericMetricsAspect = new GenericMetricsAspect(null, null);
        factory.addAspect(genericMetricsAspect);
        TestInterface proxy = factory.getProxy();

        // when
        metricBeanProcessor.postProcessAfterInitialization(proxy, proxy.getClass().getSimpleName());

        // then
        assertThat(metricRegistry.getMetrics().size()).isEqualTo(1);
        assertThat(metricRegistry.getCounters().firstKey()).isNotEmpty();
    }
}