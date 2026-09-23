package com.example.carpark.model;

// rates in pence a minute, keeps the money maths off doubles
public enum VehicleType {

    SMALL(1, 10),
    MEDIUM(2, 20),
    LARGE(3, 40);

    private final int code;
    private final int pencePerMinute;

    VehicleType(int code, int pencePerMinute) {
        this.code = code;
        this.pencePerMinute = pencePerMinute;
    }

    public int pencePerMinute() {
        return pencePerMinute;
    }

    public static VehicleType fromCode(int code) {
        for (VehicleType t : values()) {
            if (t.code == code) {
                return t;
            }
        }
        throw new IllegalArgumentException("vehicleType must be 1, 2 or 3 but got " + code);
    }
}
