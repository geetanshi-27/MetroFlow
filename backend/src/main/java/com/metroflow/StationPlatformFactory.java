
package com.metroflow;

import java.util.LinkedHashMap;
import java.util.Map;

public class StationPlatformFactory {

    public static Map<String, StationPlatformManager> createAllStations() {
        Map<String, StationPlatformManager> managers = new LinkedHashMap<>();

        // Create a platform manager for each station.
        managers.put("S01", new StationPlatformManager("S01"));
        managers.put("S02", new StationPlatformManager("S02"));
        managers.put("S03", new StationPlatformManager("S03"));
        managers.put("S04", new StationPlatformManager("S04"));
        managers.put("S05", new StationPlatformManager("S05"));

        // Central (S01): platforms toward Museum and Airport.
        managers.get("S01").addPlatform(new Platform("P01", 3, "S02"));
        managers.get("S01").addPlatform(new Platform("P02", 3, "S05"));

        // Museum (S02): platforms toward Central, University, and City Hospital.
        managers.get("S02").addPlatform(new Platform("P03", 3, "S01"));
        managers.get("S02").addPlatform(new Platform("P04", 3, "S03"));
        managers.get("S02").addPlatform(new Platform("P05", 3, "S04"));

        // University (S03): platforms toward Museum and Airport.
        managers.get("S03").addPlatform(new Platform("P06", 3, "S02"));
        managers.get("S03").addPlatform(new Platform("P07", 3, "S05"));

        // City Hospital (S04): platforms toward Museum and Airport.
        managers.get("S04").addPlatform(new Platform("P08", 3, "S02"));
        managers.get("S04").addPlatform(new Platform("P09", 3, "S05"));

        // Airport (S05): platforms toward Central, University, and City Hospital.
        managers.get("S05").addPlatform(new Platform("P10", 3, "S01"));
        managers.get("S05").addPlatform(new Platform("P11", 3, "S03"));
        managers.get("S05").addPlatform(new Platform("P12", 3, "S04"));

        // Seed the simulation with initial passengers.
        managers.get("S01").getPlatforms().get(0)
                .admitPassenger(new Passenger("SIM-S01-01"));
        managers.get("S01").getPlatforms().get(0)
                .admitPassenger(new Passenger("SIM-S01-02"));

        managers.get("S01").getPlatforms().get(1)
                .admitPassenger(new Passenger("SIM-S01-03"));

        managers.get("S02").getPlatforms().get(1)
                .admitPassenger(new Passenger("SIM-S02-01"));
        managers.get("S02").getPlatforms().get(1)
                .admitPassenger(new Passenger("SIM-S02-02"));

        managers.get("S03").getPlatforms().get(0)
                .admitPassenger(new Passenger("SIM-S03-01"));

        managers.get("S04").getPlatforms().get(1)
                .admitPassenger(new Passenger("SIM-S04-01"));

        managers.get("S05").getPlatforms().get(0)
                .admitPassenger(new Passenger("SIM-S05-01"));

        return managers;
    }
}
