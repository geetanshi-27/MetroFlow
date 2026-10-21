
package com.metroflow;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StationPlatformService {

    private final StationPlatformManager centralStation;

    public StationPlatformService() {
        this.centralStation = StationPlatformFactory.createCentralStation();
    }

    public StationPlatformManager getCentralStation() {
        return centralStation;
    }

    public List<PlatformStatus> getPlatformStatuses() {
        List<PlatformStatus> statuses = new ArrayList<>();

        for (Platform platform : centralStation.getPlatforms()) {
            statuses.add(
                    new PlatformStatus(
                            platform.getPlatformId(),
                            platform.getDirectionStationId(),
                            platform.getMaxCapacity(),
                            platform.getCurrentPassengers(),
                            platform.getQueueSize()));
        }

        return statuses;
    }

    public PlatformAdmissionResult requestAdmission(
            String passengerId,
            String destinationStationId) {

        Passenger passenger = new Passenger(passengerId);

        return centralStation.requestAdmission(
                passenger,
                destinationStationId);
    }


public PassengerStatus getPassengerStatus(String passengerId) {
    for (Platform platform : centralStation.getPlatforms()) {

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

    return new PassengerStatus(
        passengerId,
        "NOT_FOUND",
        null,
        0,
        0,
        "No active platform admission or queue entry was found for this passenger."
    );
}

public boolean simulateDeparture(String platformId) {
    return centralStation.releasePassengerFromPlatform(platformId);
}


}
