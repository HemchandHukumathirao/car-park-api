package com.example.carpark.dto;

import java.time.Instant;

public record ParkResponse(String vehicleReg, int spaceNumber, Instant timeIn) {
}
