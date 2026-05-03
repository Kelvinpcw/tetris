import javax.swing.JOptionPane;

public class EndGame {
    public static void handle(Board board) {
        JOptionPane.showMessageDialog(board, "Game Over!", "Tetris", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}
