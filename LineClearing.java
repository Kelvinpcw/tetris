import java.awt.Color;

public class LineClearing {
    public static void clear(Color[][] boardGrid, int rows, int cols) {
        for (int r = rows - 1; r >= 0; r--) {
            boolean isFull = true;
            for (int c = 0; c < cols; c++) {
                if (boardGrid[r][c] == null) {
                    isFull = false;
                    break;
                }
            }

            if (isFull) {
                for (int moveRow = r; moveRow > 0; moveRow--) {
                    for (int c = 0; c < cols; c++) {
                        boardGrid[moveRow][c] = boardGrid[moveRow - 1][c];
                    }
                }

                for (int c = 0; c < cols; c++) {
                    boardGrid[0][c] = null;
                }

                r++;
            }
        }
    }
}
