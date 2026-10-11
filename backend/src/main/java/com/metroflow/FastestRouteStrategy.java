
package com.metroflow;

import java.util.*;

public class FastestRouteStrategy implements RouteStrategy{

    @Override 
    public RouteResult findRoute(
            MetroNetwork network,
            String source,
            String destination) {

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();

        PriorityQueue<String> queue = new PriorityQueue<>(
                Comparator.comparingInt(distances::get)
        );

        for (String stationId : network.getStationIds()) {
            distances.put(stationId, Integer.MAX_VALUE);
        }

        if (!distances.containsKey(source)
                || !distances.containsKey(destination)) {
            return null;
        }

        distances.put(source, 0);
        queue.add(source);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(destination)) {
                break;
            }

            for (Connection connection :
                    network.getConnections(current)) {

                String neighbor = connection.getToStationId();

                int newDistance = distances.get(current)
                        + connection.getTravelTimeMinutes();

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, current);

                    queue.remove(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        if (distances.get(destination) == Integer.MAX_VALUE) {
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
                distances.get(destination)
        );
    }
}
