import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Maze Assignment - MazePanel
 * Custom drawing panel that renders the grid and its walls.
 * The cell size is worked out from the panel size every time it is
 * painted, so any maze dimension fits and resizing the window works.
 * Colours: light grey = not carved yet, white = carved passage,
 * light blue = explored by the solver, orange = the shortest path.
 *
 * @author Vlastimil Finger (B00176858)
 */

public class MazePanel extends JPanel
{
  private static final long serialVersionUID = 1L;
  private static final int MARGIN = 10;
  private static final Color UNVISITED = Color.LIGHT_GRAY;
  private static final Color OPEN = Color.WHITE;
  private static final Color EXPLORED = new Color(173, 216, 230);
  private static final Color PATH = Color.ORANGE;
  private static final Color WALL = Color.BLACK;

  private Grid grid;
  private MazeSolver solver;

  public MazePanel()
  {
    setPreferredSize(new Dimension(620, 620));
    setBackground(Color.WHITE);
  }  // end constructor

  public void setGrid(Grid grid)
  {
    this.grid = grid;
    repaint();
  }

  /**
   * Set to null while the maze is being generated. Once a solver is
   * set, a visited cell means explored by the solver.
   */

  public void setSolver(MazeSolver solver)
  {
    this.solver = solver;
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    if (grid == null)
    {
      return;
    }
    int rows = grid.getRows();
    int cols = grid.getCols();
    int size = Math.min((getWidth() - 2 * MARGIN) / cols, (getHeight() - 2 * MARGIN) / rows);
    size = Math.max(size, 1);
    // top-left corner of the maze, so the maze is centred in the panel
    int x0 = (getWidth() - size * cols) / 2;
    int y0 = (getHeight() - size * rows) / 2;

    // fill every cell first, then draw the walls on top, otherwise a
    // cell's fill would paint over the wall of the cell beside it
    for (int r = 0; r < rows; r++)
    {
      for (int c = 0; c < cols; c++)
      {
        g.setColor(colourOf(grid.getCell(r, c)));
        g.fillRect(x0 + c * size, y0 + r * size, size, size);
      }
    }
    if (solver != null)
    {
      g.setColor(PATH);
      for (Cell cell : solver.getPath())
      {
        g.fillRect(x0 + cell.getCol() * size, y0 + cell.getRow() * size, size, size);
      }
    }

    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(WALL);
    g2.setStroke(new BasicStroke(2));
    for (int r = 0; r < rows; r++)
    {
      for (int c = 0; c < cols; c++)
      {
        Cell cell = grid.getCell(r, c);
        int x = x0 + c * size;
        int y = y0 + r * size;
        if (cell.hasTopWall())
        {
          g2.drawLine(x, y, x + size, y);
        }
        if (cell.hasRightWall())
        {
          g2.drawLine(x + size, y, x + size, y + size);
        }
        if (cell.hasBottomWall())
        {
          g2.drawLine(x, y + size, x + size, y + size);
        }
        if (cell.hasLeftWall())
        {
          g2.drawLine(x, y, x, y + size);
        }
      }
    }
  }

  /**
   * Visited means carved while generating, but explored while solving,
   * because the solver resets the visited flags before it starts.
   */

  private Color colourOf(Cell cell)
  {
    if (solver != null)
    {
      return cell.isVisited() ? EXPLORED : OPEN;
    }
    return cell.isVisited() ? OPEN : UNVISITED;
  }

}  // end MazePanel