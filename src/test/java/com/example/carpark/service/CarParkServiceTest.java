package com.example.carpark.service;

import com.example.carpark.exception.CarParkFullException;
import com.example.carpark.exception.NotParkedException;
import com.example.carpark.repository.ParkingStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarParkServiceTest {

    private CarParkService newCarPark(int capacity) {
        return new CarParkService(new ParkingStore(), capacity);
    }

    @Test
    void parkThenBillFreesTheSpace() {
        CarParkService park = newCarPark(2);

        park.park("AB12 CDE", 1);
        assertEquals(1, park.spaces().occupiedSpaces());

        park.bill("AB12 CDE");
        assertEquals(0, park.spaces().occupiedSpaces());
    }

    @Test
    void fullCarParkIsRejected() {
        CarParkService park = newCarPark(1);
        park.park("ONLY1", 1);
        assertThrows(CarParkFullException.class, () -> park.park("NEXT1", 1));
    }

    @Test
    void billingAnUnknownRegBlowsUp() {
        CarParkService park = newCarPark(5);
        assertThrows(NotParkedException.class, () -> park.bill("GHOST1"));
    }
}
