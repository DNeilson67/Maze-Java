# Pathfinding Navigator

A small Java Swing application that visualizes pathfinding algorithms on a grid/maze. You can interactively place start/goal cells, draw walls, generate mazes, import/export maze configurations, and run several pathfinding algorithms (Dijkstra, A*, BFS, DFS, Best-First Search) to see the explored nodes and final path.

## Features

- Interactive GUI built with Swing.
- Place start and goal nodes by clicking, toggle walls, and clear the board.
- Generate random maze using a recursive backtracking variant.
- Import and save maze layouts to/from text files.
- Visual implementations of: Dijkstra, A*, BFS, DFS, Best-First Search.
- Adjustable delay (visualization speed) and basic benchmark timer.

## Requirements

- Java 8+ (Swing is part of the standard JDK).
- A terminal (PowerShell is shown in the examples below) or an IDE (IntelliJ, Eclipse, NetBeans).

## How to compile and run (PowerShell)

From the project root (where `src` folder is located) you can compile and run with the following commands.

PowerShell commands:

```powershell
javac -d out src\*.java
java -cp out Main
```

Notes:
- The commands compile all `.java` files in `src` and put the class files into the `out` directory.
- If you open the project in an IDE, import the project as a plain Java project and run the `Main` class.

## Using the GUI

- The window title is "Pathfinding Navigator" and displays a 15x15 grid.
- Buttons / controls:
  - Radio buttons: choose the algorithm to run (Dijkstra default, AStar, BFS, DFS, BestFS).
  - Delay spinner: controls visualization delay in milliseconds.
  - Maze: generate a random maze.
  - Import: load a maze file (text format, see below).
  - Save: write the current walls/start/goal to a file.
  - Reset: clears the grid.
  - Start: runs the currently selected algorithm.
- Interaction with grid cells (click a cell):
  - Click an empty white cell: it becomes a wall (black).
  - Click a wall cell (black) when start and goal are empty: set start (green).
  - Click a green start cell when goal is empty: reset start.
  - Click a black cell when start is set but goal is not: set goal (cyan).
  - Clicking discovered (orange), neighbor (blue), or path (red) cells will clear discovered/path visualization.

## Import / Save file format

The app reads plain text files where each line encodes either a wall, the start, or the goal using simple delimiters. The coordinate layout used in code is `node[col][row]` and the file format uses two integers separated by a delimiter.

Lines supported by `ImportFile`:
- Walls: `col,row` (comma) — example: `2,5` means a wall at column 2 row 5.
- Start: `col//row` (double slash) — example: `3//7` sets the start at (3,7).
- Goal: `col##row` (double hash) — example: `10##12` sets the goal at (10,12).

Save format uses the same conventions except:
- Start is saved using `.` as a delimiter (e.g. `3.7`) and goal is saved using `/` (e.g. `10/12`). Walls are saved as `col,row`.

Notes and caveats:
- Input import is fairly strict; illegal expressions or repeated start/goal entries will cause an error dialog and the program exits.
- Coordinates are zero-based and must be within the grid size (15x15 by default).

## Project structure and important classes

- `src/Main.java` — Launches the GUI.
- `src/GUI.java` — Builds the Swing window, adds controls (algorithm selectors, start/reset, import/export), and wires up action listeners.
- `src/Panel.java` — The grid implementation. Contains the visualization logic, maze generation, and the pathfinding algorithm implementations (Dijkstra, A*, BFS, DFS, BestFS). Central static state: `node[][]`, `startPoint`, `endPoint`, `totalTimer`.
- `src/Node.java` — A single grid cell (extends `JButton`). Tracks state (start/goal/wall/visited/path) and visual appearance.
- `src/PQ.java` — A custom priority queue used by Dijkstra/A* (min-heap by `distance` field).
- `src/SaveFile.java` — Save dialogs and file writing.
- `src/ImportFile.java` — Reads a file line-by-line and applies walls/start/goal to the grid.
- `src/Stack.java`, `src/StackInt.java` — Simple stack implementation used by DFS.

## Algorithms implemented

- Dijkstra: implemented with `PQ` and the node `distance` field. Visualizes discovered nodes (orange) and final path (red).
- A*: uses a combination of rootDistance and Manhattan heuristics stored in `manhattanDistance`.
- BFS: breadth-first search using a queue and visited matrix.
- DFS: depth-first search using a stack.
- BestFS: greedy best-first search using manhattan distance as priority.

Enjoy exploring the algorithms visually!
