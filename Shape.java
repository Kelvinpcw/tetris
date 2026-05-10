import java.awt.Color;

/**
 * General-purpose concrete piece used by PieceFactory.
 *
 * Extends AbstractPiece; coordinates and color are supplied at construction
 * time (PieceFactory's responsibility).  A copy constructor is provided so
 * Board can cheaply clone a piece for ghost-piece rendering.
 */
public class Shape extends AbstractPiece {

    private final int[][] coordTemplate;
    private final Color color;

    /**
     * Primary constructor used by PieceFactory.
     *
     * @param coords relative tile offsets that define the piece layout
     * @param color  display color
     */
    public Shape(int[][] coords, Color color) {
        // Store the template BEFORE super() triggers initCoordinates()
        // Java requires super() first, so we work around this by letting
        // initCoordinates() return a placeholder and then assigning properly.
        super();
        this.coordTemplate = coords;
        this.color = color;
        // Now that fields are set, initialise coordinates for real.
        this.coordinates = copyCoords(coords);
    }

    /**
     * Copy constructor — used by Board to create a ghost piece.
     *
     * @param source piece to clone
     */
    public Shape(Shape source) {
        super();
        this.coordTemplate = source.coordTemplate;
        this.color = source.color;
        this.coordinates = copyCoords(source.coordinates);
        this.x = source.x;
        this.y = source.y;
    }

    // AbstractPiece requires this; the real initialisation happens in the
    // constructor body above once coordTemplate is available.
    @Override
    public int[][] initCoordinates() {
        return new int[0][2]; // placeholder; overwritten in constructor body
    }

    @Override
    public Color getColor() { return color; }

    // ── helper ──────────────────────────────────────────────────────────────

    private static int[][] copyCoords(int[][] src) {
        int[][] copy = new int[src.length][2];
        for (int i = 0; i < src.length; i++) {
            copy[i][0] = src[i][0];
            copy[i][1] = src[i][1];
        }
        return copy;
    }
}
