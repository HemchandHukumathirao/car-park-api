package com.example.carpark.exception;

public class NotParkedException extends RuntimeException {
    public NotParkedException(String reg) {
        super("No parked vehicle found for " + reg);
    }
}
