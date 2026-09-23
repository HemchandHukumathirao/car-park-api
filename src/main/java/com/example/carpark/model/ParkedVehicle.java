package com.example.carpark.model;

import java.time.Instant;

public record ParkedVehicle(String vehicleReg, VehicleType type, int spaceNumber, Instant timeIn) {
}
