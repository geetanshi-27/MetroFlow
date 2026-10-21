
package com.metroflow;

public class SeniorTicket extends Ticket {

    @Override
    public int calculateFare(int baseFare) {
        return (int) Math.round(baseFare * 0.70);
    }
}
