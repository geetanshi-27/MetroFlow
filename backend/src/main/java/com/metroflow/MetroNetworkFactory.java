
package com.metroflow;

public class MetroNetworkFactory {

    public static MetroNetwork createSampleNetwork() {
        MetroNetwork network = new MetroNetwork();

        // Create our five stations
        network.addStation("S01"); // Central
        network.addStation("S02"); // Museum
        network.addStation("S03"); // University
        network.addStation("S04"); // City Hospital
        network.addStation("S05"); // Airport

        // Add connections with travel times in minutes
        // Add connections with travel time and crowd level
network.addConnection("S01", "S02", 4, 2);
network.addConnection("S02", "S03", 6, 1);
network.addConnection("S02", "S04", 3, 3);
network.addConnection("S03", "S05", 5, 1);
network.addConnection("S04", "S05", 8, 3);

        return network;
    }
}
