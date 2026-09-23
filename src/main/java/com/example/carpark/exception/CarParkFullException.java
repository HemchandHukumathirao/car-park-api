package com.example.carpark.exception;

public class CarParkFullException extends RuntimeException {
    public CarParkFullException() {
        super("Car park is full");
    }
}
