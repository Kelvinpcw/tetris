import javax.swing.*;
import java.awt.event.*;

/**
 * Wires input events and the game timer to the model, and owns the application
 * entry point.
 *
 * <p>The controller depends on {@link GameAction}, <em>not</em> on
 * {@code GameModel} directly — a clean example of programming to an interface.
 *
 * <p>{@code EndGame} and {@code TetrisGame} have been merged here: both were
 * single-responsibility one-liners that didn't justify separate files.
 */
public class GameController {

    private final GameAction model;   // ← interface, not concrete class
    private final Board view;
    private final Timer timer;

    public GameController(GameAction model, Board view) {
        this.model = model;
        this.view  = view;

        timer = new Timer(500, e -> tick());

        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (model.isGameOver()) return;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT  -> model.moveLeft();
                    case KeyEvent.VK_RIGHT -> model.moveRight();
                    case KeyEvent.VK_DOWN  -> model.moveDown();
                    case KeyEvent.VK_UP    -> model.rotate();
                    case KeyEvent.VK_SPACE -> model.hardDrop();
                }
                checkGameOver();
                view.repaint();
            }
        });

        timer.start();
    }

    // ── timer callback ───────────────────────────────────────────────────────

    private void tick() {
        model.moveDown();
        checkGameOver();
        view.repaint();
    }

    private void checkGameOver() {
        if (model.isGameOver()) {
            timer.stop();
            // Absorbed from EndGame.java — one line didn't need its own class.
            JOptionPane.showMessageDialog(view, "Game Over!", "Tetris",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    // ── entry point (absorbed from TetrisGame.java) ──────────────────────────

    /**
     * Bootstraps the Swing frame and starts the game.
     * Extracted into its own method so it can be called on the EDT.
     */
    public static void launch() {
        GameModel model = new GameModel();
        Board     board = new Board(model);
        new GameController(model, board);   // controller wires everything up

        JFrame frame = new JFrame("Tetris");
        frame.add(board);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameController::launch);
    }
}
