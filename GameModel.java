import java.awt.Color;

/**
 * Holds all mutable game state.
 * Implements {@link GameAction} so the controller depends only on the interface.
 */
public class GameModel implements GameAction {
    public static final int ROWS = 20;
    public static final int COLS = 10;

    private final Color[][] boardGrid = new Color[ROWS][COLS];
    private Shape currentPiece;
    private Shape nextPiece;
    private int score = 0;
    private boolean gameOver = false;

    public GameModel() {
        nextPiece = PieceFactory.getRandomPiece();
        spawnPiece();
    }

    // ── GameAction implementation ────────────────────────────────────────────

    @Override
    public void moveLeft() {
        if (isValidMove(currentPiece.getX() - 1, currentPiece.getY()))
            currentPiece.moveLeft();
    }

    @Override
    public void moveRight() {
        if (isValidMove(currentPiece.getX() + 1, currentPiece.getY()))
            currentPiece.moveRight();
    }

    @Override
    public void moveDown() {
        if (isValidMove(currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.moveDown();
        } else {
            lockPiece();
            score += scoreForLines(LineClearing.clear(boardGrid, ROWS, COLS));
            spawnPiece();
        }
    }

    @Override
    public void rotate() {
        currentPiece.rotate();
        if (!isValidMove(currentPiece.getX(), currentPiece.getY()))
            currentPiece.rotateBack();
    }

    @Override
    public void hardDrop() {
        while (isValidMove(currentPiece.getX(), currentPiece.getY() + 1))
            currentPiece.moveDown();
        lockPiece();
        score += scoreForLines(LineClearing.clear(boardGrid, ROWS, COLS));
        spawnPiece();
    }

    @Override public boolean isGameOver() { return gameOver; }
    @Override public int getScore()       { return score; }

    // ── package-level helpers used by Board ──────────────────────────────────

    public boolean isValidMove(int newX, int newY) {
        for (int[] rel : currentPiece.getCoordinates()) {
            int x = newX + rel[0];
            int y = newY + rel[1];
            if (x < 0 || x >= COLS || y >= ROWS) return false;
            if (y >= 0 && boardGrid[y][x] != null) return false;
        }
        return true;
    }

    public Color[][] getBoardGrid()   { return boardGrid; }
    public Shape getCurrentPiece()    { return currentPiece; }
    public Shape getNextPiece()       { return nextPiece; }

    // ── private helpers ──────────────────────────────────────────────────────

    private void spawnPiece() {
        currentPiece = nextPiece;
        currentPiece.spawn(COLS / 2 - 1, 0);
        nextPiece = PieceFactory.getRandomPiece();
        if (!isValidMove(currentPiece.getX(), currentPiece.getY()))
            gameOver = true;
    }

    private void lockPiece() {
        for (int[] rel : currentPiece.getCoordinates()) {
            int x = currentPiece.getX() + rel[0];
            int y = currentPiece.getY() + rel[1];
            if (y >= 0 && y < ROWS && x >= 0 && x < COLS)
                boardGrid[y][x] = currentPiece.getColor();
        }
    }

    private int scoreForLines(int lines) {
        return switch (lines) {
            case 1 -> 100;
            case 2 -> 300;
            case 3 -> 500;
            case 4 -> 800;
            default -> 0;
        };
    }
}
