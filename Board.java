import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel {
    private final int TILE_SIZE = 30;
    private int curX = 4;
    private int curY = 0;
    private Shape currentPiece;

    public Board() {
        setBackground(Color.BLACK);
        setFocusable(true);
        currentPiece = PieceFactory.getSquare();

        // Game Loop: Moves the piece down every 500ms
        Timer timer = new Timer(500, e -> {
            moveDown();
            repaint();
        });
        timer.start();

        // Keyboard Controls
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> curX--;
                    case KeyEvent.VK_RIGHT -> curX++;
                    case KeyEvent.VK_DOWN -> moveDown();
                }
                repaint();
            }
        });
    }

    private void moveDown() {
        if (curY < 18) curY++; // Simple floor boundary
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(currentPiece.color);
        
        // Draw each block of the shape
        for (int[] relCoord : currentPiece.coordinates) {
            int x = (curX + relCoord[0]) * TILE_SIZE;
            int y = (curY + relCoord[1]) * TILE_SIZE;
            g.fillRect(x, y, TILE_SIZE - 1, TILE_SIZE - 1);
        }
    }
}