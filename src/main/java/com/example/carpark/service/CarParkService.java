package com.example.carpark.service;

import com.example.carpark.dto.BillResponse;
import com.example.carpark.dto.SpacesResponse;
import com.example.carpark.exception.AlreadyParkedException;
import com.example.carpark.exception.CarParkFullException;
import com.example.carpark.exception.NotParkedException;
import com.example.carpark.model.ParkedVehicle;
import com.example.carpark.model.VehicleType;
import com.example.carpark.repository.ParkingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class CarParkService {

    private final ParkingStore store;
    private final int capacity;

    public CarParkService(ParkingStore store, @Value("${carpark.capacity:10}") int capacity) {
        this.store = store;
        this.capacity = capacity;
    }

    public SpacesResponse spaces() {
        int occupied = store.count();
        return new SpacesResponse(capacity - occupied, occupied);
    }

    public synchronized ParkedVehicle park(String reg, int typeCode) {
        VehicleType type = VehicleType.fromCode(typeCode);

        if (store.contains(reg)) {
            throw new AlreadyParkedException(reg);
        }

        int space = firstFreeSpace();
        if (space == -1) {
            throw new CarParkFullException();
        }

        ParkedVehicle vehicle = new ParkedVehicle(reg, type, space, Instant.now());
        store.save(vehicle);
        return vehicle;
    }

    public BillResponse bill(String reg) {
        ParkedVehicle vehicle = store.remove(reg);
        if (vehicle == null) {
            throw new NotParkedException(reg);
        }

        Instant timeOut = Instant.now();
        double charge = Charges.calculate(vehicle.type(), vehicle.timeIn(), timeOut);

        return new BillResponse(
                UUID.randomUUID().toString(),
                reg,
                charge,
                vehicle.timeIn(),
                timeOut);
    }

    private int firstFreeSpace() {
        for (int space = 1; space <= capacity; space++) {
            if (!store.spaceTaken(space)) {
                return space;
            }
        }
        return -1;
    }
}
