
package com.metroflow;

public class PassengerStatus {
    private final String passengerId;
    private final String status;
    private final String platformId;
    private final int queuePosition;
    private final int estimatedWaitingMinutes;
    private final String message;

    public PassengerStatus(
            String passengerId,
            String status,
            String platformId,
            int queuePosition,
            int estimatedWaitingMinutes,
            String message) {
        this.passengerId = passengerId;
        this.status = status;
        this.platformId = platformId;
        this.queuePosition = queuePosition;
        this.estimatedWaitingMinutes = estimatedWaitingMinutes;
        this.message = message;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public String getStatus() {
        return status;
    }

    public String getPlatformId() {
        return platformId;
    }

    public int getQueuePosition() {
        return queuePosition;
    }

    public int getEstimatedWaitingMinutes() {
        return estimatedWaitingMinutes;
    }

    public String getMessage() {
        return message;
    }
}
