# Eco-Robot Autonomous Environment Simulator

A modular, object-oriented simulation engine written in Java that models an autonomous exploration robot navigating and analyzing procedural 2D environments. The system models multi-biome environmental conditions, dynamically tracks ecological factors (toxicity, air quality, humidity, soil composition), and processes programmatic agent missions.

---

## Key Features

- **Command-Driven Architecture:** Executes sequential robotic tasks with energy tracking, input validation, and real-time state reporting.
- **Dynamic Biome & Entity Simulation:** Simulates discrete geographical zones (Tropical, Polar, Desert, Tundra, Forest) populated by interactive fauna, flora, water sources, and soil profiles.
- **Ecological Metrics Tracking:** Dynamically calculates localized atmosphere and substrate conditions based on surrounding vegetation density and weather patterns.
- **Automated Test Harness:** Built-in JSON-driven validation suite verifying simulation invariants, path actions, and error handling.

---

## Architectural Design & Patterns

### 1. Command Pattern (`commands/`)
Robot instructions are encapsulated into self-contained command objects implementing a shared `Command` interface:
- `MoveBot`: Updates agent spatial coordinates and calculates directional movement costs.
- `ScanObj`: Inspects target adjacent entities and logs environmental telemetry.
- `Recharge`: Restores robot battery capacity based on local solar/energy sources.
- `ChangeWeather`: Simulates regional atmospheric shifts affecting local humidity and temperature.
- `GetEnergy` & `PrintEnvConditions`: Queries internal telemetry and territory snapshots.

### 2. Polymorphic Entity Hierarchy (`entities/`)
All world components derive from a root `Entity` class, structured into extensible sub-trees:
- **`Air`:** Models atmospheric regions (`Tropical`, `Polar`, `Desert`, `Mountain`, `Temperate`) with custom oxygen and particle densities.
- **`Soil`:** Models terrain ground types (`ForestSoil`, `DesertSoil`, `GrasslandSoil`, `SwampSoil`, `TundraSoil`).
- **`Plant`, `Animal`, `Water`:** Active surface entities that influence surrounding biome parameters during scans and robot actions.

### 3. Simulation Controller (`simulation/`)
- **`Manager` & `Simulation`:** Coordinates batch iterations, territory state maps, and command execution queues.
- **`Bot`:** Encapsulates the agent state, current grid position, and active battery budget.

---

## Project Structure

```text
Tema1-POO/
├── input/                  # Test input scenarios in JSON format
├── ref/                    # Reference ground-truth outputs for validation
├── pom.xml                 # Maven build, dependency, and plugin configurations
└── src/
    ├── main/java/
    │   ├── commands/       # Command pattern implementations
    │   ├── entities/       # Entity base classes & biome specializations
    │   │   ├── air/        # Biome-specific air implementations
    │   │   └── soil/       # Biome-specific soil implementations
    │   ├── fileio/         # JSON deserializers and configuration mappers
    │   ├── simulation/     # Grid manager, robot controller, and state loops
    │   └── main/Main.java  # Application execution entry point
    └── test/               # Checkstyle audits and automated unit/functional tests
