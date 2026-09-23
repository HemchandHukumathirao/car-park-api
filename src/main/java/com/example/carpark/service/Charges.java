package com.example.carpark.service;

import com.example.carpark.model.VehicleType;

import java.time.Duration;
import java.time.Instant;

// per-minute rate, plus a quid for every full 5 mins
final class Charges {

    private Charges() {
    }

    static double calculate(VehicleType type, Instant in, Instant out) {
        long minutes = Duration.between(in, out).toMinutes();
        long pence = minutes * type.pencePerMinute() + (minutes / 5) * 100;
        return pence / 100.0;
    }
}
