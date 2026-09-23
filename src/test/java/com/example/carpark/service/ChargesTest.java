package com.example.carpark.service;

import com.example.carpark.model.VehicleType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChargesTest {

    @Test
    void smallCarFor12Minutes() {
        Instant in = Instant.parse("2026-09-22T10:00:00Z");
        Instant out = in.plusSeconds(12 * 60);
        // 12 * 0.10 + 2 * 1.00 = 3.20
        assertEquals(3.20, Charges.calculate(VehicleType.SMALL, in, out), 0.0001);
    }

    @Test
    void largeCarForFourMinutesHasNoFiveMinuteBlock() {
        Instant in = Instant.parse("2026-09-22T10:00:00Z");
        Instant out = in.plusSeconds(4 * 60);
        // 4 * 0.40 = 1.60, no full 5 min block yet
        assertEquals(1.60, Charges.calculate(VehicleType.LARGE, in, out), 0.0001);
    }
}
