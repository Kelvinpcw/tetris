import java.awt.Color;
import java.util.Random;

public class PieceFactory {
    private static final Random random = new Random();

    public static Shape getRandomPiece() {
        int pieceType = random.nextInt(7);
        return switch (pieceType) {
            case 0 -> getSquare();
            case 1 -> getLine();
            case 2 -> getT();
            case 3 -> getL();
            case 4 -> getJ();
            case 5 -> getS();
            case 6 -> getZ();
            default -> getSquare();
        };
    }

    public static Shape getSquare() {
        int[][] coords = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
        return new Shape(coords, Color.YELLOW);
    }

    public static Shape getLine() {
        int[][] coords = {{-1, 0}, {0, 0}, {1, 0}, {2, 0}};
        return new Shape(coords, Color.CYAN);
    }

    public static Shape getT() {
        int[][] coords = {{0, -1}, {-1, 0}, {0, 0}, {1, 0}};
        return new Shape(coords, Color.MAGENTA);
    }

    public static Shape getL() {
        int[][] coords = {{-1, 0}, {0, 0}, {1, 0}, {1, -1}};
        return new Shape(coords, Color.ORANGE);
    }

    public static Shape getJ() {
        int[][] coords = {{-1, -1}, {-1, 0}, {0, 0}, {1, 0}};
        return new Shape(coords, Color.BLUE);
    }

    public static Shape getS() {
        int[][] coords = {{0, -1}, {1, -1}, {-1, 0}, {0, 0}};
        return new Shape(coords, Color.GREEN);
    }

    public static Shape getZ() {
        int[][] coords = {{-1, -1}, {0, -1}, {0, 0}, {1, 0}};
        return new Shape(coords, Color.RED);
    }
}
