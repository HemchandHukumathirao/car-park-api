package com.example.carpark.repository;

import com.example.carpark.model.ParkedVehicle;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// in-memory store, no database (brief asked for that). keyed by reg.
@Repository
public class ParkingStore {

    private final ConcurrentMap<String, ParkedVehicle> parked = new ConcurrentHashMap<>();

    public boolean contains(String reg) {
        return parked.containsKey(reg);
    }

    public void save(ParkedVehicle vehicle) {
        parked.put(vehicle.vehicleReg(), vehicle);
    }

    public ParkedVehicle remove(String reg) {
        return parked.remove(reg);
    }

    public int count() {
        return parked.size();
    }

    public boolean spaceTaken(int space) {
        return parked.values().stream().anyMatch(v -> v.spaceNumber() == space);
    }
}
