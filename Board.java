import javax.swing.*;
import java.awt.*;

// Board is now a pure view 
public class Board extends JPanel {
    private static final int TILE_SIZE = 30;
    private static final int PREVIEW_OFFSET_X = GameModel.COLS * TILE_SIZE + 20;
    private static final int PREVIEW_TILE = 20;

    private final GameModel model;

    public Board(GameModel model) {
        this.model = model;
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(GameModel.COLS * TILE_SIZE + 120, GameModel.ROWS * TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawCurrentPiece(g);
        drawSidebar(g);
    }

    private void drawBoard(Graphics g) {
        Color[][] grid = model.getBoardGrid();
        for (int r = 0; r < GameModel.ROWS; r++) {
            for (int c = 0; c < GameModel.COLS; c++) {
                if (grid[r][c] != null) {
                    g.setColor(grid[r][c]);
                    g.fillRect(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
                }
            }
        }
        g.setColor(Color.DARK_GRAY);
        for (int r = 0; r <= GameModel.ROWS; r++) {
            g.drawLine(0, r * TILE_SIZE, GameModel.COLS * TILE_SIZE, r * TILE_SIZE);
        }
        for (int c = 0; c <= GameModel.COLS; c++) {
            g.drawLine(c * TILE_SIZE, 0, c * TILE_SIZE, GameModel.ROWS * TILE_SIZE);
        }
    }

    private void drawCurrentPiece(Graphics g) {
        Shape piece = model.getCurrentPiece();
        if (piece == null) return;

        Shape ghost = computeGhost(piece);
        g.setColor(piece.color.darker().darker());
        for (int[] rc : ghost.coordinates) {
            int x = (ghost.x + rc[0]) * TILE_SIZE;
            int y = (ghost.y + rc[1]) * TILE_SIZE;
            g.fillRect(x, y, TILE_SIZE - 1, TILE_SIZE - 1);
        }

        g.setColor(piece.color);
        for (int[] rc : piece.coordinates) {
            int x = (piece.x + rc[0]) * TILE_SIZE;
            int y = (piece.y + rc[1]) * TILE_SIZE;
            g.fillRect(x, y, TILE_SIZE - 1, TILE_SIZE - 1);
        }
    }

    private Shape computeGhost(Shape piece) {
        Shape ghost = new Shape(copyCoords(piece.coordinates), piece.color);
        ghost.spawn(piece.x, piece.y);
        while (model.isValidMove(ghost.x, ghost.y + 1)) {
            ghost.moveDown();
        }
        return ghost;
    }

    private int[][] copyCoords(int[][] src) {
        int[][] copy = new int[src.length][2];
        for (int i = 0; i < src.length; i++) {
            copy[i][0] = src[i][0];
            copy[i][1] = src[i][1];
        }
        return copy;
    }

    private void drawSidebar(Graphics g) {
        int sx = PREVIEW_OFFSET_X;

        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 12));
        g.drawString("NEXT", sx, 20);

        Shape next = model.getNextPiece();
        if (next != null) {
            g.setColor(next.color);
            for (int[] rc : next.coordinates) {
                int x = sx + (rc[0] + 2) * PREVIEW_TILE;
                int y = 30 + (rc[1] + 2) * PREVIEW_TILE;
                g.fillRect(x, y, PREVIEW_TILE - 1, PREVIEW_TILE - 1);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("SCORE", sx, 130);
        g.drawString(String.valueOf(model.getScore()), sx, 148);
    }
}
