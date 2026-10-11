
package com.metroflow;

import java.util.*;

public class MetroNetwork {

    private final Map<String, List<Connection>> adjacencyList;

    public MetroNetwork() {
        adjacencyList = new HashMap<>();
    }

    public void addStation(String stationId) {
        adjacencyList.putIfAbsent(stationId, new ArrayList<>());
    }

    public void addConnection(String from, String to, int travelTime) {
        addConnection(from, to, travelTime, 1);
    }

    public void addConnection(
            String from,
            String to,
            int travelTime,
            int crowdLevel) {

        adjacencyList.get(from).add(
                new Connection(from, to, travelTime, crowdLevel));

        adjacencyList.get(to).add(
                new Connection(to, from, travelTime, crowdLevel));
    }

    public List<Connection> getConnections(String stationId) {
        return adjacencyList.getOrDefault(
                stationId, Collections.emptyList());
    }

    public Set<String> getStationIds() {
        return adjacencyList.keySet();
    }
}
