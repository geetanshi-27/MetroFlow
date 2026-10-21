
package com.metroflow;

public class PlatformStatus {

    private final String platformId;
    private final String directionStationId;
    private final int maxCapacity;
    private final int currentPassengers;
    private final int waitingCount;

    public PlatformStatus(
            String platformId,
            String directionStationId,
            int maxCapacity,
            int currentPassengers,
            int waitingCount) {

        this.platformId = platformId;
        this.directionStationId = directionStationId;
        this.maxCapacity = maxCapacity;
        this.currentPassengers = currentPassengers;
        this.waitingCount = waitingCount;
    }

    public String getPlatformId() {
        return platformId;
    }

    public String getDirectionStationId() {
        return directionStationId;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentPassengers() {
        return currentPassengers;
    }

    public int getWaitingCount() {
        return waitingCount;
    }
}
