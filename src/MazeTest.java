import java.util.ArrayList;

/**
 * Maze Assignment - MazeTest
 * Console test of the generator and solver on different maze
 * dimensions, including the edge cases of a single cell, a single
 * row and a single column. No window is opened, so it also shows the
 * algorithms scale to 100 x 100 without crashing.
 *
 * @author Vlastimil Finger (B00176858)
 */

public class MazeTest
{
  public static void main(String[] args)
  {
    int[][] sizes = { {1, 1}, {1, 10}, {10, 1}, {2, 2}, {10, 10}, {30, 30}, {10, 30}, {100, 100} };
    boolean allPassed = true;
    for (int[] size : sizes)
    {
      if (!test(size[0], size[1]))
      {
        allPassed = false;
      }
    }
    System.out.println(allPassed ? "All tests passed." : "Some tests FAILED.");
  }

  /**
   * Generates and solves one maze, then checks that:
   *  - every cell was visited by the generator
   *  - the outer walls are all still standing
   *  - there are exactly (cells - 1) passages. Every cell is connected,
   *    and a connected grid with cells - 1 passages has no loops,
   *    so it is a perfect maze
   *  - the path starts at the start, ends at the exit and only
   *    moves between open neighbours, so it never crosses a wall
   */

  public static boolean test(int rows, int cols)
  {
    Grid grid = new Grid(rows, cols);
    new MazeGenerator(grid).generate();
    String problem = null;
    int passages = 0;
    for (int r = 0; r < rows; r++)
    {
      for (int c = 0; c < cols; c++)
      {
        Cell cell = grid.getCell(r, c);
        if (!cell.isVisited())
        {
          problem = "cell " + cell + " was not visited";
        }
        if ((r == 0 && !cell.hasTopWall()) || (r == rows - 1 && !cell.hasBottomWall())
            || (c == 0 && !cell.hasLeftWall()) || (c == cols - 1 && !cell.hasRightWall()))
        {
          problem = "outer wall missing at " + cell;
        }
        // count each passage once, from the cell on its left or above
        if (c < cols - 1 && !cell.hasRightWall())
        {
          passages++;
        }
        if (r < rows - 1 && !cell.hasBottomWall())
        {
          passages++;
        }
      }
    }
    if (passages != rows * cols - 1)
    {
      problem = passages + " passages, expected " + (rows * cols - 1);
    }

    MazeSolver solver = new MazeSolver(grid);
    solver.solve();
    ArrayList<Cell> path = solver.getPath();
    if (path.isEmpty() || path.get(0) != grid.getCell(0, 0)
        || path.get(path.size() - 1) != grid.getCell(rows - 1, cols - 1))
    {
      problem = "path does not go from start to exit";
    }
    else
    {
      for (int i = 1; i < path.size(); i++)
      {
        if (!grid.getOpenNeighbours(path.get(i - 1)).contains(path.get(i)))
        {
          problem = "path goes through a wall at " + path.get(i);
        }
      }
    }

    if (problem == null)
    {
      System.out.println(rows + " x " + cols + ": OK, path length " + path.size());
      return true;
    }
    System.out.println(rows + " x " + cols + ": FAILED, " + problem);
    return false;
  }

}  // end MazeTest
