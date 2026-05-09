import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController {
    private final GameModel model;
    private final Board view;
    private final Timer timer;

    public GameController(GameModel model, Board view) {
        this.model = model;
        this.view = view;

        timer = new Timer(500, e -> tick());

        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (model.isGameOver()) return;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> model.moveLeft();
                    case KeyEvent.VK_RIGHT -> model.moveRight();
                    case KeyEvent.VK_DOWN -> model.moveDown();
                    case KeyEvent.VK_UP -> model.rotate();
                    case KeyEvent.VK_SPACE -> model.hardDrop();
                }
                checkGameOver();
                view.repaint();
            }
        });

        timer.start();
    }

    private void tick() {
        model.moveDown();
        checkGameOver();
        view.repaint();
    }

    private void checkGameOver() {
        if (model.isGameOver()) {
            timer.stop();
            EndGame.handle(view);
        }
    }
}
