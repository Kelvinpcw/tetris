import javax.swing.JFrame;

public class TetrisGame {
    public static void main(String[] args) {
        GameModel model = new GameModel();
        Board board = new Board(model);
        new GameController(model, board);

        JFrame frame = new JFrame("Tetris");
        frame.add(board);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}