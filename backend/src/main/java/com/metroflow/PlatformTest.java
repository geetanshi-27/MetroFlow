
package com.metroflow;

public class PlatformTest {

    public static void main(String[] args) {

        Platform platform = new Platform("PL01", 3, "S05");

        Passenger p1 = new Passenger("P001");
        Passenger p2 = new Passenger("P002");
        Passenger p3 = new Passenger("P003");
        Passenger p4 = new Passenger("P004");
        Passenger p5 = new Passenger("P005");

        // Fill the platform
        platform.admitPassenger(p1);
        platform.admitPassenger(p2);
        platform.admitPassenger(p3);

        // These passengers must wait
        platform.addToQueue(p4);
        platform.addToQueue(p5);

        System.out.println("Passengers on platform:");
        for (Passenger passenger : platform.getPassengers()) {
            System.out.println(passenger.getPassengerId());
        }

        System.out.println("Waiting queue size: "
                + platform.getQueueSize());

        // One passenger leaves; P004 should be admitted
        platform.releasePassenger();

        System.out.println("\nAfter one passenger leaves:");

        System.out.println("Passengers on platform:");
        for (Passenger passenger : platform.getPassengers()) {
            System.out.println(passenger.getPassengerId());
        }

        System.out.println("Waiting queue size: "
                + platform.getQueueSize());

        System.out.println("\n--- Duplicate Admission Test ---");

        Passenger duplicate = new Passenger("P002");

        System.out.println(
                "Admit existing passenger again: "
                        + platform.admitPassenger(duplicate));

        System.out.println(
                "Queue existing passenger again: "
                        + platform.addToQueue(duplicate));

        System.out.println(
                "Final passenger count: "
                        + platform.getCurrentPassengers());

        System.out.println(
                "Final queue size: "
                        + platform.getQueueSize());
    }

}
