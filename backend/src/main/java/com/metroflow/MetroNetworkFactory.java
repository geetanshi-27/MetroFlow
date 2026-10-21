
package com.metroflow;

public class MetroNetworkFactory {

    public static MetroNetwork createSampleNetwork() {
        MetroNetwork network = new MetroNetwork();

        // 1. Create all nine stations with their occupancy percentages.
        network.addStation("S01", "Central", 45);
        network.addStation("S02", "Museum", 68);
        network.addStation("S03", "University", 30);
        network.addStation("S04", "City Hospital", 88);
        network.addStation("S05", "Airport", 55);
        network.addStation("S06", "Stadium", 92);
        network.addStation("S07", "Tech Park", 35);
        network.addStation("S08", "Market Square", 82);
        network.addStation("S09", "Riverside", 25);

        // 2. Main corridor.
        network.addConnection("S01", "S02", 4, 2);
        network.addConnection("S02", "S03", 5, 1);
        network.addConnection("S03", "S05", 5, 1);

        // 3. Alternative route through City Hospital.
        network.addConnection("S02", "S04", 3, 3);
        network.addConnection("S04", "S05", 4, 3);

        // 4. Branch through Stadium and Market Square.
        network.addConnection("S01", "S06", 3, 3);
        network.addConnection("S06", "S08", 4, 3);
        network.addConnection("S08", "S09", 4, 2);

        // 5. Branch to Tech Park.
        network.addConnection("S01", "S07", 6, 1);

        return network;
    }
}
