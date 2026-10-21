
package com.metroflow;

public class PlatformAdmissionResult {

    private final String status;
    private final String platformId;
    private final int queuePosition;
    private final String message;

    public PlatformAdmissionResult(
            String status,
            String platformId,
            int queuePosition,
            String message) {

        this.status = status;
        this.platformId = platformId;
        this.queuePosition = queuePosition;
        this.message = message;
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

    public String getMessage() {
        return message;
    }
}
