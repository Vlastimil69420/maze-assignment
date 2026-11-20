import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Maze Assignment - MazeApp
 * Main window of the application. The bar at the top lets the user
 * enter the number of rows and columns (1 to 100), generate a maze
 * of that size and then solve it.
 * Both algorithms are animated with a Swing Timer: every tick runs a
 * few step()s and repaints, so the user can watch the maze being
 * carved and then the search spreading out to the exit.
 *
 * @author Vlastimil Finger (B00176858)
 */

public class MazeApp extends JFrame
{
  private static final long serialVersionUID = 1L;
  private static final int MAX_SIZE = 100;
  private static final int DELAY = 15;        // milliseconds between frames

  private JTextField rowsField;
  private JTextField colsField;
  private JButton generateButton;
  private JButton solveButton;
  private MazePanel panel;
  private Timer timer;
  private Grid grid;

  public MazeApp()
  {
    super("Maze - Vlastimil Finger (B00176858)");
    rowsField = new JTextField("10", 3);
    colsField = new JTextField("10", 3);
    generateButton = new JButton("Generate");
    solveButton = new JButton("Solve");
    solveButton.setEnabled(false);            // nothing to solve yet
    generateButton.addActionListener(e -> startGenerating());
    solveButton.addActionListener(e -> startSolving());

    JPanel controls = new JPanel();
    controls.add(new JLabel("Rows:"));
    controls.add(rowsField);
    controls.add(new JLabel("Cols:"));
    controls.add(colsField);
    controls.add(generateButton);
    controls.add(solveButton);

    panel = new MazePanel();
    add(controls, BorderLayout.NORTH);
    add(panel, BorderLayout.CENTER);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);              // centre on the screen
  }  // end constructor

  /**
   * Reads the size, creates a new grid and animates the generator.
   * Solve stays disabled until the maze is finished.
   */

  private void startGenerating()
  {
    int rows = readSize(rowsField, "Rows");
    if (rows == -1)
    {
      return;
    }
    int cols = readSize(colsField, "Cols");
    if (cols == -1)
    {
      return;
    }
    stopTimer();                              // Generate pressed while still animating
    grid = new Grid(rows, cols);
    MazeGenerator generator = new MazeGenerator(grid);
    panel.setGrid(grid);
    panel.setGenerator(generator);
    panel.setSolver(null);
    solveButton.setEnabled(false);

    int steps = stepsPerFrame();
    timer = new Timer(DELAY, e ->
    {
      for (int i = 0; i < steps && !generator.isDone(); i++)
      {
        generator.step();
      }
      panel.repaint();
      if (generator.isDone())
      {
        timer.stop();
        solveButton.setEnabled(true);
      }
    });
    timer.start();
  }

  /**
   * Animates the solver on the maze that was just generated.
   */

  private void startSolving()
  {
    stopTimer();
    MazeSolver solver = new MazeSolver(grid);
    panel.setSolver(solver);
    solveButton.setEnabled(false);

    int steps = stepsPerFrame();
    timer = new Timer(DELAY, e ->
    {
      for (int i = 0; i < steps && !solver.isDone(); i++)
      {
        solver.step();
      }
      panel.repaint();
      if (solver.isDone())
      {
        timer.stop();
        solveButton.setEnabled(true);
      }
    });
    timer.start();
  }

  /**
   * Bigger mazes run more steps per frame, so the animation takes about
   * the same time (a few seconds) for a 10 x 10 and a 100 x 100 maze.
   */

  private int stepsPerFrame()
  {
    return Math.max(1, grid.getRows() * grid.getCols() / 100);
  }

  /**
   * Returns the number in the field, or -1 after showing a message
   * if it is not a whole number from 1 to MAX_SIZE.
   */

  private int readSize(JTextField field, String name)
  {
    try
    {
      int size = Integer.parseInt(field.getText().trim());
      if (size >= 1 && size <= MAX_SIZE)
      {
        return size;
      }
    }
    catch (NumberFormatException e)
    {
      // not a number, falls through to the message below
    }
    JOptionPane.showMessageDialog(this, name + " must be a whole number from 1 to " + MAX_SIZE + ".");
    return -1;
  }

  private void stopTimer()
  {
    if (timer != null)
    {
      timer.stop();
    }
  }

  public static void main(String[] args)
  {
    // Swing components must be created on the Swing event thread
    SwingUtilities.invokeLater(() -> new MazeApp().setVisible(true));
  }

}  // end MazeApp
