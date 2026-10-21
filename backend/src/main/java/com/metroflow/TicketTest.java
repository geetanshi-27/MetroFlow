
package com.metroflow;

public class TicketTest {

    public static void main(String[] args) {

        int baseFare = 20;

        Ticket adult = new AdultTicket();
        Ticket child = new ChildTicket();
        Ticket senior = new SeniorTicket();

        System.out.println("Base fare: Rs. " + baseFare);
        System.out.println("Adult fare: Rs. "
                + adult.calculateFare(baseFare));
        System.out.println("Child fare: Rs. "
                + child.calculateFare(baseFare));
        System.out.println("Senior fare: Rs. "
                + senior.calculateFare(baseFare));
    }
}
