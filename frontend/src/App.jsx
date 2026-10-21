
import { useEffect, useState } from "react";

export default function App() {
  const [stations, setStations] = useState([]);
  const [source, setSource] = useState("");
  const [destination, setDestination] = useState("");
  const [strategy, setStrategy] = useState("FASTEST");
  const [error, setError] = useState("");
  const [route, setRoute] = useState(null);
  const [loading, setLoading] = useState(false);
  const [passengerId, setPassengerId] = useState("");
  const [sessionError, setSessionError] = useState("");
  const [platforms, setPlatforms] = useState([]);
  const [admission, setAdmission] = useState(null);
  const [admissionLoading, setAdmissionLoading] = useState(false);
  const [passengerStatus, setPassengerStatus] = useState(null);
  const [departureLoading, setDepartureLoading] = useState(false);
  const [departureMessage, setDepartureMessage] = useState("");

  useEffect(() => {
    fetch("http://localhost:8080/api/stations")
      .then((response) => {
        if (!response.ok) throw new Error("Request failed");
        return response.json();
      })
      .then((data) => {
        setStations(data);
        setSource(data[0]?.id ?? "");
        setDestination(data[data.length - 1]?.id ?? "");
      })
      .catch(() => {
        setError("Cannot connect. Make sure Spring Boot is running.");
      });
  }, []);

  async function findRoute() {
    setLoading(true);
    setError("");
    setRoute(null);

    try {
      const response = await fetch("http://localhost:8080/api/routes", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          sourceId: source,
          destinationId: destination,
          strategy: strategy,
        }),
      });

      if (!response.ok) {
        throw new Error("Could not find a route.");
      }

      const data = await response.json();
      setRoute(data);
    } catch (err) {
      setError(err.message || "Something went wrong. Please try again.");
    } finally {
      setLoading(false);
    }
  }


  async function requestAdmission() {
    if (!passengerId || !destination) return;

    setAdmissionLoading(true);
    setAdmission(null);
    setError("");

    try {
      const response = await fetch(
        "http://localhost:8080/api/platforms/admission",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            stationId: source,
            passengerId,
            destinationStationId: destination,
          }),
        }
      );

      if (!response.ok) {
        throw new Error("Could not request platform admission.");
      }

      const data = await response.json();
      setAdmission(data);
      setPassengerStatus({
        passengerId,
        status: data.status,
        platformId: data.platformId,
        queuePosition: data.queuePosition,
        estimatedWaitingMinutes: 0,
        message: data.message,
      });
    } catch (err) {
      setError(err.message || "Platform admission failed.");
    } finally {
      setAdmissionLoading(false);
    }
  }


  async function simulateDeparture() {
    if (!admission?.platformId) return;

    setDepartureLoading(true);
    setDepartureMessage("");

    try {
      const response = await fetch(
        `http://localhost:8080/api/platforms/${admission.platformId}/departure`,
        { method: "POST" }
      );

      if (!response.ok) {
        throw new Error("Could not simulate departure.");
      }

      const message = await response.text();
      setDepartureMessage(message);
    } catch (err) {
      setDepartureMessage(err.message || "Departure simulation failed.");
    } finally {
      setDepartureLoading(false);
    }
  }




  useEffect(() => {
    async function createPassengerSession() {
      try {
        const response = await fetch("http://localhost:8080/api/sessions", {
          method: "POST",
        });

        if (!response.ok) {
          throw new Error("Could not create passenger session.");
        }

        const data = await response.json();
        setPassengerId(data.passengerId);
      } catch {
        setSessionError(
          "Could not create your passenger ID. Check the backend connection."
        );
      }
    }

    createPassengerSession();
  }, []);



  useEffect(() => {
    async function fetchPlatforms() {
      try {
        const response = await fetch("http://localhost:8080/api/platforms");

        if (!response.ok) {
          throw new Error("Could not load platforms.");
        }

        const data = await response.json();
        setPlatforms(data);
      } catch (err) {
        console.error("Platform loading failed:", err);
      }
    }

    async function fetchPassengerStatus() {
      if (!passengerId || !admission) return;

      try {
        const response = await fetch(
          `http://localhost:8080/api/platforms/status/${passengerId}`
        );

        if (!response.ok) {
          throw new Error("Could not load passenger status.");
        }

        const data = await response.json();
        setPassengerStatus(data);
      } catch (err) {
        console.error("Passenger status loading failed:", err);
      }
    }

    fetchPlatforms();
    fetchPassengerStatus();

    const intervalId = setInterval(() => {
      fetchPlatforms();
      fetchPassengerStatus();
    }, 2000);

    return () => clearInterval(intervalId);
  }, [passengerId, admission]);




  return (
    <div className="min-h-screen bg-white-950 text-slate-100">
      <header className="border-b border-slate-800">
        <div className="mx-auto flex max-w-6xl items-center justify-between px-6 py-5">
          <div>
            <h1 className="text-2xl font-bold tracking-tight">
              <span className="text-teal-400">Metro</span>Flow
            </h1>
            <p className="mt-1 text-sm text-slate-400">
              Smarter journeys. Better connections.
            </p>
          </div>
          <div className="text-right">
            <span className="rounded-full border border-teal-800 bg-teal-950 px-3 py-1 text-xs text-teal-300">
              NETWORK DEMO
            </span>

            <p className="mt-2 text-xs text-slate-400">
              Passenger ID:{" "}
              <span className="font-semibold text-teal-300">
                {passengerId || "Generating..."}
              </span>
            </p>

            {sessionError && (
              <p className="mt-2 text-xs text-rose-400">{sessionError}</p>
            )}
          </div>
        </div>
      </header>

      <main className="mx-auto max-w-6xl px-6 py-10">
        <div className="mb-8">
          <p className="text-sm font-semibold uppercase tracking-widest text-teal-400">
            Journey planner
          </p>
          <h2 className="mt-3 text-3xl font-bold sm:text-4xl">
            Where are you headed?
          </h2>
          <p className="mt-3 text-slate-400">
            Find routes through a smarter metro network.
          </p>
        </div>

        <div className="grid gap-6 lg:grid-cols-[1.2fr_0.8fr]">
          <section className="rounded-2xl border border-slate-800 bg-slate-900 p-6">
            <h3 className="text-lg font-semibold">Plan your journey</h3>

            <div className="mt-6 grid gap-5 sm:grid-cols-2">
              <label className="text-sm text-slate-300">
                From
                <select
                  value={source}
                  onChange={(e) => setSource(e.target.value)}
                  className="mt-2 w-full rounded-xl border border-slate-700 bg-slate-950 p-3 text-white focus:border-teal-400 focus:outline-none"
                >
                  {stations.map((station) => (
                    <option key={station.id} value={station.id}>
                      {station.name}
                    </option>
                  ))}
                </select>
              </label>

              <label className="text-sm text-slate-300">
                To
                <select
                  value={destination}
                  onChange={(e) => setDestination(e.target.value)}
                  className="mt-2 w-full rounded-xl border border-slate-700 bg-slate-950 p-3 text-white focus:border-teal-400 focus:outline-none"
                >
                  {stations.map((station) => (
                    <option key={station.id} value={station.id}>
                      {station.name}
                    </option>
                  ))}
                </select>
              </label>
            </div>

            <div className="mt-6 grid gap-3 sm:grid-cols-2">
              <button
                onClick={() => setStrategy("FASTEST")}
                className={`rounded-xl border p-4 text-left ${strategy === "FASTEST"
                  ? "border-teal-400 bg-teal-950"
                  : "border-slate-700 bg-slate-950 hover:border-slate-500"
                  }`}
              >
                <span className="block font-semibold">⚡ Fastest route</span>
                <span className="mt-1 block text-sm text-slate-400">
                  Prioritize travel time
                </span>
              </button>

              <button
                onClick={() => setStrategy("crowd-aware")}
                className={`rounded-xl border p-4 text-left ${strategy === "crowd-aware"
                  ? "border-teal-400 bg-teal-950"
                  : "border-slate-700 bg-slate-950 hover:border-slate-500"
                  }`}
              >
                <span className="block font-semibold">◎ Crowd-aware</span>
                <span className="mt-1 block text-sm text-slate-400">
                  Account for congestion
                </span>
              </button>
            </div>


            <button
              disabled={
                !source ||
                !destination ||
                source === destination ||
                loading
              }
              onClick={findRoute}
              className="mt-6 w-full rounded-xl bg-teal-400 px-5 py-3 font-bold text-slate-950 hover:bg-teal-300 disabled:cursor-not-allowed disabled:opacity-40"
            >
              {loading ? "Finding route..." : "Find my route"}
            </button>


            {error && (
              <p className="mt-4 text-sm text-rose-400">{error}</p>
            )}


            {route && (
              <div className="mt-6 rounded-xl border border-teal-800 bg-slate-950 p-5">
                <h4 className="font-semibold text-teal-300">
                  Your route
                </h4>

                <p className="mt-3 text-2xl font-bold">
                  {route.totalTravelTimeMinutes} minutes
                </p>

                <p className="mt-1 text-sm text-slate-400">
                  Estimated travel time
                </p>

                <ol className="mt-4 space-y-2">
                  {route.stationIds.map((stationId, index) => {
                    const station = stations.find(
                      (item) => item.id === stationId
                    );

                    return (
                      <li key={stationId} className="flex items-center gap-3">
                        <span className="flex h-7 w-7 items-center justify-center rounded-full bg-teal-950 text-xs text-teal-300">
                          {index + 1}
                        </span>

                        <span>
                          {station ? station.name : stationId}
                        </span>
                      </li>
                    );
                  })}
                </ol>
              </div>
            )}


            <div className="mt-6 border-t border-slate-800 pt-6">
              <h3 className="text-lg font-semibold">Platform access</h3>

              <p className="mt-2 text-sm text-slate-400">
                Request entry to a platform serving your selected destination.
              </p>

              <button
                onClick={requestAdmission}
                disabled={!passengerId || !destination || admissionLoading}
                className="mt-4 w-full rounded-xl bg-teal-400 px-5 py-3 font-bold text-slate-950 hover:bg-teal-300 disabled:cursor-not-allowed disabled:opacity-40"
              >
                {admissionLoading ? "Requesting admission..." : "Request platform admission"}
              </button>


              {admission && (
                <div className="mt-4 rounded-xl border border-slate-700 bg-slate-950 p-4">
                  <p className="font-semibold text-teal-300">
                    {passengerStatus?.status || admission.status}
                  </p>

                  <p className="mt-2 text-sm text-slate-300">
                    {passengerStatus?.message || admission.message}
                  </p>

                  {(passengerStatus?.platformId || admission.platformId) && (
                    <p className="mt-2 text-sm text-slate-400">
                      Platform: {passengerStatus?.platformId || admission.platformId}
                    </p>
                  )}

                  {passengerStatus?.status === "WAITING" && (
                    <div className="mt-2 text-sm text-slate-400">
                      <p>Queue position: {passengerStatus.queuePosition}</p>
                      <p>
                        Estimated wait: {passengerStatus.estimatedWaitingMinutes} minutes
                      </p>
                    </div>
                  )}

                  <button
                    onClick={simulateDeparture}
                    disabled={!(passengerStatus?.platformId || admission.platformId) || departureLoading}
                    className="mt-4 w-full rounded-xl border border-teal-700 px-4 py-3 font-semibold text-teal-300 hover:bg-teal-950 disabled:cursor-not-allowed disabled:opacity-40"
                  >
                    {departureLoading
                      ? "Simulating departure..."
                      : "Simulate train departure"}
                  </button>

                  {departureMessage && (
                    <p className="mt-3 text-sm text-slate-400">
                      {departureMessage}
                    </p>
                  )}
                </div>
              )}

            </div>


          </section>

          <aside className="rounded-2xl border border-slate-800 bg-slate-900 p-6">
            <h3 className="text-lg font-semibold">Network overview</h3>
            <p className="mt-2 text-sm text-slate-400">
              Live connection to your local backend.
            </p>

            <div className="mt-6 grid grid-cols-2 gap-3">
              <div className="rounded-xl bg-slate-950 p-4">
                <p className="text-sm text-slate-400">Stations</p>
                <p className="mt-2 text-3xl font-bold">{stations.length}</p>
              </div>
              <div className="rounded-xl bg-slate-950 p-4">
                <p className="text-sm text-slate-400">Route modes</p>
                <p className="mt-2 text-3xl font-bold">02</p>
              </div>
            </div>


            <h4 className="mt-6 font-medium">Platform status</h4>

            <div className="mt-3 space-y-3">
              {platforms
                .filter((platform) => platform.stationId === source)
                .map((platform) => {
                  const occupancyPercentage = Math.round(
                    (platform.currentPassengers / platform.maxCapacity) * 100
                  );

                  return (
                    <div
                      key={platform.platformId}
                      className="rounded-xl border border-slate-800 bg-slate-950 p-4"
                    >
                      <div className="flex items-center justify-between">
                        <span className="font-semibold">
                          Platform {platform.platformId}
                        </span>

                        <span className="text-xs text-slate-400">
                          Toward {platform.directionStationId}
                        </span>
                      </div>

                      <div className="mt-3 flex items-center justify-between text-sm">
                        <span className="text-slate-400">Occupancy</span>

                        <span>
                          {platform.currentPassengers}/{platform.maxCapacity}
                        </span>
                      </div>

                      <div className="mt-2 h-2 overflow-hidden rounded-full bg-slate-800">
                        <div
                          className="h-full rounded-full bg-teal-400 transition-all"
                          style={{ width: `${occupancyPercentage}%` }}
                        />
                      </div>

                      <div className="mt-3 flex justify-between text-sm">
                        <span className="text-slate-400">Waiting queue</span>
                        <span>{platform.waitingCount}</span>
                      </div>
                    </div>
                  );
                })}

              {platforms.length === 0 && (
                <p className="text-sm text-slate-500">
                  No platform data available.
                </p>
              )}
            </div>


            <h4 className="mt-6 font-medium">Available stations</h4>
            <ul className="mt-3 space-y-2">
              {stations.map((station) => (
                <li
                  key={station.id}
                  className="flex items-center gap-3 rounded-lg bg-slate-950 p-3"
                >
                  <span className="h-2 w-2 rounded-full bg-teal-400" />
                  <span className="text-sm text-slate-300">
                    {station.name}
                  </span>
                  <span className="ml-auto text-xs text-slate-500">
                    {station.id}
                  </span>
                </li>
              ))}
            </ul>
          </aside>
        </div>

        <footer className="mt-8 text-center text-xs text-slate-500">
          MetroFlow · Academic network simulation
        </footer>
      </main>
    </div>
  );
}
