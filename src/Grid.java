import java.util.ArrayList;

/**
 * Maze Assignment - Grid
 * A rows x cols grid of Cell objects stored in a 2D array.
 * Coordinates are (row, col): row 0 is the top, col 0 is the left,
 * so the start (0, 0) is the top-left and the exit
 * (rows - 1, cols - 1) is the bottom-right.
 * getCell() returns null for a position outside the grid, which is
 * how the edges are handled: a cell on the edge simply has fewer
 * neighbours, and its outer walls are never removed.
 *
 * @author Vlastimil Finger (B00176858)
 */

public class Grid
{
  private int rows;
  private int cols;
  private Cell[][] cells;

  public Grid(int rows, int cols)
  {
    this.rows = rows;
    this.cols = cols;
    cells = new Cell[rows][cols];
    for (int r = 0; r < rows; r++)
    {
      for (int c = 0; c < cols; c++)
      {
        cells[r][c] = new Cell(r, c);
      }
    }
  }  // end constructor

  public int getRows()
  {
    return rows;
  }

  public int getCols()
  {
    return cols;
  }

  /**
   * Returns the cell at (row, col), or null if it is outside the grid.
   */

  public Cell getCell(int row, int col)
  {
    if (row < 0 || row >= rows || col < 0 || col >= cols)
    {
      return null;
    }
    return cells[row][col];
  }

  /**
   * Returns the cells directly above, right, below and left of c
   * that are inside the grid, whether or not a wall is in the way.
   */

  public ArrayList<Cell> getNeighbours(Cell c)
  {
    ArrayList<Cell> neighbours = new ArrayList<Cell>();
    addIfInside(neighbours, c.getRow() - 1, c.getCol());   // top
    addIfInside(neighbours, c.getRow(), c.getCol() + 1);   // right
    addIfInside(neighbours, c.getRow() + 1, c.getCol());   // bottom
    addIfInside(neighbours, c.getRow(), c.getCol() - 1);   // left
    return neighbours;
  }

  /**
   * Returns only the neighbours of c that can be reached from c,
   * meaning the wall between them has been knocked down.
   */

  public ArrayList<Cell> getOpenNeighbours(Cell c)
  {
    ArrayList<Cell> open = new ArrayList<Cell>();
    if (!c.hasTopWall())
    {
      addIfInside(open, c.getRow() - 1, c.getCol());
    }
    if (!c.hasRightWall())
    {
      addIfInside(open, c.getRow(), c.getCol() + 1);
    }
    if (!c.hasBottomWall())
    {
      addIfInside(open, c.getRow() + 1, c.getCol());
    }
    if (!c.hasLeftWall())
    {
      addIfInside(open, c.getRow(), c.getCol() - 1);
    }
    return open;
  }

  /**
   * Knocks down the wall between two neighbouring cells. Both cells
   * store the shared wall, so both sides are removed, otherwise the
   * passage would only be open in one direction.
   */

  public void removeWallBetween(Cell a, Cell b)
  {
    if (b.getRow() == a.getRow() - 1)          // b is above a
    {
      a.setTopWall(false);
      b.setBottomWall(false);
    }
    else if (b.getRow() == a.getRow() + 1)     // b is below a
    {
      a.setBottomWall(false);
      b.setTopWall(false);
    }
    else if (b.getCol() == a.getCol() + 1)     // b is right of a
    {
      a.setRightWall(false);
      b.setLeftWall(false);
    }
    else if (b.getCol() == a.getCol() - 1)     // b is left of a
    {
      a.setLeftWall(false);
      b.setRightWall(false);
    }
  }

  /**
   * Marks every cell as not visited. The generator leaves every cell
   * visited, so this is called before the solver starts.
   */

  public void resetVisited()
  {
    for (int r = 0; r < rows; r++)
    {
      for (int c = 0; c < cols; c++)
      {
        cells[r][c].setVisited(false);
      }
    }
  }

  private void addIfInside(ArrayList<Cell> list, int row, int col)
  {
    Cell c = getCell(row, col);
    if (c != null)
    {
      list.add(c);
    }
  }

}  // end Grid
