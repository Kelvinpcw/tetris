import java.awt.Color;
// Factory design pattern to create Tetris pieces
public class PieceFactory {
    // This method returns a Square (O-shape)
    public static Shape getSquare() {
        // A square is 2x2: (0,0), (1,0), (0,1), (1,1)
        int[][] coords = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
        return new Shape(coords, Color.YELLOW);
    }
}