
package com.metroflow;

public class RouteStationDetail {

    private final String id;
    private final String name;
    private final int occupancyPercentage;
    private final CrowdLevel crowdLevel;

    public RouteStationDetail(
            String id,
            String name,
            int occupancyPercentage,
            CrowdLevel crowdLevel) {

        this.id = id;
        this.name = name;
        this.occupancyPercentage = occupancyPercentage;
        this.crowdLevel = crowdLevel;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOccupancyPercentage() {
        return occupancyPercentage;
    }

    public CrowdLevel getCrowdLevel() {
        return crowdLevel;
    }
}
