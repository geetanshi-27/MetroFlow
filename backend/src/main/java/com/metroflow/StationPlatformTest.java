
package com.metroflow;

public class StationPlatformTest {

    public static void main(String[] args) {

        StationPlatformManager central =
                StationPlatformFactory.createCentralStation();

        // Fill the Airport-bound platform (P02).
        Platform airportPlatform =
                central.findAvailablePlatform("S05");

        airportPlatform.admitPassenger(new Passenger("P0001"));
        airportPlatform.admitPassenger(new Passenger("P0002"));
        airportPlatform.admitPassenger(new Passenger("P0003"));

        // Passenger 4 must wait because P02 is full.
        PlatformAdmissionResult waitingResult =
                central.requestAdmission(
                        new Passenger("P0004"),
                        "S05"
                );

        System.out.println("--- Passenger 4 ---");
        System.out.println("Status: " + waitingResult.getStatus());
        System.out.println("Platform: " + waitingResult.getPlatformId());
        System.out.println("Queue position: "
                + waitingResult.getQueuePosition());
        System.out.println("Message: " + waitingResult.getMessage());

        // Passenger 5 travels toward Museum and can use P01.
        PlatformAdmissionResult admittedResult =
                central.requestAdmission(
                        new Passenger("P0005"),
                        "S02"
                );

        System.out.println("\n--- Passenger 5 ---");
        System.out.println("Status: " + admittedResult.getStatus());
        System.out.println("Platform: " + admittedResult.getPlatformId());
        System.out.println("Message: " + admittedResult.getMessage());

        // Simulate a departure from P02.
        System.out.println("\n--- Simulate Train Departure ---");

        boolean released =
                central.releasePassengerFromPlatform("P02");

        System.out.println("Passenger released: " + released);
        System.out.println("Passengers on P02: "
                + airportPlatform.getCurrentPassengers());
        System.out.println("Waiting queue size: "
                + airportPlatform.getQueueSize());

        System.out.println("Passengers now on P02:");
        for (Passenger passenger : airportPlatform.getPassengers()) {
            System.out.println(passenger.getPassengerId());
        }
    }
}
