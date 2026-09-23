package com.example.carpark.dto;

import jakarta.validation.constraints.NotBlank;

public record BillRequest(@NotBlank String vehicleReg) {
}
