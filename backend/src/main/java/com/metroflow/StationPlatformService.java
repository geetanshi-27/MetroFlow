
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

    

public int simulateDeparture(
        String stationId,
        String platformId,
        int trainCapacity) {

    // First select the correct station.
    StationPlatformManager manager = stationManagers.get(stationId);

    if (manager == null) {
        return -1;
    }

    // Then find the platform within that station.
    for (Platform platform : manager.getPlatforms()) {

        if (!platform.getPlatformId().equals(platformId)) {
            continue;
        }

        // Remember who was on the platform before departure.
        List<Passenger> beforeDeparture =
                new ArrayList<>(platform.getPassengers());

        int departed = manager.departTrainFromPlatform(
                platformId, trainCapacity);

        // Track passengers who actually left on the train.
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

    // This station exists, but the platform does not.
    return -1;
}




    
    
   
@Scheduled(
    initialDelay = 60000,
    fixedRate = 45000
)
public void simulateAutomaticDepartures() {

    // Keep each platform paired with its station ID.
    List<Platform> platforms = new ArrayList<>();
    List<String> stationIds = new ArrayList<>();

    for (StationPlatformManager manager : stationManagers.values()) {
        for (Platform platform : manager.getPlatforms()) {
            platforms.add(platform);
            stationIds.add(manager.getStationId());
        }
    }

    if (platforms.isEmpty()) {
        return;
    }

    // Search from the next platform in the rotation.
    for (int checked = 0; checked < platforms.size(); checked++) {

        int index = (nextPlatformIndex + checked)
                % platforms.size();

        Platform platform = platforms.get(index);

        if (platform.getCurrentPassengers() > 0) {

            nextPlatformIndex = (index + 1) % platforms.size();

            int departed = simulateDeparture(
                stationIds.get(index),
                platform.getPlatformId(),
                10
            );

            if (departed > 0) {
                System.out.println(
                    "Automatic train departed from station "
                    + stationIds.get(index)
                    + ", platform " + platform.getPlatformId()
                    + ", carrying " + departed + " passengers."
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
