package com.example.carpark.controller;

import com.example.carpark.dto.BillRequest;
import com.example.carpark.dto.BillResponse;
import com.example.carpark.dto.ParkRequest;
import com.example.carpark.dto.ParkResponse;
import com.example.carpark.dto.SpacesResponse;
import com.example.carpark.model.ParkedVehicle;
import com.example.carpark.service.CarParkService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final CarParkService service;

    public ParkingController(CarParkService service) {
        this.service = service;
    }

    @GetMapping
    public SpacesResponse spaces() {
        return service.spaces();
    }

    @PostMapping
    public ParkResponse park(@Valid @RequestBody ParkRequest request) {
        ParkedVehicle v = service.park(request.vehicleReg(), request.vehicleType());
        return new ParkResponse(v.vehicleReg(), v.spaceNumber(), v.timeIn());
    }

    @PostMapping("/bill")
    public BillResponse bill(@Valid @RequestBody BillRequest request) {
        return service.bill(request.vehicleReg());
    }
}
