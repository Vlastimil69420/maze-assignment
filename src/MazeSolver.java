import java.util.ArrayList;

/**
 * Maze Assignment - MazeSolver
 * Solves the maze from the top-left (0, 0) to the bottom-right
 * (rows - 1, cols - 1) using Breadth-First Search with a
 * QueueReferenceBased. BFS explores the maze in rings of equal
 * distance from the start, so the first time the exit is reached
 * it has been reached by the shortest path.
 *
 * The solver only moves through knocked-down walls, because it gets
 * its next cells from grid.getOpenNeighbours().
 * parent[r][c] stores the cell that cell (r, c) was reached from.
 * When the exit is found, following the parents back from the exit
 * to the start gives the path.
 * inQueue[r][c] is true while cell (r, c) is waiting in the queue, so
 * the panel can show the edge of the search as it spreads out.
 *
 * @author Vlastimil Finger (B00176858)
 */

public class MazeSolver
{
  private Grid grid;
  private QueueReferenceBased queue;
  private Cell[][] parent;
  private Cell exit;
  private Cell current;
  private ArrayList<Cell> path;
  private boolean done;
  private boolean[][] inQueue;

  public MazeSolver(Grid grid)
  {
    this.grid = grid;
    queue = new QueueReferenceBased();
    parent = new Cell[grid.getRows()][grid.getCols()];
    path = new ArrayList<Cell>();
    done = false;
    inQueue = new boolean[grid.getRows()][grid.getCols()];
    grid.resetVisited();                     // the generator left every cell visited
    Cell start = grid.getCell(0, 0);
    exit = grid.getCell(grid.getRows() - 1, grid.getCols() - 1);
    start.setVisited(true);
    queue.enqueue(start);
    inQueue[0][0] = true;
  }  // end constructor

  public boolean isDone()
  {
    return done;
  }

  /**
   * Returns the cell being explored, or null once solving has finished.
   */

  public Cell getCurrent()
  {
    if (done)
    {
      return null;
    }
    return current;
  }

  /**
   * Returns the shortest path from start to exit. It is empty until
   * the exit has been found.
   */

  public ArrayList<Cell> getPath()
  {
    return path;
  }

  /**
   * True while c is waiting in the queue. Once solving has finished
   * the search has stopped, so nothing counts as waiting any more.
   */

  public boolean isInQueue(Cell c)
  {
    return !done && inQueue[c.getRow()][c.getCol()];
  }

  /**
   * Takes the next cell off the queue and adds its unvisited open
   * neighbours to the back of the queue. Cells are marked visited when
   * they are added, so no cell is ever in the queue twice.
   */

  public void step()
  {
    if (done)
    {
      return;
    }
    if (queue.isEmpty())
    {
      done = true;                           // no path (cannot happen in a perfect maze)
      return;
    }
    current = (Cell) queue.dequeue();
    inQueue[current.getRow()][current.getCol()] = false;
    if (current == exit)
    {
      buildPath();
      done = true;
      return;
    }
    for (Cell n : grid.getOpenNeighbours(current))
    {
      if (!n.isVisited())
      {
        n.setVisited(true);
        parent[n.getRow()][n.getCol()] = current;
        queue.enqueue(n);
        inQueue[n.getRow()][n.getCol()] = true;
      }
    }
  }

  /**
   * Runs step() until the exit is found.
   */

  public void solve()
  {
    while (!done)
    {
      step();
    }
  }

  /**
   * Walks the parent pointers from the exit back to the start. The
   * start has no parent, so the loop stops there. Each cell is added
   * at the front of the list, so the path ends up in start-to-exit order.
   */

  private void buildPath()
  {
    Cell c = exit;
    while (c != null)
    {
      path.add(0, c);
      c = parent[c.getRow()][c.getCol()];
    }
  }

}  // end MazeSolver
