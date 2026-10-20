
package com.metroflow;

import java.util.*;

public class MetroNetwork {

    private final Map<String, List<Connection>> adjacencyList;
    private final Map<String, Station> stations;

    public MetroNetwork() {
        adjacencyList = new HashMap<>();
        stations = new LinkedHashMap<>();
    }

    public void addStation(String stationId) {
        addStation(stationId, stationId, 0);
    }

    public void addStation(
            String stationId,
            String name,
            int occupancyPercentage) {

        adjacencyList.putIfAbsent(stationId, new ArrayList<>());

        stations.put(
                stationId,
                new Station(stationId, name, occupancyPercentage));
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

    public Station getStation(String stationId) {
        return stations.get(stationId);
    }

    public Collection<Station> getStations() {
        return Collections.unmodifiableCollection(stations.values());
    }

    public List<Connection> getConnections(String stationId) {
        return adjacencyList.getOrDefault(
                stationId, Collections.emptyList());
    }

    public Set<String> getStationIds() {
        return adjacencyList.keySet();
    }
}
