package com.conch.metrics.processor;

import com.codahale.metrics.*;
import com.conch.metrics.config.MetricsAutoConfig;
import com.conch.metrics.metrics.MetricsType;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Conditional(MetricsAutoConfig.class)
public class MetricsTypeFactory {

    private final Map<MetricsType, Function<Reservoir, Metric>> supportedMetricList;

    public MetricsTypeFactory() {
        this.supportedMetricList = new HashMap<>();
        supportedMetricList.put(MetricsType.COUNT, (reservoir) -> new Counter());
        supportedMetricList.put(MetricsType.METER, (reservoir) -> new Meter());
        supportedMetricList.put(MetricsType.HISTOGRAM, Histogram::new);
        supportedMetricList.put(MetricsType.TIMER, Timer::new);
    }

    public Function<Reservoir, Metric> getMetric(MetricsType type) {
        return supportedMetricList.get(type);
    }
}
