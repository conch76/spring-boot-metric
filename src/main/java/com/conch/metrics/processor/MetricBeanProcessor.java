package com.conch.metrics.processor;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Reservoir;
import com.conch.metrics.config.MetricsAutoConfig;
import com.conch.metrics.metrics.CustomMetric;
import com.conch.metrics.metrics.Metrics;
import com.conch.metrics.metrics.MetricsType;
import com.conch.metrics.metrics.ReservoirType;
import com.conch.metrics.processor.exception.CustomMetricsInstantiationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@Component
@Slf4j
@AllArgsConstructor
@Conditional(MetricsAutoConfig.class)
public class MetricBeanProcessor implements BeanPostProcessor {

    private static BiFunction<String, Metrics, String> METRIC_NAME_RESOLVER =
            (name, metrics) -> StringUtils.isEmpty(metrics.metricsName()) ? name : metrics.metricsName();

    private final MetricRegistry metricRegistry;
    private final ReservoirFactory reservoirFactory;
    private final MetricsTypeFactory metricsTypeFactory;
    private final CustomMetricProcessor customMetricProcessor;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Advised) {
            Advised advised = (Advised) bean;
            createMetrics(advised);
        }
        return bean;
    }

    // this is for spring 4.X backward compatibility
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private void createMetrics(Advised advised) {

        Map<String, Metrics> eligibleMetricsMap = new HashMap<>();

        Method[] declaredMethods = advised.getTargetSource().getTargetClass().getDeclaredMethods();
        String qualifiedClassName = advised.getTargetSource().getTargetClass().getName();
        for (Method method : declaredMethods) {
            Metrics[] declaredAnnotationsByType = method.getDeclaredAnnotationsByType(Metrics.class);
            if (declaredAnnotationsByType.length > 0) {
                String methodName = method.getName();
                eligibleMetricsMap.put(qualifiedClassName + "." + methodName, declaredAnnotationsByType[0]);
            }
        }

        // ISSUE : https://gh.riotgames.com/krtech-common/riotmetric/issues/5
        // implement logic for controllerMetricAspect processing
        if (eligibleMetricsMap.size() > 0) {
            // this has to be advised class with MetricsType annotation not auto configured controller
            eligibleMetricsMap.forEach(this::resolveMetrics);
        }
    }

    private void resolveMetrics(String name, Metrics metrics) {
        String metricsName = METRIC_NAME_RESOLVER.apply(name, metrics);
        ReservoirType reservoir = metrics.reservoir();
        Reservoir metricReservoir = reservoirFactory.resolveReservoir(reservoir)
                .apply(metrics.reservoirConstrcutorParams());

        if (metrics.type() == MetricsType.CUSTOM) {
            CustomMetric customMetric = instantiateCustomMetrics(metrics);
            customMetricProcessor.addCustomMetric(metricsName, customMetric);
            customMetric.registerMetric(metricRegistry, metricsName);
            return;
        }

        Metric metric = metricsTypeFactory.getMetric(metrics.type())
                .apply(metricReservoir);
        metricRegistry.register(metricsName, metric);
    }

    private CustomMetric instantiateCustomMetrics(Metrics metrics) {
        try {
            return metrics.customAspect().newInstance();
        } catch (Exception e) {
            throw new CustomMetricsInstantiationException();
        }
    }
}
