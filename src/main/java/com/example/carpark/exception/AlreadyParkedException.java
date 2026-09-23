package com.example.carpark.exception;

public class AlreadyParkedException extends RuntimeException {
    public AlreadyParkedException(String reg) {
        super(reg + " is already parked");
    }
}
