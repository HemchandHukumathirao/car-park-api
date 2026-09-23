package com.example.carpark.dto;

import java.time.Instant;

public record BillResponse(
        String billId,
        String vehicleReg,
        double vehicleCharge,
        Instant timeIn,
        Instant timeOut) {
}
