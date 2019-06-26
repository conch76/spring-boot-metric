package com.conch.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.conch.metrics.metrics.Metrics;
import com.conch.metrics.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MetricIntegrationIT {

    @Autowired
    private MetricRegistry metricRegistry;
    @Autowired
    private TestService testService;

    @Test
    public void should_create_metric_from_test_service() {
        int metricAnnotationCounter = 0;
        Method[] declaredMethods = TestService.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            Metrics[] declaredAnnotationsByType = method.getDeclaredAnnotationsByType(Metrics.class);
            metricAnnotationCounter = metricAnnotationCounter + declaredAnnotationsByType.length;
        }

        assertThat(metricRegistry.getMetrics().size()).isEqualTo(metricAnnotationCounter);

        assertThat(metricRegistry.getMetrics()
                .containsKey(TestService.TEST_CUSTOM_COUNTER_NAME)).isTrue();
    }

    @Test
    public void should_increase_test_custom_counter_when_invoked() {
        testService.customMetricTest();
        Counter metric = (Counter)metricRegistry.getMetrics().get(TestService.TEST_CUSTOM_COUNTER_NAME);
        long count = metric.getCount();

        assertThat(count).isEqualTo(1);
    }
}
