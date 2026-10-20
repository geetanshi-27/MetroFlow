
package com.metroflow;

public class MetroNetworkFactory {

    public static MetroNetwork createSampleNetwork() {
        MetroNetwork network = new MetroNetwork();

        network.addStation("S01", "Central", 45);
        network.addStation("S02", "Museum", 68);
        network.addStation("S03", "University", 30);
        network.addStation("S04", "City Hospital", 88);
        network.addStation("S05", "Airport", 55);

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
