package com.conch.metrics.metrics;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface  Metrics {

    /**
     * Supported MetricsType from coda hale metric.
     * @return
     */
    MetricsType type();

    /**
     * Supported Reservoir from coda hale metric.  Default is slidingTimeWindowArrayReservoir
     * @return
     */
    ReservoirType reservoir() default ReservoirType.SLIDING_TIME_WINDOW;

    /**
     * depending on Reservoir, parameters required for constructing Reservoir
     * @return
     */
    String [] reservoirConstrcutorParams() default {};

    /**
     * Coda hale metrics key name, if not supplied MetricBeanProcessor will extract package + class + method_name
     * @return
     */
    String metricsName() default "";

    /**
     * custom aspect class, only used when metricType is custom
     * @return
     */
    Class<? extends CustomMetric> customAspect() default CustomMetric.class;
}
