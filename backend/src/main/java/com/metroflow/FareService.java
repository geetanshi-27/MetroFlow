
package com.metroflow;

import org.springframework.stereotype.Service;

@Service
public class FareService {

    public int calculateBaseFare(int numberOfStops) {

        if (numberOfStops <= 0) {
            throw new IllegalArgumentException(
                "Number of stops must be positive."
            );
        }

        if (numberOfStops == 1) {
            return 10;
        } else if (numberOfStops == 2) {
            return 15;
        } else {
            return 20;
        }
    }


    public FareQuote calculateFare(
            RouteResult route) {

        int numberOfStops =
                route.getStationIds().size() - 1;

        int baseFare = calculateBaseFare(numberOfStops);

        Ticket adult = new AdultTicket();
        Ticket child = new ChildTicket();
        Ticket senior = new SeniorTicket();

        return new FareQuote(
                numberOfStops,
                baseFare,
                adult.calculateFare(baseFare),
                child.calculateFare(baseFare),
                senior.calculateFare(baseFare)
        );
    }

}
