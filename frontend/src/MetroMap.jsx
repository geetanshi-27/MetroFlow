
const stations = [
  { id: "S01", name: "Central", x: 100, y: 190 },
  { id: "S02", name: "Museum", x: 260, y: 110 },
  { id: "S03", name: "University", x: 430, y: 90 },
  { id: "S04", name: "City Hospital", x: 430, y: 260 },
  { id: "S05", name: "Airport", x: 620, y: 190 },
  { id: "S06", name: "Stadium", x: 100, y: 340 },
  { id: "S07", name: "Tech Park", x: 260, y: 340 },
  { id: "S08", name: "Market Square", x: 300, y: 430 },
  { id: "S09", name: "Riverside", x: 500, y: 430 },
];

const connections = [
  ["S01", "S02"],
  ["S02", "S03"],
  ["S03", "S05"],
  ["S02", "S04"],
  ["S04", "S05"],
  ["S01", "S06"],
  ["S06", "S08"],
  ["S08", "S09"],
  ["S01", "S07"],
];

export default function MetroMap() {
  const stationById = Object.fromEntries(
    stations.map((station) => [station.id, station])
  );

  return (
    <div className="w-full overflow-x-auto rounded-xl bg-slate-950 p-2">
      <svg
        viewBox="0 0 720 500"
        className="w-full min-w-[540px]"
        role="img"
        aria-label="Metro network map showing nine stations and their connections"
      >
        {/* Draw connections first, behind the stations. */}
        {connections.map(([fromId, toId]) => {
          const from = stationById[fromId];
          const to = stationById[toId];

          return (
            <line
              key={`${fromId}-${toId}`}
              x1={from.x}
              y1={from.y}
              x2={to.x}
              y2={to.y}
              stroke="#475569"
              strokeWidth="3"
              strokeLinecap="round"
            />
          );
        })}

        {/* Draw station markers and labels. */}
        {stations.map((station) => (
          <g key={station.id}>
            <circle
              cx={station.x}
              cy={station.y}
              r="8"
              fill="#0f766e"
              stroke="#5eead4"
              strokeWidth="3"
            />

            <text
              x={station.x}
              y={station.y - 19}
              textAnchor="middle"
              fill="#e2e8f0"
              fontSize="13"
              fontWeight="600"
            >
              {station.name}
            </text>

            <text
              x={station.x}
              y={station.y + 25}
              textAnchor="middle"
              fill="#94a3b8"
              fontSize="11"
            >
              {station.id}
            </text>
          </g>
        ))}
      </svg>

      <p className="px-3 pb-2 text-xs text-slate-400">
        Each line represents a direct connection. The diagram is schematic,
        not geographically scaled.
      </p>
    </div>
  );
}
