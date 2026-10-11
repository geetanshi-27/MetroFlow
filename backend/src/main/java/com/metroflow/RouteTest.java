
package com.metroflow;

public class RouteTest {

    public static void main(String[] args) {

        MetroNetwork network =
                MetroNetworkFactory.createSampleNetwork();

        FastestRouteStrategy strategy =
                new FastestRouteStrategy();

        RouteResult result = strategy.findRoute(
                network, "S01", "S05"
        );

        if (result == null) {
            System.out.println("No route found.");
        } else {
            System.out.println("Route: " + result.getStationIds());
            System.out.println("Travel time: "
                    + result.getTotalTravelTimeMinutes()
                    + " minutes");
        }

                System.out.println("\n--- Crowd-Aware Route ---");

        CrowdAwareRouteStrategy crowdStrategy =
                new CrowdAwareRouteStrategy();

        RouteResult crowdResult = crowdStrategy.findRoute(
                network, "S01", "S05"
        );

        if (crowdResult == null) {
            System.out.println("No route found.");
        } else {
            System.out.println(
                    "Route: " + crowdResult.getStationIds()
            );
            System.out.println(
                    "Travel time: "
                    + crowdResult.getTotalTravelTimeMinutes()
                    + " minutes"
            );
        }
    }
}
