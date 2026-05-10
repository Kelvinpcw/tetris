import java.awt.Color;

/**
 * Abstract base for all Tetris pieces.
 *
 * Concrete pieces (via PieceFactory) supply their own coordinate layout and
 * color through the two abstract methods. Everything else — movement, rotation,
 * position tracking — is handled here, and the previously public fields are now
 * properly encapsulated.
 */
public abstract class AbstractPiece {

    // Protected so Board can read them without extra boilerplate getters,
    // but external code goes through the concrete accessors below.
    protected int[][] coordinates;
    protected int x;
    protected int y;

    protected AbstractPiece() {
        this.coordinates = initCoordinates();
    }

    /** Concrete piece supplies its relative tile offsets. */
    public abstract int[][] initCoordinates();

    /** Concrete piece supplies its display color. */
    public abstract Color getColor();

    // ── position ────────────────────────────────────────────────────────────

    public void spawn(int startX, int startY) {
        this.x = startX;
        this.y = startY;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int[][] getCoordinates() { return coordinates; }

    // ── movement ────────────────────────────────────────────────────────────

    public void moveLeft()  { x--; }
    public void moveRight() { x++; }
    public void moveDown()  { y++; }

    // ── rotation ────────────────────────────────────────────────────────────

    public void rotate() {
        for (int[] coord : coordinates) {
            int temp  =  coord[0];
            coord[0]  = -coord[1];
            coord[1]  =  temp;
        }
    }

    public void rotateBack() {
        for (int[] coord : coordinates) {
            int temp  = coord[0];
            coord[0]  = coord[1];
            coord[1]  = -temp;
        }
    }
}
