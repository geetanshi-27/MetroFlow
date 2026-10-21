
package com.metroflow;

public class StationPlatformFactory {

    public static StationPlatformManager createCentralStation() {

        StationPlatformManager manager =
                new StationPlatformManager("S01");

        // Platform 1 serves passengers travelling toward Museum.
        manager.addPlatform(
                new Platform("P01", 3, "S02")
        );

        // Platform 2 serves passengers travelling toward Airport.
        manager.addPlatform(
                new Platform("P02", 3, "S05")
        );

        return manager;
    }
}
