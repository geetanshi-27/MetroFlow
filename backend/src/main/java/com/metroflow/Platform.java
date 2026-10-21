
package com.metroflow;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

public class Platform {

    private final String platformId;
    private final int maxCapacity;
    private int currentPassengers;
    private final Queue<Passenger> waitingQueue;
    private final List<Passenger> passengers;
    private final String directionStationId;

    public Platform(
            String platformId,
            int maxCapacity,
            String directionStationId) {

        if (maxCapacity <= 0) {
            throw new IllegalArgumentException(
                    "Platform capacity must be positive.");
        }

        this.platformId = platformId;
        this.maxCapacity = maxCapacity;
        this.directionStationId = directionStationId;
        this.currentPassengers = 0;
        this.passengers = new ArrayList<>();
        this.waitingQueue = new ArrayDeque<>();
    }

    public String getPlatformId() {
        return platformId;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentPassengers() {
        return currentPassengers;
    }

    public boolean hasSpace() {
        return currentPassengers < maxCapacity;
    }

    public boolean admitPassenger() {
        if (!hasSpace()) {
            return false;
        }

        currentPassengers++;
        return true;
    }

    public boolean admitPassenger(Passenger passenger) {
        if (passenger == null
                || !hasSpace()
                || containsPassenger(passenger)) {
            return false;
        }

        passengers.add(passenger);
        currentPassengers++;

        return true;
    }

    public boolean releasePassenger() {
        if (passengers.isEmpty()) {
            return false;
        }

        passengers.remove(0);
        currentPassengers--;

        if (!waitingQueue.isEmpty()) {
            Passenger nextPassenger = waitingQueue.poll();
            passengers.add(nextPassenger);
            currentPassengers++;
        }

        return true;
    }


public int departTrain(int trainCapacity) {
    if (trainCapacity <= 0) {
        throw new IllegalArgumentException(
                "Train capacity must be positive.");
    }

    int passengersLeaving = Math.min(
            trainCapacity, passengers.size());

    for (int i = 0; i < passengersLeaving; i++) {
        passengers.remove(0);
    }

    currentPassengers -= passengersLeaving;

    while (hasSpace() && !waitingQueue.isEmpty()) {
        Passenger nextPassenger = waitingQueue.poll();
        passengers.add(nextPassenger);
        currentPassengers++;
    }

    return passengersLeaving;
}


    public boolean addToQueue(Passenger passenger) {
        if (passenger == null || containsPassenger(passenger)) {
            return false;
        }

        waitingQueue.offer(passenger);
        return true;
    }

    public int getQueueSize() {
        return waitingQueue.size();
    }

    public List<Passenger> getPassengers() {
        return java.util.Collections.unmodifiableList(passengers);
    }

    private boolean containsPassenger(Passenger passenger) {
        for (Passenger current : passengers) {
            if (current.getPassengerId().equals(
                    passenger.getPassengerId())) {
                return true;
            }
        }

        for (Passenger waiting : waitingQueue) {
            if (waiting.getPassengerId().equals(
                    passenger.getPassengerId())) {
                return true;
            }
        }

        return false;
    }

    public String getDirectionStationId() {
        return directionStationId;
    }

    public int getQueuePosition(String passengerId) {
        int position = 1;

        for (Passenger passenger : waitingQueue) {
            if (passenger.getPassengerId().equals(passengerId)) {
                return position;
            }

            position++;
        }

        return 0;
    }

    public boolean hasPassenger(String passengerId) {
        for (Passenger passenger : passengers) {
            if (passenger.getPassengerId().equals(passengerId)) {
                return true;
            }
        }
        return false;
    }

}
