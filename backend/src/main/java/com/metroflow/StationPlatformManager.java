
package com.metroflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StationPlatformManager {

    private final String stationId;
    private final List<Platform> platforms;

    public StationPlatformManager(String stationId) {
        this.stationId = stationId;
        this.platforms = new ArrayList<>();
    }

    public String getStationId() {
        return stationId;
    }

    public void addPlatform(Platform platform) {
        platforms.add(platform);
    }

    public List<Platform> getPlatforms() {
        return Collections.unmodifiableList(platforms);
    }

    public Platform findAvailablePlatform(String destinationStationId) {
        for (Platform platform : platforms) {
            if (platform.getDirectionStationId()
                    .equals(destinationStationId)
                    && platform.hasSpace()) {
                return platform;
            }
        }

        return null;
    }


public PlatformAdmissionResult requestAdmission(
        Passenger passenger,
        String destinationStationId) {

    if (passenger == null || destinationStationId == null) {
        throw new IllegalArgumentException(
                "Passenger and destination are required."
        );
    }

    // First, look for a suitable platform with available space.
    Platform availablePlatform =
            findAvailablePlatform(destinationStationId);

    if (availablePlatform != null) {
        boolean admitted = availablePlatform.admitPassenger(passenger);

        if (admitted) {
            return new PlatformAdmissionResult(
                    "ADMITTED",
                    availablePlatform.getPlatformId(),
                    0,
                    "Proceed to Platform "
                            + availablePlatform.getPlatformId() + "."
            );
        }
    }

    // No suitable platform has space. Find the shortest suitable queue.
    Platform selectedPlatform = null;

    for (Platform platform : platforms) {
        if (platform.getDirectionStationId()
                .equals(destinationStationId)) {

            if (selectedPlatform == null
                    || platform.getQueueSize()
                    < selectedPlatform.getQueueSize()) {
                selectedPlatform = platform;
            }
        }
    }

    if (selectedPlatform == null) {
        return new PlatformAdmissionResult(
                "NO_PLATFORM",
                null,
                0,
                "No platform serves this destination."
        );
    }

    boolean queued = selectedPlatform.addToQueue(passenger);

    if (!queued) {
        return new PlatformAdmissionResult(
                "ALREADY_REGISTERED",
                selectedPlatform.getPlatformId(),
                0,
                "This passenger is already on this platform or in its queue."
        );
    }

    return new PlatformAdmissionResult(
            "WAITING",
            selectedPlatform.getPlatformId(),
            selectedPlatform.getQueueSize(),
            "You are number "
                    + selectedPlatform.getQueueSize()
                    + " in the queue for Platform "
                    + selectedPlatform.getPlatformId() + "."
    );
}

public boolean releasePassengerFromPlatform(String platformId) {
    for (Platform platform : platforms) {
        if (platform.getPlatformId().equals(platformId)) {
            return platform.releasePassenger();
        }
    }

    return false;
}

}
