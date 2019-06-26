package com.conch.metrics;

import com.conch.metrics.config.jvm.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(value = {"jvm-auto-configuration"})
public class JvmAutoConfigurationIT {

    @Autowired
    private JvmMetricAutoConfig jvmMetricAutoConfig;
    @Autowired
    private JvmMemoryConfig jvmMemoryConfig;
    @Autowired
    private JvmGcConfig jvmGcConfig;
    @Autowired
    private JvmThreadStatesConfig jvmThreadStatesConfig;
    @Autowired
    private JvmFdUsageConfig jvmFdUsageConfig;
    @Autowired
    private JvmCpuConfig jvmCpuConfig;

    @Test
    public void should_create_jvm_metrics() {
        assertThat(jvmMetricAutoConfig).isNotNull();
        assertThat(jvmMemoryConfig).isNotNull();
        assertThat(jvmGcConfig).isNotNull();
        assertThat(jvmThreadStatesConfig).isNotNull();
        assertThat(jvmFdUsageConfig).isNotNull();
        assertThat(jvmCpuConfig).isNotNull();
    }
}
