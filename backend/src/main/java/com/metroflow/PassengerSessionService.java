
package com.metroflow;

import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PassengerSessionService {

    private final AtomicInteger nextPassengerNumber =
            new AtomicInteger(1);

    public PassengerSession createSession() {
        int number = nextPassengerNumber.getAndIncrement();

        String passengerId = String.format("P%04d", number);

        return new PassengerSession(passengerId);
    }
}
