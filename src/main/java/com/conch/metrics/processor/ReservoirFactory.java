package com.conch.metrics.processor;

import com.codahale.metrics.ExponentiallyDecayingReservoir;
import com.codahale.metrics.Reservoir;
import com.codahale.metrics.SlidingTimeWindowArrayReservoir;
import com.codahale.metrics.UniformReservoir;
import com.conch.metrics.config.MetricsAutoConfig;
import com.conch.metrics.metrics.ReservoirType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@Slf4j
@Conditional(MetricsAutoConfig.class)
public class ReservoirFactory {

    private Map<ReservoirType, Function<String [], Reservoir>> supportedReservoir = new HashMap<>();

    public ReservoirFactory() {
        supportedReservoir.put(ReservoirType.EXPONENTIAL_DECAY, (params) -> new ExponentiallyDecayingReservoir());
        supportedReservoir.put(ReservoirType.SLIDING_TIME_WINDOW, ReservoirFactory::applySlidingTimeWindow);
        supportedReservoir.put(ReservoirType.UNIFORM, ReservoirFactory::applyUniformReservoir);
    }

    private static Reservoir applySlidingTimeWindow(String[] params) {
        if (params.length != 2) {
            throw new IllegalArgumentException("Requires window and TimeUnit param");
        }
        log.info("Creating slding time window array reservoir");
        return new SlidingTimeWindowArrayReservoir(Long.valueOf(params[0]), TimeUnit.valueOf(params[1]));
    }

    private static Reservoir applyUniformReservoir(String[] params) {
        return params.length == 1 ? new UniformReservoir(Integer.parseInt(params[0])) : new UniformReservoir();
    }

    public Function<String [], Reservoir> resolveReservoir(ReservoirType reservoir) {
        return supportedReservoir.get(reservoir);
    }
}
