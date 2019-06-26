package com.conch.metrics.aop;

import com.conch.metrics.config.MetricsAutoConfig;
import com.conch.metrics.metrics.CustomMetric;
import com.conch.metrics.metrics.Metrics;
import com.conch.metrics.metrics.MetricsType;
import com.conch.metrics.processor.CustomMetricProcessor;
import com.conch.metrics.processor.metric.MetricProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
@Conditional(value = {MetricsAutoConfig.class})
public class GenericMetricsAspect {

    private final MetricProcessor metricProcessor;
    private final CustomMetricProcessor customMetricProcessor;

    @Pointcut(value = "execution(public * *(..))")
    public void anyPublicMethod() {
    }

    @Around("anyPublicMethod() && @annotation(com.conch.metrics.metrics.Metrics)")
    public Object around(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Metrics metrics = method.getAnnotation(Metrics.class);

        if (metrics.type() == MetricsType.CUSTOM) {
            return processCustomMetric(metrics, joinPoint);
        }

        return metricProcessor.processMetric(metrics)
                .apply(metrics, joinPoint);
    }

    private Object processCustomMetric(Metrics metrics, ProceedingJoinPoint joinPoint) {
        CustomMetric customMetric = customMetricProcessor.getCustomMetric(metricProcessor.resolveMetricName(joinPoint, metrics));
        return customMetric.applyCustomMetric(joinPoint);
    }
}
