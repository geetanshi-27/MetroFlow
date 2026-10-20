
package com.metroflow;

public class CrowdTest {

    public static void main(String[] args) {

        MetroNetwork network =
                MetroNetworkFactory.createSampleNetwork();

        for (Station station : network.getStations()) {
            System.out.println(
                    station.getName()
                    + " | Occupancy: "
                    + station.getOccupancyPercentage()
                    + "% | Crowd: "
                    + station.getCrowdLevel()
            );
        }
    }
}
