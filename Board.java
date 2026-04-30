import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel {
    private final int TILE_SIZE = 30;
    private final int ROWS = 20;
    private final int COLS = 10;
    private Color[][] boardGrid = new Color[ROWS][COLS];

    private Shape currentPiece;
    private Timer timer;

    public Board() {
        setBackground(Color.BLACK);
        setFocusable(true);

        timer = new Timer(500, e -> {
            moveDownLogic();
            repaint();
        });

        spawnPiece();
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (currentPiece == null) return;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> {
                        if (isValidMove(currentPiece.x - 1, currentPiece.y)) {
                            currentPiece.moveLeft();
                        }
                    }
                    case KeyEvent.VK_RIGHT -> {
                        if (isValidMove(currentPiece.x + 1, currentPiece.y)) {
                            currentPiece.moveRight();
                        }
                    }
                    case KeyEvent.VK_DOWN -> moveDownLogic();
                    case KeyEvent.VK_UP -> {
                        currentPiece.rotate();
                        if (!isValidMove(currentPiece.x, currentPiece.y)) {
                            currentPiece.rotateBack();
                        }
                    }
                }
                repaint();
            }
        });
    }

    private void spawnPiece() {
        currentPiece = PieceFactory.getRandomPiece();
        currentPiece.spawn(COLS / 2 - 1, 0);

        if (!isValidMove(currentPiece.x, currentPiece.y)) {
            if (timer != null) {
                timer.stop();
            }
            EndGame.handle(this);
        }
    }

    private void moveDownLogic() {
        if (currentPiece == null) return;
        if (isValidMove(currentPiece.x, currentPiece.y + 1)) {
            currentPiece.moveDown();
        } else {
            lockPiece();
            LineClearing.clear(boardGrid, ROWS, COLS);
            spawnPiece();
        }
    }

    private boolean isValidMove(int newX, int newY) {
        for (int[] relCoord : currentPiece.coordinates) {
            int x = newX + relCoord[0];
            int y = newY + relCoord[1];

            if (x < 0 || x >= COLS || y >= ROWS) {
                return false;
            }
            if (y >= 0 && boardGrid[y][x] != null) {
                return false;
            }
        }
        return true;
    }

    private void lockPiece() {
        for (int[] relCoord : currentPiece.coordinates) {
            int x = currentPiece.x + relCoord[0];
            int y = currentPiece.y + relCoord[1];
            if (y >= 0 && y < ROWS && x >= 0 && x < COLS) {
                boardGrid[y][x] = currentPiece.color;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (boardGrid[r][c] != null) {
                    g.setColor(boardGrid[r][c]);
                    g.fillRect(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
                }
            }
        }

        if (currentPiece != null) {
            g.setColor(currentPiece.color);
            for (int[] relCoord : currentPiece.coordinates) {
                int x = (currentPiece.x + relCoord[0]) * TILE_SIZE;
                int y = (currentPiece.y + relCoord[1]) * TILE_SIZE;
                g.fillRect(x, y, TILE_SIZE - 1, TILE_SIZE - 1);
            }
        }
    }
}
