import java.awt.Graphics;

/**
 * Any game panel that can paint itself implements this interface.
 * Board implements Renderable; future panels (pause screen, HUD overlay) would too.
 */
public interface Renderable {
    void render(Graphics g);
}
