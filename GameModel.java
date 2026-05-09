import java.awt.Color;

public class GameModel {
    public static final int ROWS = 20;
    public static final int COLS = 10;

    private Color[][] boardGrid = new Color[ROWS][COLS];
    private Shape currentPiece;
    private Shape nextPiece;
    private int score = 0;
    private boolean gameOver = false;

    public GameModel() {
        nextPiece = PieceFactory.getRandomPiece();
        spawnPiece();
    }

    public void spawnPiece() {
        currentPiece = nextPiece;
        currentPiece.spawn(COLS / 2 - 1, 0);
        nextPiece = PieceFactory.getRandomPiece();

        if (!isValidMove(currentPiece.x, currentPiece.y)) {
            gameOver = true;
        }
    }

    public void moveDown() {
        if (isValidMove(currentPiece.x, currentPiece.y + 1)) {
            currentPiece.moveDown();
        } else {
            lockPiece();
            int linesCleared = LineClearing.clear(boardGrid, ROWS, COLS);
            score += scoreForLines(linesCleared);
            spawnPiece();
        }
    }

    public void moveLeft() {
        if (isValidMove(currentPiece.x - 1, currentPiece.y)) {
            currentPiece.moveLeft();
        }
    }

    public void moveRight() {
        if (isValidMove(currentPiece.x + 1, currentPiece.y)) {
            currentPiece.moveRight();
        }
    }

    public void rotate() {
        currentPiece.rotate();
        if (!isValidMove(currentPiece.x, currentPiece.y)) {
            currentPiece.rotateBack();
        }
    }

    public void hardDrop() {
        while (isValidMove(currentPiece.x, currentPiece.y + 1)) {
            currentPiece.moveDown();
        }
        lockPiece();
        int linesCleared = LineClearing.clear(boardGrid, ROWS, COLS);
        score += scoreForLines(linesCleared);
        spawnPiece();
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

    public boolean isValidMove(int newX, int newY) {
        for (int[] relCoord : currentPiece.coordinates) {
            int x = newX + relCoord[0];
            int y = newY + relCoord[1];
            if (x < 0 || x >= COLS || y >= ROWS) return false;
            if (y >= 0 && boardGrid[y][x] != null) return false;
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

    public Color[][] getBoardGrid() { return boardGrid; }
    public Shape getCurrentPiece() { return currentPiece; }
    public Shape getNextPiece() { return nextPiece; }
    public int getScore() { return score; }
    public boolean isGameOver() { return gameOver; }
}
