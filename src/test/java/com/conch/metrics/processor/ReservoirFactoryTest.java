package com.conch.metrics.processor;

import com.codahale.metrics.ExponentiallyDecayingReservoir;
import com.codahale.metrics.Reservoir;
import com.conch.metrics.metrics.ReservoirType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservoirFactoryTest {

    private ReservoirFactory reservoirFactory;

    @BeforeEach
    public void setUp() {
        reservoirFactory = new ReservoirFactory();
    }

    @Test
    public void resolveReservoir_should_resolve_supported_reservoir() {
        Reservoir reservoir = reservoirFactory.resolveReservoir(ReservoirType.EXPONENTIAL_DECAY)
                .apply(null);
        assertThat(reservoir).isInstanceOf(ExponentiallyDecayingReservoir.class);
    }

    @Test
    public void resolveReservoir_should_throw_illegalArgument_exception_when_no_param_given_to_sliding_reservoir() {
        assertThrows(IllegalArgumentException.class,
                () -> reservoirFactory.resolveReservoir(ReservoirType.SLIDING_TIME_WINDOW)
                        .apply(new String[]{})
        );
    }
}