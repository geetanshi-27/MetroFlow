
package com.metroflow;

import java.util.LinkedHashMap;
import java.util.Map;

public class StationPlatformFactory {

    public static Map<String, StationPlatformManager> createAllStations() {
        Map<String, StationPlatformManager> managers = new LinkedHashMap<>();

        // 1. Create managers for all nine stations.
        managers.put("S01", new StationPlatformManager("S01")); // Central
        managers.put("S02", new StationPlatformManager("S02")); // Museum
        managers.put("S03", new StationPlatformManager("S03")); // University
        managers.put("S04", new StationPlatformManager("S04")); // City Hospital
        managers.put("S05", new StationPlatformManager("S05")); // Airport
        managers.put("S06", new StationPlatformManager("S06")); // Stadium
        managers.put("S07", new StationPlatformManager("S07")); // Tech Park
        managers.put("S08", new StationPlatformManager("S08")); // Market Square
        managers.put("S09", new StationPlatformManager("S09")); // Riverside

        // 2. Add platforms matching the current network connections.
        // Each platform has a capacity of 3 passengers.
        // Platform numbering starts at P01 within each station.

        // Central (S01)
        managers.get("S01").addPlatform(new Platform("P01", 3, "S02"));
        managers.get("S01").addPlatform(new Platform("P02", 3, "S06"));
        managers.get("S01").addPlatform(new Platform("P03", 3, "S07"));

        // Museum (S02)
        managers.get("S02").addPlatform(new Platform("P01", 3, "S01"));
        managers.get("S02").addPlatform(new Platform("P02", 3, "S03"));
        managers.get("S02").addPlatform(new Platform("P03", 3, "S04"));

        // University (S03)
        managers.get("S03").addPlatform(new Platform("P01", 3, "S02"));
        managers.get("S03").addPlatform(new Platform("P02", 3, "S05"));
        managers.get("S03").addPlatform(new Platform("P03", 3, "S07"));

        // City Hospital (S04)
        managers.get("S04").addPlatform(new Platform("P01", 3, "S02"));
        managers.get("S04").addPlatform(new Platform("P02", 3, "S05"));

        // Airport (S05)
        managers.get("S05").addPlatform(new Platform("P01", 3, "S03"));
        managers.get("S05").addPlatform(new Platform("P02", 3, "S04"));
        managers.get("S05").addPlatform(new Platform("P03", 3, "S09"));

        // Stadium (S06)
        managers.get("S06").addPlatform(new Platform("P01", 3, "S01"));
        managers.get("S06").addPlatform(new Platform("P02", 3, "S08"));

        // Tech Park (S07)
        managers.get("S07").addPlatform(new Platform("P01", 3, "S01"));
        managers.get("S07").addPlatform(new Platform("P02", 3, "S03"));

        // Market Square (S08)
        managers.get("S08").addPlatform(new Platform("P01", 3, "S06"));
        managers.get("S08").addPlatform(new Platform("P02", 3, "S09"));

        // Riverside (S09)
        managers.get("S09").addPlatform(new Platform("P01", 3, "S08"));
        managers.get("S09").addPlatform(new Platform("P02", 3, "S05"));

        // 3. Seed the simulation with initial passengers.

        // Central: two passengers toward Museum.
        managers.get("S01").getPlatforms().get(0)
                .admitPassenger(new Passenger("SIM-S01-01"));
        managers.get("S01").getPlatforms().get(0)
                .admitPassenger(new Passenger("SIM-S01-02"));

        // Central: one passenger toward Stadium.
        managers.get("S01").getPlatforms().get(1)
                .admitPassenger(new Passenger("SIM-S01-03"));

        // Museum: two passengers toward University.
        managers.get("S02").getPlatforms().get(1)
                .admitPassenger(new Passenger("SIM-S02-01"));
        managers.get("S02").getPlatforms().get(1)
                .admitPassenger(new Passenger("SIM-S02-02"));

        // University: one passenger toward Museum.
        managers.get("S03").getPlatforms().get(0)
                .admitPassenger(new Passenger("SIM-S03-01"));

        // City Hospital: one passenger toward Airport.
        managers.get("S04").getPlatforms().get(1)
                .admitPassenger(new Passenger("SIM-S04-01"));

        // Airport: one passenger toward University.
        managers.get("S05").getPlatforms().get(0)
                .admitPassenger(new Passenger("SIM-S05-01"));

        return managers;
    }
}
