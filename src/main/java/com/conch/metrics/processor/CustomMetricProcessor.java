package com.conch.metrics.processor;

import com.conch.metrics.config.MetricsAutoConfig;
import com.conch.metrics.metrics.CustomMetric;
import com.conch.metrics.processor.exception.MetricAlreadyRegisteredException;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Conditional(MetricsAutoConfig.class)
public class CustomMetricProcessor {

    private Map<String, CustomMetric> customMetricMap;

    public CustomMetricProcessor() {
        this.customMetricMap = new HashMap<>();
    }

    public void addCustomMetric(String metricName, CustomMetric customMetric) {
        if (customMetricMap.containsKey(metricName)) {
            throw new MetricAlreadyRegisteredException();
        }
        customMetricMap.put(metricName, customMetric);
    }

    public CustomMetric getCustomMetric(String metricName) {
        return customMetricMap.get(metricName);
    }
}
