
package com.metroflow;

public class FareQuote {

    private final int numberOfStops;
    private final int baseFare;
    private final int adultFare;
    private final int childFare;
    private final int seniorFare;

    public FareQuote(
            int numberOfStops,
            int baseFare,
            int adultFare,
            int childFare,
            int seniorFare) {

        this.numberOfStops = numberOfStops;
        this.baseFare = baseFare;
        this.adultFare = adultFare;
        this.childFare = childFare;
        this.seniorFare = seniorFare;
    }

    public int getNumberOfStops() {
        return numberOfStops;
    }

    public int getBaseFare() {
        return baseFare;
    }

    public int getAdultFare() {
        return adultFare;
    }

    public int getChildFare() {
        return childFare;
    }

    public int getSeniorFare() {
        return seniorFare;
    }
}
