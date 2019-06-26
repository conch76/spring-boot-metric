package com.conch.metrics.metrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

public class CpuMetricSet implements MetricSet {

    private final OperatingSystemMXBean osBean;

    public CpuMetricSet() {
        osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    }

    @Override
    public Map<String, Metric> getMetrics() {
        Map<String, Metric> gauageMap = new HashMap<>();
        gauageMap.put("jvmCpuLoad", (Gauge<Double>) osBean::getProcessCpuLoad);
        return gauageMap;
    }
}
