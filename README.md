# MetroFlow
# MetroFlow 🚇

**Smarter journeys. Better connections.**

MetroFlow is a metro network simulation and journey-planning application designed to demonstrate route optimization, crowd-aware navigation, and platform passenger management using object-oriented programming.

The project aims to help passengers find suitable routes through a metro network while simulating crowd conditions and platform capacity constraints.

## 🎯 Project Objectives

- Find the fastest route between two metro stations using Dijkstra's algorithm.
- Compare fastest-route and crowd-aware route recommendations.
- Simulate station crowding and platform passenger capacity.
- Demonstrate object-oriented programming concepts through meaningful application behaviour.
- Provide a usable web interface connected to a Java backend.

## 🛠️ Technology Stack

- **Backend:** Java, Spring Boot, Maven
- **Frontend:** React, JavaScript, Vite
- **Styling:** Tailwind CSS
- **API communication:** REST endpoints using JSON

## ✅ Current Implementation Status

### MVP 1 — Metro Network and Route Planner

**Status: Core routing implemented**

- A sample metro network with five stations and bidirectional connections.
- Configured travel times between connected stations.
- Dijkstra's algorithm to find the minimum-travel-time route.
- Route results containing the ordered station IDs and estimated travel time.
- A REST API that accepts source and destination stations.
- A React interface with station selectors and route results.

### MVP 2 — Crowd-Aware Routing

**Status: Initial routing logic implemented**

- Connections have simulated crowd levels from 1 to 3.
- A crowd-aware routing strategy assigns penalties to crowded connections.
- Two routing strategies can be selected from the frontend:
  - **Fastest route:** Minimizes travel time.
  - **Crowd-aware route:** Minimizes travel time combined with crowd penalties.
- The backend returns the estimated travel time of the selected route.

**Current limitation:** Crowd levels are assigned to connections, not yet calculated as station occupancy percentages. The four crowd classifications (LOW, MODERATE, HIGH, and CRITICAL) and a dedicated crowd information service remain to be implemented.

### MVP 3 — Platform Capacity Control

**Status: Planned**

- Platform maximum capacity and current occupancy.
- Passenger admission subject to available capacity.
- FIFO queues for passengers waiting to enter.
- Passenger release and automatic admission of waiting passengers when space becomes available.

### MVP 4 — Connected Application

**Status: In progress**

The existing interface supports station selection, route search, route mode selection, and route results.

Planned additions include:

- Station crowd information and crowd levels in route results.
- Platform occupancy and queue status.
- Controls to simulate passenger admission and release.
- Integration of the platform simulation with the frontend.

## 🗺️ Sample Metro Network

The current network contains five stations:

| Station ID | Station Name |
|---|---|
| S01 | Central |
| S02 | Museum |
| S03 | University |
| S04 | City Hospital |
| S05 | Airport |

Example connections:

| Connection | Travel Time | Crowd Level |
|---|---:|---:|
| Central – Museum | 4 minutes | 2 |
| Museum – University | 6 minutes | 1 |
| Museum – City Hospital | 3 minutes | 3 |
| University – Airport | 5 minutes | 1 |
| City Hospital – Airport | 8 minutes | 3 |

The sample network is configured for testing and does not represent live metro data.

## 🔌 Current API Endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/stations` | Returns the list of stations. |
| GET | `/api/network` | Returns the network connections and their properties. |
| POST | `/api/routes` | Finds a route using the requested strategy. |

### Example Route Request

`POST /api/routes`

```json
{
  "sourceId": "S01",
  "destinationId": "S05",
  "strategy": "crowd-aware"
}
```

For the fastest route, use `"strategy": "fastest"`.

If the strategy is omitted, the backend defaults to the fastest route.

### Example Route Response

```json
{
  "stationIds": ["S01", "S02", "S03", "S05"],
  "totalTravelTimeMinutes": 15
}
```

The response above illustrates the crowd-aware route through University. The fastest-route strategy may select a different path with the same travel time.

**Planned API endpoints:** Platform status, passenger admission, and passenger release endpoints will be added as the platform simulation is developed.

## 🚀 Running the Project Locally

### Prerequisites

- Java Development Kit (JDK) compatible with the Spring Boot project.
- Node.js and npm.
- Git.

### 1. Start the Backend

Open a terminal in the backend directory containing `pom.xml` and run:

```bash
./mvnw spring-boot:run
```

The backend should start at:

`http://localhost:8080`

On Windows, use `mvnw.cmd spring-boot:run` instead.

### 2. Start the Frontend

Open a second terminal in the React frontend directory containing `package.json` and run:

```bash
npm install
npm run dev
```

Open the local URL printed by Vite, typically:

`http://localhost:5173`

Keep both development servers running while using the application.

## 🧪 Testing the Current Features

1. Start the backend and frontend.
2. Select Central as the source and Airport as the destination.
3. Choose **Fastest route** and click **Find my route**.
4. Choose **Crowd-aware** and search again.
5. Compare the station sequences and travel times.

The current sample network allows both strategies to return routes taking 15 minutes, while the crowd-aware strategy favours the less crowded University branch.

The backend can also be compiled with:

```bash
./mvnw compile
```

## 🧱 Planned Architecture

The project is being developed using object-oriented design and separation of responsibilities.

| Component | Responsibility |
|---|---|
| `Connection` | Represents a connection between stations, including travel time and crowd level. |
| `MetroNetwork` | Stores stations and connections and provides network lookups. |
| `MetroNetworkFactory` | Creates the sample network. |
| `RouteResult` | Represents the selected station sequence and total travel time. |
| `RouteStrategy` | Defines the common route-finding interface. |
| `FastestRouteStrategy` | Finds a minimum-travel-time route. |
| `CrowdAwareRouteStrategy` | Finds a route using travel time and crowd penalties. |
| `StationController` | Exposes the station, network, and route REST endpoints. |

Future components may include `Station`, `RouteService`, `CrowdService`, `Platform`, and `PassengerQueue`.

## 🤝 Team Development

The project is being developed incrementally, with the routing backend forming the foundation for the remaining features.

Potential areas for parallel development include:

- **Frontend:** Improve the interface, route visualisation, crowd indicators, and platform status panels.
- **Platform simulation:** Implement platform capacity, passenger admission, and FIFO queue behaviour.
- **Crowd management:** Introduce station occupancy percentages, crowd classifications, and crowd information services.
- **Integration and testing:** Connect the new features to the REST API and verify end-to-end behaviour.

Coordinate changes to API request and response formats before integrating separate components.

## 📌 Scope and Limitations

MetroFlow currently uses a simulated metro network and predefined travel times and crowd levels. It does not use live metro APIs, machine learning, or real passenger data.

Advanced features such as train movement simulation, dynamic gate allocation, historical analytics, and coach-level occupancy are outside the initial project scope.

## 📄 Project Status

MetroFlow is an academic project under active development. The initial route-planning and crowd-aware routing features are working, while platform simulation and full crowd management remain planned extensions.