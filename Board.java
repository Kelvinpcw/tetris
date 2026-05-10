import javax.swing.*;
import java.awt.*;

/**
 * The game's main drawing surface.
 *
 * Implements {@link Renderable}: all painting logic lives in {@code render()},
 * and {@code paintComponent()} simply delegates to it.  This keeps Swing's
 * override separate from the game's drawing concern, and lets other code
 * (tests, screenshots) call {@code render()} without going through Swing.
 */
public class Board extends JPanel implements Renderable {

    private static final int TILE_SIZE       = 30;
    private static final int PREVIEW_OFFSET_X = GameModel.COLS * TILE_SIZE + 20;
    private static final int PREVIEW_TILE     = 20;

    private final GameModel model;

    public Board(GameModel model) {
        this.model = model;
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(
                GameModel.COLS * TILE_SIZE + 120,
                GameModel.ROWS * TILE_SIZE));
    }

    // ── Swing override ───────────────────────────────────────────────────────

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);              // delegate to the Renderable contract
    }

    // ── Renderable implementation ────────────────────────────────────────────

    @Override
    public void render(Graphics g) {
        drawBoard(g);
        drawCurrentPiece(g);
        drawSidebar(g);
    }

    // ── private drawing helpers ──────────────────────────────────────────────

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
        for (int r = 0; r <= GameModel.ROWS; r++)
            g.drawLine(0, r * TILE_SIZE, GameModel.COLS * TILE_SIZE, r * TILE_SIZE);
        for (int c = 0; c <= GameModel.COLS; c++)
            g.drawLine(c * TILE_SIZE, 0, c * TILE_SIZE, GameModel.ROWS * TILE_SIZE);
    }

    private void drawCurrentPiece(Graphics g) {
        Shape piece = model.getCurrentPiece();
        if (piece == null) return;

        // ghost piece (shows where the piece will land)
        Shape ghost = new Shape(piece);          // copy constructor
        while (model.isValidMove(ghost.getX(), ghost.getY() + 1))
            ghost.moveDown();

        g.setColor(piece.getColor().darker().darker());
        for (int[] rc : ghost.getCoordinates()) {
            g.fillRect((ghost.getX() + rc[0]) * TILE_SIZE,
                       (ghost.getY() + rc[1]) * TILE_SIZE,
                       TILE_SIZE - 1, TILE_SIZE - 1);
        }

        // actual piece
        g.setColor(piece.getColor());
        for (int[] rc : piece.getCoordinates()) {
            g.fillRect((piece.getX() + rc[0]) * TILE_SIZE,
                       (piece.getY() + rc[1]) * TILE_SIZE,
                       TILE_SIZE - 1, TILE_SIZE - 1);
        }
    }

    private void drawSidebar(Graphics g) {
        int sx = PREVIEW_OFFSET_X;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, 12));
        g.drawString("NEXT", sx, 20);

        Shape next = model.getNextPiece();
        if (next != null) {
            g.setColor(next.getColor());
            for (int[] rc : next.getCoordinates()) {
                g.fillRect(sx + (rc[0] + 2) * PREVIEW_TILE,
                           30  + (rc[1] + 2) * PREVIEW_TILE,
                           PREVIEW_TILE - 1, PREVIEW_TILE - 1);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("SCORE", sx, 130);
        g.drawString(String.valueOf(model.getScore()), sx, 148);
    }
}
