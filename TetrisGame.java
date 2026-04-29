import javax.swing.JFrame;

public class TetrisGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("OOP Tetris MVP");
        Board board = new Board();
        
        frame.add(board);
        frame.setSize(315, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}