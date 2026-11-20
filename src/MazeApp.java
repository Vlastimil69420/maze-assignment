import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Maze Assignment - MazeApp
 * Main window of the application. Generates a 10 x 10 maze, solves
 * it and shows the result in a MazePanel.
 *
 * @author Vlastimil Finger (B00176858)
 */

public class MazeApp extends JFrame
{
  private static final long serialVersionUID = 1L;

  private MazePanel panel;

  public MazeApp()
  {
    super("Maze - Vlastimil Finger (B00176858)");
    Grid grid = new Grid(10, 10);
    new MazeGenerator(grid).generate();
    MazeSolver solver = new MazeSolver(grid);
    solver.solve();
    panel = new MazePanel();
    panel.setGrid(grid);
    panel.setSolver(solver);
    add(panel);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);              // centre on the screen
  }  // end constructor

  public static void main(String[] args)
  {
    // Swing components must be created on the Swing event thread
    SwingUtilities.invokeLater(() -> new MazeApp().setVisible(true));
  }

}  // end MazeApp
