
package com.metroflow;

import java.util.List;

public class RouteResult {
    private final List<String> stationIds;
    private final int totalTravelTimeMinutes;
    private final List<RouteStationDetail> stationDetails;

    public RouteResult(
        List<String> stationIds,
        int totalTravelTimeMinutes,
        List<RouteStationDetail> stationDetails) {

    this.stationIds = stationIds;
    this.totalTravelTimeMinutes = totalTravelTimeMinutes;
    this.stationDetails = stationDetails;
}

    public List<String> getStationIds() {
        return stationIds;
    }

    public int getTotalTravelTimeMinutes() {
        return totalTravelTimeMinutes;
    }
    public List<RouteStationDetail> getStationDetails() {
    return stationDetails;
}
}
