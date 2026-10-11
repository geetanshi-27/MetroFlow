
import { useEffect, useState } from "react";

export default function App() {
  const [stations, setStations] = useState([]);
  const [source, setSource] = useState("");
  const [destination, setDestination] = useState("");
  const [strategy, setStrategy] = useState("FASTEST");
  const [error, setError] = useState("");
  const [route, setRoute] = useState(null);
  const [loading, setLoading] = useState(false);

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

  return (
    <div className="min-h-screen bg-slate-950 text-slate-100">
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
          <span className="rounded-full border border-teal-800 bg-teal-950 px-3 py-1 text-xs text-teal-300">
            NETWORK DEMO
          </span>
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
