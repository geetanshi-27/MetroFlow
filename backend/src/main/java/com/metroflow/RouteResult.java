
package com.metroflow;

import java.util.List;

public class RouteResult {
    private final List<String> stationIds;
    private final int totalTravelTimeMinutes;

    public RouteResult(List<String> stationIds,
                       int totalTravelTimeMinutes) {
        this.stationIds = stationIds;
        this.totalTravelTimeMinutes = totalTravelTimeMinutes;
    }

    public List<String> getStationIds() {
        return stationIds;
    }

    public int getTotalTravelTimeMinutes() {
        return totalTravelTimeMinutes;
    }
}
