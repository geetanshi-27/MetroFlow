
package com.metroflow;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

import java.util.LinkedHashMap;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class StationController {

    
@GetMapping("/api/stations")
public List<Map<String, Object>> getStations() {

    MetroNetwork network = MetroNetworkFactory.createSampleNetwork();

    List<Map<String, Object>> result = new java.util.ArrayList<>();

    for (Station station : network.getStations()) {
        Map<String, Object> stationData = new LinkedHashMap<>();

        stationData.put("id", station.getId());
        stationData.put("name", station.getName());
        stationData.put("occupancyPercentage",
                station.getOccupancyPercentage());
        stationData.put("crowdLevel", station.getCrowdLevel());

        result.add(stationData);
    }

    return result;
}


    @GetMapping("/api/network")
    public Map<String, List<Connection>> getNetwork() {
        MetroNetwork network = MetroNetworkFactory.createSampleNetwork();

        Map<String, List<Connection>> result = new LinkedHashMap<>();

        for (String stationId : network.getStationIds()) {
            result.put(stationId, network.getConnections(stationId));
        }

        return result;
    }

    @PostMapping("/api/routes")
    public ResponseEntity<RouteResult> findRoute(
            @RequestBody Map<String, String> request) {

        String source = request.get("sourceId");
        String destination = request.get("destinationId");

        if (source == null || destination == null) {
            return ResponseEntity.badRequest().build();
        }

        MetroNetwork network = MetroNetworkFactory.createSampleNetwork();

        String strategyName = request.get("strategy");

        RouteStrategy strategy;

        if (strategyName == null
                || strategyName.trim().isEmpty()
                || strategyName.equalsIgnoreCase("fastest")) {

            strategy = new FastestRouteStrategy();

        } else if (strategyName.equalsIgnoreCase("crowd-aware")) {

            strategy = new CrowdAwareRouteStrategy();

        } else {
            return ResponseEntity.badRequest().build();
        }

        RouteResult result = strategy.findRoute(
                network, source, destination);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

}
