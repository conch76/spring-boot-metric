package com.conch.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    @Profile(value = {"!rtp-auto-configuration"})
    public MetricRegistry metricRegistry() {
        MetricRegistry metricRegistry = new MetricRegistry();
        ConsoleReporter
                .forRegistry(metricRegistry)
                .build()
                .start(30, TimeUnit.SECONDS);
        return metricRegistry;
    }
}