package com.conch.metrics.processor.metric;

import com.codahale.metrics.*;
import com.conch.metrics.config.MetricsAutoConfig;
import com.conch.metrics.metrics.Metrics;
import com.conch.metrics.metrics.MetricsType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Component
@Conditional(MetricsAutoConfig.class)
public class MetricProcessor {

    @Autowired
    private MetricRegistry metricRegistry;

    private Map<MetricsType, BiFunction<Metrics, ProceedingJoinPoint, Object>> metricConsumer = new HashMap<>();

    public MetricProcessor() {
        metricConsumer.put(MetricsType.TIMER, this::applyTimer);
        metricConsumer.put(MetricsType.METER, this::applyMeter);
        metricConsumer.put(MetricsType.COUNT, this::applyCount);
        metricConsumer.put(MetricsType.HISTOGRAM, this::applyHistogram);
    }

    public BiFunction<Metrics, ProceedingJoinPoint, Object> processMetric(Metrics metric) {
        return metricConsumer.get(metric.type());
    }

    private Object applyTimer (Metrics metric, ProceedingJoinPoint pjp) {
        String metricName = this.resolveMetricName(pjp, metric);
        Timer timer = metricRegistry.timer(metricName);
        Timer.Context context = timer.time();
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            context.stop();
        }
    }

    private Object applyMeter (Metrics metric, ProceedingJoinPoint pjp) {
        String metricName = this.resolveMetricName(pjp, metric);
        Meter meter = metricRegistry.meter(metricName);
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            meter.mark();
        }
    }

    private Object applyCount (Metrics metric, ProceedingJoinPoint pjp) {
        String metricName = this.resolveMetricName(pjp, metric);
        Counter counter = metricRegistry.counter(metricName);
        try {
            counter.inc();
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private Object applyHistogram (Metrics metric, ProceedingJoinPoint pjp) {
        String metricName = this.resolveMetricName(pjp, metric);
        Histogram histogram = metricRegistry.histogram(metricName);
        long startTime = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        } finally {
            histogram.update(System.currentTimeMillis() - startTime);
        }
    }

    public String resolveMetricName(ProceedingJoinPoint joinPoint, Metrics metrics) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        String className = joinPoint.getTarget().getClass().getCanonicalName();
        return StringUtils.isEmpty(metrics.metricsName()) ? className + "." + methodName : metrics.metricsName();
    }
}
