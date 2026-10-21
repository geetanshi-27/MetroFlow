
package com.metroflow;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/platforms")
@CrossOrigin(origins = "http://localhost:5173")
public class PlatformController {

    private final StationPlatformService platformService;

    public PlatformController(StationPlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public List<PlatformStatus> getPlatforms() {
        return platformService.getPlatformStatuses();
    }


@PostMapping("/admission")
public PlatformAdmissionResult requestAdmission(
        @RequestBody AdmissionRequest request) {

    return platformService.requestAdmission(
            request.getPassengerId(),
            request.getDestinationStationId()
    );
}


@GetMapping("/status/{passengerId}")
public PassengerStatus getPassengerStatus(
        @PathVariable String passengerId) {
    return platformService.getPassengerStatus(passengerId);
}


@PostMapping("/{platformId}/departure")
public String simulateDeparture(
        @PathVariable String platformId) {

    boolean released = platformService.simulateDeparture(platformId);

    if (released) {
        return "Departure simulated for Platform " + platformId
            + ". The next queued passenger, if any, has been admitted.";
    }

    return "No passenger could be released from Platform " + platformId
        + ". Check the platform ID and current occupancy.";
}



}
