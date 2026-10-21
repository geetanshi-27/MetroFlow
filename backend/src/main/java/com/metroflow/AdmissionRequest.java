
package com.metroflow;

public class AdmissionRequest {

    private String passengerId;
    private String destinationStationId;
    private String stationId;

    public AdmissionRequest() {
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getDestinationStationId() {
        return destinationStationId;
    }

    public void setDestinationStationId(String destinationStationId) {
        this.destinationStationId = destinationStationId;
    }
    public String getStationId() {
    return stationId;
}

public void setStationId(String stationId) {
    this.stationId = stationId;
}
}
