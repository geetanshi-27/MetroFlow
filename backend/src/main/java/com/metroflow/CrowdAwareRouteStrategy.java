package com.metroflow;

import java.util.*;

public class CrowdAwareRouteStrategy implements RouteStrategy {

    @Override
    public RouteResult findRoute(
            MetroNetwork network,
            String source,
            String destination) {

               Map<String, Integer> costs = new HashMap<>();
        Map<String, Integer> travelTimes = new HashMap<>();
        Map<String, String> previous = new HashMap<>();

        PriorityQueue<String> queue = new PriorityQueue<>(
                Comparator.comparingInt(costs::get)
        );

        for (String stationId : network.getStationIds()) {
            costs.put(stationId, Integer.MAX_VALUE);
            travelTimes.put(stationId, Integer.MAX_VALUE);
        }

        if (!costs.containsKey(source)
                || !costs.containsKey(destination)) {
            return null;
        }

        costs.put(source, 0);
        travelTimes.put(source, 0);
        queue.add(source);

                while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(destination)) {
                break;
            }

            if (costs.get(current) == Integer.MAX_VALUE) {
                continue;
            }

            for (Connection connection :
                    network.getConnections(current)) {

                String neighbor = connection.getToStationId();

                int crowdPenalty;

                switch (connection.getCrowdLevel()) {
                    case 1:
                        crowdPenalty = 0;
                        break;
                    case 2:
                        crowdPenalty = 2;
                        break;
                    default:
                        crowdPenalty = 5;
                }

                int newCost = costs.get(current)
                        + connection.getTravelTimeMinutes()
                        + crowdPenalty;

                int newTravelTime = travelTimes.get(current)
                        + connection.getTravelTimeMinutes();

                if (newCost < costs.get(neighbor)) {
                    costs.put(neighbor, newCost);
                    travelTimes.put(neighbor, newTravelTime);
                    previous.put(neighbor, current);

                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        if (costs.get(destination) == Integer.MAX_VALUE) {
            return null;
        }

        List<String> path = new ArrayList<>();
        String current = destination;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);

        return new RouteResult(
                path,
                travelTimes.get(destination)
        );
    }
}
