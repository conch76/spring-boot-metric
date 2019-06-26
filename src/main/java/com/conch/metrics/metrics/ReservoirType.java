package com.conch.metrics.metrics;

import com.codahale.metrics.*;

public enum ReservoirType {

    EXPONENTIAL_DECAY(ExponentiallyDecayingReservoir.class),
    SLIDING_TIME_WINDOW(SlidingTimeWindowArrayReservoir.class),
    UNIFORM(UniformReservoir.class),
    SLIDING_WINDOW(SlidingWindowReservoir.class);

    Class<? extends Reservoir> reservoirClass;

    ReservoirType(Class<? extends Reservoir> reservoirClass) {
        this.reservoirClass = reservoirClass;
    }
}
