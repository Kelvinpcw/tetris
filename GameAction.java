/**
 * Defines the actions that can be performed on the game state.
 * GameController depends only on this interface, not on GameModel directly.
 */
public interface GameAction {
    void moveLeft();
    void moveRight();
    void moveDown();
    void rotate();
    void hardDrop();
    boolean isGameOver();
    int getScore();
}
