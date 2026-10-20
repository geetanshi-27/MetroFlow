
package com.metroflow;

public class Station {

    private final String id;
    private final String name;
    private int occupancyPercentage;

    public Station(String id, String name, int occupancyPercentage) {
        this.id = id;
        this.name = name;
        setOccupancyPercentage(occupancyPercentage);
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

    public void setOccupancyPercentage(int occupancyPercentage) {
        if (occupancyPercentage < 0 || occupancyPercentage > 100) {
            throw new IllegalArgumentException(
                    "Occupancy must be between 0 and 100."
            );
        }

        this.occupancyPercentage = occupancyPercentage;
    }

    public CrowdLevel getCrowdLevel() {
        if (occupancyPercentage < 40) {
            return CrowdLevel.LOW;
        } else if (occupancyPercentage < 60) {
            return CrowdLevel.MODERATE;
        } else if (occupancyPercentage < 80) {
            return CrowdLevel.HIGH;
        } else {
            return CrowdLevel.CRITICAL;
        }
    }
}
