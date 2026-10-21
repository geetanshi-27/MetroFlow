
package com.metroflow;

public class Passenger {

    private final String passengerId;

    public Passenger(String passengerId) {
        if (passengerId == null || passengerId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Passenger ID cannot be empty."
            );
        }

        this.passengerId = passengerId;
    }

    public String getPassengerId() {
        return passengerId;
    }
}
