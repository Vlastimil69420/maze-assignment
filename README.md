# Maze Generation and Solving

Vlastimil Finger (B00176858)

This program generates a random maze using Depth-First Search with a stack, solves it using
Breadth-First Search with a queue, and shows both step by step in a Java Swing window.

## How to compile and run

### Eclipse
1. Put all the `.java` files in the `src` folder of a Java project (no `module-info.java`).
2. Right-click `MazeApp.java` and choose Run As > Java Application.
3. Enter the number of rows and columns (1 to 100) and press Generate.
4. When the maze is finished, press Solve.

To run the tests, right-click `MazeTest.java` and choose Run As > Java Application.

### Command line
From the project folder:
```
cd src
javac *.java
java MazeApp
java MazeTest
```

## Colours

- Light grey: not carved yet
- White: carved passage
- Light green: cells on the generator's stack or in the solver's queue
- Dark green: the cell the generator or solver is working on
- Light blue: explored by the solver
- Orange: the shortest path from the start to the exit

## Classes

- `Cell` - one square of the maze, with its row, column, four walls and a visited flag
- `Grid` - 2D array of cells, finds neighbours and removes walls
- `MazeGenerator` - creates the maze using DFS and `StackReferenceBased`
- `MazeSolver` - finds the shortest path using BFS and `QueueReferenceBased`
- `MazePanel` - extends `JPanel` and draws the maze
- `MazeApp` - the main window with the Rows/Cols boxes and the Generate and Solve buttons
- `MazeTest` - tests different maze sizes in the console
- `StackReferenceBased`, `StackInterface`, `StackException`, `Node` - stack from Lab 3
- `QueueInterface`, `QueueException` - from Lab 4
- `QueueReferenceBased` - linked version of the Lab 4 queue

## Design choices

**Grid coordinates.** The cells are stored in a `Cell[rows][cols]` array and found by
`(row, col)`. Row 0 is the top and column 0 is the left, so the start `(0, 0)` is the top-left
and the exit `(rows - 1, cols - 1)` is the bottom-right.

**Walls and boundaries.** Each cell has four booleans for its walls (`true` means the wall is
there). When a wall between two cells is removed, it is removed in both cells. `getCell()`
returns `null` for a position outside the grid, so cells on the edge just have fewer
neighbours and the outer walls are never removed.

**Generating.** The start cell is pushed onto the stack. Each step looks at the cell on top of
the stack. If it has unvisited neighbours, one is picked at random, the wall between them is
removed and the neighbour is pushed. If not, the cell is popped to go back. When the stack is
empty every cell has been visited, and the maze has exactly one path between any two cells.

**Solving.** The start cell is put in the queue. Each step takes a cell from the front of the
queue and adds its unvisited neighbours that have no wall in between. Each cell remembers
which cell it came from (`parent`). BFS checks cells in order of distance, so the first path
that reaches the exit is the shortest. The path is found by following the parents back from
the exit to the start.

**Queue.** I used `QueueReferenceBased` instead of the Lab 4 `QueueArrayBased` because the
array version can only hold 50 items. The linked version uses the same `QueueInterface` and
`Node` class but has no limit, so it works for any maze size.

**Animation.** A Swing `Timer` calls the generator's or solver's `step()` method every few
milliseconds and repaints the panel, so you can watch it happen.

## Testing

In the window, try different sizes such as 10 x 10, 30 x 30, 10 x 30, 1 x 1 and 100 x 100.
Anything that is not a whole number from 1 to 100 shows an error message.

`MazeTest` generates and solves mazes of 1 x 1, 1 x 10, 10 x 1, 2 x 2, 10 x 10, 30 x 30,
10 x 30 and 100 x 100. For each one it checks that every cell was visited, the outer walls
are still there, the maze has no loops, and the path goes from the start to the exit without
going through a wall.
