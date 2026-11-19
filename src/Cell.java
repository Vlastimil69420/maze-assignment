/**
 * Maze Assignment - Cell
 * One square of the maze grid. Each cell knows its own position,
 * which of its four walls are standing, and whether it has been
 * visited by the generator or the solver.
 * A wall is stored as a boolean: true means the wall is standing.
 * Every cell starts with all four walls up and not visited.
 *
 * @author Vlastimil Finger (B00176858)
 */

public class Cell
{
  private int row;
  private int col;
  private boolean topWall;
  private boolean rightWall;
  private boolean bottomWall;
  private boolean leftWall;
  private boolean visited;

  public Cell(int row, int col)
  {
    this.row = row;
    this.col = col;
    topWall = true;
    rightWall = true;
    bottomWall = true;
    leftWall = true;
    visited = false;
  }  // end constructor

  public int getRow()
  {
    return row;
  }

  public int getCol()
  {
    return col;
  }

  public boolean hasTopWall()
  {
    return topWall;
  }

  public boolean hasRightWall()
  {
    return rightWall;
  }

  public boolean hasBottomWall()
  {
    return bottomWall;
  }

  public boolean hasLeftWall()
  {
    return leftWall;
  }

  public void setTopWall(boolean standing)
  {
    topWall = standing;
  }

  public void setRightWall(boolean standing)
  {
    rightWall = standing;
  }

  public void setBottomWall(boolean standing)
  {
    bottomWall = standing;
  }

  public void setLeftWall(boolean standing)
  {
    leftWall = standing;
  }

  public boolean isVisited()
  {
    return visited;
  }

  public void setVisited(boolean visited)
  {
    this.visited = visited;
  }

  @Override
  public String toString()
  {
    return "(" + row + ", " + col + ")";
  }

}  // end Cell
