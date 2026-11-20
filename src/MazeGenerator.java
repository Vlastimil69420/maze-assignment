import java.util.ArrayList;
import java.util.Random;

/**
 * Maze Assignment - MazeGenerator
 * Generates a perfect maze using Randomized Depth-First Search with
 * backtracking (the recursive backtracker). Instead of recursion,
 * the Lab3 StackReferenceBased holds the path of cells, so the top of
 * the stack is always the cell currently being carved from.
 *
 * Each step():
 *   - if the current cell has unvisited neighbours, pick one at random,
 *     knock down the wall between them, mark it visited and push it
 *   - otherwise it is a dead end, so pop to backtrack
 * When the stack is empty every cell has been visited. Every wall
 * that is knocked down leads to a new, unvisited cell, so no loops
 * can form and there is exactly one path between any two cells.
 *
 * @author Vlastimil Finger (B00176858)
 */

public class MazeGenerator
{
  private Grid grid;
  private StackReferenceBased stack;
  private Random random;

  public MazeGenerator(Grid grid)
  {
    this.grid = grid;
    stack = new StackReferenceBased();
    random = new Random();
    Cell start = grid.getCell(0, 0);
    start.setVisited(true);
    stack.push(start);
  }  // end constructor

  public boolean isDone()
  {
    return stack.isEmpty();
  }

  /**
   * Returns the cell on top of the stack, or null once generation
   * has finished.
   */

  public Cell getCurrent()
  {
    if (isDone())
    {
      return null;
    }
    return (Cell) stack.peek();
  }

  /**
   * Carves one passage or backtracks one cell.
   */

  public void step()
  {
    if (isDone())
    {
      return;
    }
    Cell current = (Cell) stack.peek();
    ArrayList<Cell> unvisited = new ArrayList<Cell>();
    for (Cell n : grid.getNeighbours(current))
    {
      if (!n.isVisited())
      {
        unvisited.add(n);
      }
    }
    if (unvisited.isEmpty())
    {
      stack.pop();                     // dead end, go back one cell
    }
    else
    {
      Cell next = unvisited.get(random.nextInt(unvisited.size()));
      grid.removeWallBetween(current, next);
      next.setVisited(true);
      stack.push(next);
    }
  }

  /**
   * Runs step() until the whole maze is generated.
   */

  public void generate()
  {
    while (!isDone())
    {
      step();
    }
  }

}  // end MazeGenerator
