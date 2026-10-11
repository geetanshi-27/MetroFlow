
package com.metroflow;

public interface RouteStrategy {

    RouteResult findRoute(
            MetroNetwork network,
            String source,
            String destination
    );
}
