
package com.metroflow;

public class ChildTicket extends Ticket {

    @Override
    public int calculateFare(int baseFare) {
        return (int) Math.round(baseFare * 0.50);
    }
}
