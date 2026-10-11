package com.metroflow;

public class Connection {

    private final String fromStationId;
    private final String toStationId;
    private final int travelTimeMinutes;
    private final int crowdLevel;

    public Connection(String fromStationId,
                      String toStationId,
                      int travelTimeMinutes) {
        this(fromStationId, toStationId, travelTimeMinutes, 1);
    }

    public Connection(String fromStationId,
                      String toStationId,
                      int travelTimeMinutes,
                      int crowdLevel) {
        this.fromStationId = fromStationId;
        this.toStationId = toStationId;
        this.travelTimeMinutes = travelTimeMinutes;
        this.crowdLevel = Math.max(1, Math.min(3, crowdLevel));
    }

    public String getFromStationId() {
        return fromStationId;
    }

    public String getToStationId() {
        return toStationId;
    }

    public int getTravelTimeMinutes() {
        return travelTimeMinutes;
    }

    public int getCrowdLevel() {
        return crowdLevel;
    }
}