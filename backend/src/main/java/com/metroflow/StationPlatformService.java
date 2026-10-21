
package com.metroflow;

import java.util.ArrayList;
//import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class StationPlatformService {

    private final Map<String, StationPlatformManager> stationManagers;
    private final Set<String> departedPassengerIds =
        ConcurrentHashMap.newKeySet();
    private int nextPlatformIndex = 0;

    public StationPlatformService() {
        this.stationManagers = StationPlatformFactory.createAllStations();
    }

    public StationPlatformManager getStation(String stationId) {
        return stationManagers.get(stationId);
    }

    public List<PlatformStatus> getPlatformStatuses() {
        List<PlatformStatus> statuses = new ArrayList<>();

        for (StationPlatformManager manager : stationManagers.values()) {
            for (Platform platform : manager.getPlatforms()) {
                statuses.add(
                    new PlatformStatus(
    manager.getStationId(),
    platform.getPlatformId(),
    platform.getDirectionStationId(),
    platform.getMaxCapacity(),
    platform.getCurrentPassengers(),
    platform.getQueueSize()
)
                );
            }
        }

        return statuses;
    }

    public PlatformAdmissionResult requestAdmission(
            String stationId,
            String passengerId,
            String destinationStationId) {

        StationPlatformManager manager = stationManagers.get(stationId);

        if (manager == null) {
            return new PlatformAdmissionResult(
                "STATION_NOT_FOUND",
                null,
                0,
                "No platform manager exists for station " + stationId + "."
            );
        }

        Passenger passenger = new Passenger(passengerId);
        return manager.requestAdmission(passenger, destinationStationId);
    }

    public PassengerStatus getPassengerStatus(String passengerId) {
        for (StationPlatformManager manager : stationManagers.values()) {
            for (Platform platform : manager.getPlatforms()) {

                if (platform.hasPassenger(passengerId)) {
                    return new PassengerStatus(
                        passengerId,
                        "ADMITTED",
                        platform.getPlatformId(),
                        0,
                        0,
                        "You have been admitted to Platform "
                            + platform.getPlatformId() + "."
                    );
                }

                int position = platform.getQueuePosition(passengerId);

                if (position > 0) {
                    return new PassengerStatus(
                        passengerId,
                        "WAITING",
                        platform.getPlatformId(),
                        position,
                        position * 3,
                        "You are number " + position
                            + " in the queue for Platform "
                            + platform.getPlatformId() + "."
                    );
                }
            }
        }

        if (departedPassengerIds.contains(passengerId)) {
    return new PassengerStatus(
            passengerId,
            "DEPARTED",
            null,
            0,
            0,
            "Your train has departed. You have left the platform."
    );
}

        return new PassengerStatus(
            passengerId,
            "NOT_FOUND",
            null,
            0,
            0,
            "No active platform admission or queue entry was found for this passenger."
        );
    }

    
public int simulateDeparture(String platformId, int trainCapacity) {
    for (StationPlatformManager manager : stationManagers.values()) {
        for (Platform platform : manager.getPlatforms()) {

            if (!platform.getPlatformId().equals(platformId)) {
                continue;
            }

            // Remember who was on the platform before departure.
            List<Passenger> beforeDeparture =
                    new ArrayList<>(platform.getPassengers());

            int departed = manager.departTrainFromPlatform(
                    platformId, trainCapacity);

            // Identify passengers removed by the departing train.
            if (departed > 0) {
                for (Passenger passenger : beforeDeparture) {
                    if (!platform.hasPassenger(
                            passenger.getPassengerId())) {
                        departedPassengerIds.add(
                                passenger.getPassengerId());
                    }
                }
            }

            return departed;
        }
    }

    return -1;
}



    
    
    @Scheduled(
    initialDelay = 60000,
    fixedRate = 45000
)
    public void simulateAutomaticDepartures() {

        // Collect platforms in a consistent order.
        List<Platform> platforms = new ArrayList<>();

        for (StationPlatformManager manager : stationManagers.values()) {
            platforms.addAll(manager.getPlatforms());
        }

        if (platforms.isEmpty()) {
            return;
        }

        // Search from the next platform in the rotation.
        for (int checked = 0; checked < platforms.size(); checked++) {

            int index = (nextPlatformIndex + checked)
                    % platforms.size();

            Platform platform = platforms.get(index);

            // Depart from the first occupied platform we find.
            if (platform.getCurrentPassengers() > 0) {

                // Next run starts after this platform.
                nextPlatformIndex = (index + 1) % platforms.size();

                int departed = simulateDeparture(
                    platform.getPlatformId(), 10
                );

                if (departed > 0) {
                    System.out.println(
                        "Automatic train departed from "
                        + platform.getPlatformId()
                        + " carrying " + departed + " passengers."
                    );
                }

                return;
            }
        }

        System.out.println(
            "No passengers available for automatic departure."
        );
    }



}
